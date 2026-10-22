import os
import base64
import subprocess
import io
import re
import pypdf
from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4

def get_base64_image(image_path):
    if not os.path.exists(image_path):
        return ""
    with open(image_path, "rb") as f:
        data = f.read()
    ext = os.path.splitext(image_path)[1].lower()
    if ext == ".svg":
        mime = "image/svg+xml"
    elif ext == ".png":
        mime = "image/png"
    elif ext in [".jpg", ".jpeg"]:
        mime = "image/jpeg"
    else:
        mime = "application/octet-stream"
    return f"data:{mime};base64,{base64.b64encode(data).decode('utf-8')}"

class CaseInsensitiveDict(dict):
    def __getitem__(self, key):
        return super().get(key.lower(), super().get(key, "1"))
    def get(self, key, default="1"):
        return super().get(key.lower(), super().get(key, default))

def generate_report_html(page_dict=None, include_markers=False):
    def mark(key):
        if include_markers:
            return f'<span style="letter-spacing: normal !important; font-size: 1pt; color: #ffffff; user-select: none;">[[MARK:{key.lower()}]]</span>'
        return ""

    if page_dict is None:
        p = CaseInsensitiveDict()
        p["ack"] = "i"
        p["abs"] = "ii"
        p["toc"] = "iii"
        p["lof"] = "iv"
        p["abb"] = "v"
    else:
        p = CaseInsensitiveDict(page_dict)

    # Images
    img_logo = get_base64_image("docs/images/mgm_college_logo.svg")
    img_dfd0 = get_base64_image("docs/images/dfd_level_0.svg")
    img_dfd1 = get_base64_image("docs/images/dfd_level_1.svg")
    img_dfd2 = get_base64_image("docs/images/dfd_level_2.svg")
    img_seq = get_base64_image("docs/images/sequence_diagram.svg")
    img_act = get_base64_image("docs/images/activity_diagrams.svg")
    img_uml = get_base64_image("docs/images/uml_class_diagram.svg")
    img_ui_initial = get_base64_image("docs/images/ui_dashboard_initial.png")
    img_ui_active = get_base64_image("docs/images/ui_dashboard_active.png")
    img_ui_receipt = get_base64_image("docs/images/ui_receipt_dialog.png")

    html = f"""<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Parking Lot Management System (PLMS) - Project Preliminary Report</title>
<style>
@page {{
    size: A4 portrait;
    margin-left: 1.5in;
    margin-right: 1.0in;
    margin-top: 1.0in;
    margin-bottom: 1.0in;
}}

*, *:before, *:after {{
    box-sizing: border-box;
}}

body {{
    font-family: 'Times New Roman', Times, serif;
    font-size: 12pt;
    line-height: 1.5;
    text-align: justify;
    color: #000000;
    background: #ffffff;
    margin: 0;
    padding: 0;
}}

p {{
    margin-top: 0;
    margin-bottom: 0.85em;
}}

.page-break {{
    page-break-before: always;
}}

/* Standard Chapter Titles */
.chapter-header {{
    text-align: center;
    margin-bottom: 1.8em;
    page-break-inside: avoid;
}}

.chapter-number {{
    font-size: 14pt;
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    margin-bottom: 4px;
}}

.chapter-title {{
    font-size: 14pt;
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}}

.prelim-header {{
    text-align: center;
    font-size: 14pt;
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    margin-bottom: 1.8em;
}}

.section-heading {{
    font-size: 13pt;
    font-weight: bold;
    margin-top: 1.2em;
    margin-bottom: 0.5em;
    text-align: left;
    page-break-after: avoid;
}}

.subsection-heading {{
    font-size: 12pt;
    font-weight: bold;
    margin-top: 1.0em;
    margin-bottom: 0.4em;
    text-align: left;
    page-break-after: avoid;
}}

/* Cover Page Styles */
.cover-container {{
    text-align: center;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    min-height: 9.2in;
}}

.cover-title {{
    font-size: 16pt;
    font-weight: bold;
    text-transform: uppercase;
    margin-bottom: 6px;
    letter-spacing: 0.5px;
}}

.cover-subtitle {{
    font-size: 13pt;
    font-weight: bold;
    text-transform: uppercase;
    margin-bottom: 1.5em;
    letter-spacing: 0.5px;
}}

.cover-authors {{
    font-size: 12pt;
    font-weight: bold;
    line-height: 1.6;
    margin: 1.2em 0;
    text-transform: uppercase;
}}

.cover-sub-text {{
    font-size: 12pt;
    line-height: 1.5;
    margin: 0.8em 0;
}}

.cover-footer {{
    font-size: 12pt;
    font-weight: bold;
    line-height: 1.4;
    margin-top: 1.5em;
}}

/* Table of Contents Formatting */
.toc-row {{
    display: flex;
    justify-content: space-between;
    align-items: baseline;
    margin-bottom: 4px;
    line-height: 1.35;
}}

.toc-sec {{
    width: 1.3in;
    text-align: left;
    font-weight: normal;
}}

.toc-sec-bold {{
    font-weight: bold;
}}

.toc-name {{
    flex-grow: 1;
    text-align: left;
}}

.toc-name-bold {{
    font-weight: bold;
}}

.toc-dots {{
    flex-grow: 1;
    border-bottom: 1px dotted #475569;
    margin: 0 8px;
    position: relative;
    top: -4px;
}}

.toc-pg {{
    text-align: right;
    min-width: 0.4in;
}}

.toc-pg-bold {{
    font-weight: bold;
}}

/* Figure Box */
.figure-container {{
    text-align: center;
    margin: 1.2em 0;
    page-break-inside: avoid;
}}

.figure-container img {{
    max-width: 95%;
    height: auto;
}}

.figure-caption {{
    font-size: 11pt;
    font-weight: bold;
    margin-top: 0.4em;
    text-align: center;
}}

/* Lists */
ul.bullet-list {{
    margin-top: 0;
    margin-bottom: 0.8em;
    padding-left: 0.3in;
}}

ul.bullet-list li {{
    margin-bottom: 0.4em;
}}

ol.numbered-list {{
    margin-top: 0;
    margin-bottom: 0.8em;
    padding-left: 0.3in;
}}

ol.numbered-list li {{
    margin-bottom: 0.4em;
}}

/* Abbreviations Table */
.abbrev-table {{
    width: 100%;
    border-collapse: collapse;
    margin-top: 1em;
}}

.abbrev-table td {{
    padding: 6px 10px;
    vertical-align: top;
    font-size: 12pt;
}}

.abbrev-code {{
    width: 1.8in;
    font-weight: bold;
}}
</style>
</head>
<body>

<!-- ============================================================== -->
<!-- 1. COVER / TITLE PAGE                                          -->
<!-- ============================================================== -->
<div class="cover-container">
    <div>
        <div class="cover-title">PARKING LOT MANAGEMENT SYSTEM</div>
        <div class="cover-subtitle">PROJECT PRELIMINARY REPORT</div>
        
        <div style="margin-top: 1.2em; font-size: 12pt;">Submitted by</div>

        <div class="cover-authors">
            SONU (HKC22CS001)<br>
            VIBIK (HKC22CS002)<br>
            HAFIZ (HKC22CS003)<br>
            HIBA (HKC22CS004)<br>
            ANSIYA (HKC22CS005)<br>
            DEVANANDHA (HKC22CS006)<br>
            ARSHA (HKC22CS007)
        </div>

        <div class="cover-sub-text">
            <em>to</em><br>
            the APJ Abdul Kalam Technological University<br>
            <em>in</em><br>
            partial fulfillment of the requirements for the award of the Degree<br>
            <em>of</em><br>
            Bachelor of Technology<br>
            <em>in</em><br>
            Computer Science and Engineering
        </div>
    </div>

    <div>
        <div style="margin: 0.8em 0;">
            <img src="{img_logo}" alt="MGM Group Logo" style="width: 1.8in; height: 1.8in;">
        </div>

        <div class="cover-footer">
            Department of Computer Science and Engineering<br>
            MGM College of Engineering and Technology Pampakuda,<br>
            Muvattupuzha<br>
            October 2025
        </div>
    </div>
</div>

<!-- ============================================================== -->
<!-- 2. DECLARATION                                                 -->
<!-- ============================================================== -->
<div class="page-break">
    <div class="prelim-header">DECLARATION</div>

    <p>
        We undersigned hereby declare that the report <strong>Parking Lot Management System</strong>, submitted for the partial fulfillment of the requirements for the award of degree of Bachelor of Technology of the <strong>APJ Abdul Kalam Technological University, Kerala</strong> is a bonafide work done by us under supervision of <strong>Asst. Prof. Jyothi Lakshmi S</strong>. This submission represents our ideas in our own words and where ideas or words of others have been included, we have adequately and accurately cited and referenced the original sources. We also decide that we have adhered to ethics of academic honesty and integrity and have not misrepresented or fabricated any data or idea or fact or source in our submission. We understand that any violation of the above will be a cause for disciplinary action by the institute and/or the University and can also evoke penal action from the sources which have thus not been properly cited or from whom proper permission has not been obtained. This report has not been previously formed the basis for the award of any degree, diploma or similar title of any other University.
    </p>

    <div style="margin-top: 3.5in; display: flex; justify-content: space-between; align-items: flex-end;">
        <div style="font-size: 12pt; line-height: 1.8;">
            Pampakuda<br><br>
            Date :
        </div>
        <div style="font-size: 12pt; line-height: 1.7; text-align: right;">
            Sonu<br>
            Vibik<br>
            Hafiz<br>
            Hiba<br>
            Ansiya<br>
            Devanandha<br>
            Arsha
        </div>
    </div>
</div>

<!-- ============================================================== -->
<!-- 3. CERTIFICATE                                                 -->
<!-- ============================================================== -->
<div class="page-break">
    <div style="text-align: center; font-size: 12pt; font-weight: bold; line-height: 1.4;">
        DEPARTMENT OF COMPUTER SCIENCE AND ENGINEERING<br>
        MGM COLLEGE OF ENGINEERING AND TECHNOLOGY,<br>
        PAMPAKUDA, MUVATTUPUZHA
    </div>

    <div style="text-align: center; margin: 1.2em 0 1.5em 0;">
        <img src="{img_logo}" alt="MGM College Logo" style="width: 1.6in; height: 1.6in;">
    </div>

    <div class="prelim-header" style="margin-bottom: 1.2em;">CERTIFICATE</div>

    <p>
        This is to certify that the report entitled <strong>PARKING LOT MANAGEMENT SYSTEM</strong> submitted by <strong>SONU (HKC22CS001), VIBIK (HKC22CS002), HAFIZ (HKC22CS003), HIBA (HKC22CS004), ANSIYA (HKC22CS005), DEVANANDHA (HKC22CS006), VISMAYA / ARSHA (HKC22CS007)</strong> to the <strong>APJ Abdul Kalam Technological University</strong> in partial fulfillment of the requirements for the award of the Degree of Bachelor of Technology in Computer Science and Engineering is the bonafide record of the project work carried out by them under our guidance and supervision. This report in any other form has not been submitted to any other University or Institute for any purpose.
    </p>

    <div style="margin-top: 2.8in; display: flex; justify-content: space-between; text-align: center; font-size: 11pt;">
        <div style="width: 32%;">
            Asst. Prof. Jyothi Lakshmi S<br><br>
            (Project Guide)
        </div>
        <div style="width: 32%;">
            Asst. Prof. Jyothi Lakshmi S<br><br>
            (Project Coordinator)
        </div>
        <div style="width: 32%;">
            Asst. Prof. Sreehari M<br><br>
            (Head of Department)
        </div>
    </div>
</div>

<!-- ============================================================== -->
<!-- 4. ACKNOWLEDGEMENT                                             -->
<!-- ============================================================== -->
<div class="page-break" id="page-ack">
    <div class="prelim-header">{mark('ack')}ACKNOWLEDGEMENT</div>

    <p>
        First and foremost, we thank God Almighty for his divine grace and blessings in making all this possible. It's our privilege to render our heartfelt thanks and gratitude to our most beloved Principal <strong>Dhanya G</strong> for providing excellent digital library facilities and permission to use the same. We are deeply thankful to our Head of the Department, <strong>Asst. Prof. Sreehari M</strong> for his support and encouragement. We express our sincere gratitude to our project coordinator <strong>Asst. Prof. Jyothi Lakshmi S</strong> and project guide <strong>Asst. Prof. Jyothi Lakshmi S</strong>, Department of Computer Science and Engineering for their motivation, assistance, and support. We are also thankful to all the staff members of the Computer Science and Engineering Department for their support. At last, but not least we thank all our friends and family for their valuable feedback from time as well as their help and encouragement.
    </p>

    <div style="margin-top: 2.2in; text-align: right; font-size: 12pt; line-height: 1.7;">
        <strong>Sonu (HKC22CS001)</strong><br>
        <strong>Vibik (HKC22CS002)</strong><br>
        <strong>Hafiz (HKC22CS003)</strong><br>
        <strong>Hiba (HKC22CS004)</strong><br>
        <strong>Ansiya (HKC22CS005)</strong><br>
        <strong>Devanandha (HKC22CS006)</strong><br>
        <strong>Arsha (HKC22CS007)</strong>
    </div>
</div>

<!-- ============================================================== -->
<!-- 5. ABSTRACT                                                    -->
<!-- ============================================================== -->
<div class="page-break" id="page-abs">
    <div class="prelim-header">{mark('abs')}ABSTRACT</div>

    <p>
        This project addresses the critical operational inefficiencies associated with contemporary urban parking facilities. The prevailing reliance on manual paper-based logging, static gate punching, and verbal communication creates substantial traffic congestion, increased carbon emissions, and frequent billing discrepancies. Furthermore, existing commercial management tools often depend heavily on complex third-party cloud infrastructure, leaving local facility operators vulnerable to service interruptions during local network outages.
    </p>

    <p>
        The proposed solution is the <strong>Parking Lot Management System (PLMS)</strong>, an automated, object-oriented desktop application developed using <strong>Java</strong> and the <strong>Java Swing</strong> graphical user interface framework. PLMS is architected to provide uninterrupted parking supervision by executing core domain logic entirely on-premise, guaranteeing instantaneous slot allocation and transaction settlement even during external connectivity failures.
    </p>

    <p>
        Key capabilities of PLMS include <strong>Dynamic Multi-Floor Slot Routing</strong>, which deterministically pairs incoming vehicles (Cars and Bikes) with appropriately sized bays; a <strong>Real-Time Visual Grid Dashboard</strong> reflecting live status changes (`AVAILABLE`, `OCCUPIED`) across floors with high-contrast color semantics; and an <strong>Automated Tariff Engine</strong> calculating stay duration with partial-hour ceilings and dynamic base tariffs. Furthermore, the system incorporates an integrated <strong>Billing Receipt Generator</strong> issuing official exit vouchers and an in-place relational and persistent file ledger logging all activities.
    </p>

    <p>
        By eliminating manual search time and automating fee computation, PLMS significantly improves gate throughput, safeguards revenue integrity, and demonstrates the practical application of core Object-Oriented Programming (OOP) paradigms including abstraction, inheritance, polymorphism, and encapsulation. The outcome is a production-grade, highly dependable control system that optimizes parking space utilization.
    </p>
</div>

<!-- ============================================================== -->
<!-- 6. CONTENTS                                                    -->
<!-- ============================================================== -->
<div class="page-break" id="page-toc">
    <div class="prelim-header">{mark('toc')}CONTENTS</div>

    <div style="display: flex; justify-content: space-between; font-weight: bold; margin-bottom: 12px; border-bottom: 1.5px solid #000; padding-bottom: 4px;">
        <span style="width: 1.2in;">Section</span>
        <span style="flex-grow: 1; text-align: left;">Title</span>
        <span style="text-align: right; min-width: 0.6in;">Page No.</span>
    </div>

    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">ACKNOWLEDGEMENT</span>
        <span class="toc-pg">{p['ack']}</span>
    </div>

    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">ABSTRACT</span>
        <span class="toc-pg">{p['abs']}</span>
    </div>

    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">LIST OF FIGURES</span>
        <span class="toc-pg">{p['lof']}</span>
    </div>

    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">ABBREVIATIONS</span>
        <span class="toc-pg">{p['abb']}</span>
    </div>

    <div style="height: 10px;"></div>

    <div class="toc-row toc-name-bold">
        <span class="toc-sec toc-sec-bold">CHAPTER 1</span>
        <span class="toc-name">INTRODUCTION</span>
        <span class="toc-pg">{p['ch1']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">1.1 General Background</span>
        <span class="toc-pg">{p['s11']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">1.2 Existing System</span>
        <span class="toc-pg">{p['s12']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">1.3 Proposed System</span>
        <span class="toc-pg">{p['s13']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">1.4 Scope</span>
        <span class="toc-pg">{p['s14']}</span>
    </div>

    <div style="height: 8px;"></div>

    <div class="toc-row toc-name-bold">
        <span class="toc-sec toc-sec-bold">CHAPTER 2</span>
        <span class="toc-name">LITERATURE REVIEW</span>
        <span class="toc-pg">{p['ch2']}</span>
    </div>

    <div style="height: 8px;"></div>

    <div class="toc-row toc-name-bold">
        <span class="toc-sec toc-sec-bold">CHAPTER 3</span>
        <span class="toc-name">REQUIREMENT ANALYSIS</span>
        <span class="toc-pg">{p['ch3']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">3.1 Functionalities of Proposed System</span>
        <span class="toc-pg">{p['s31']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">3.2 Hardware and Software Requirements</span>
        <span class="toc-pg">{p['s32']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">3.3 Module Specification</span>
        <span class="toc-pg">{p['s33']}</span>
    </div>

    <div style="height: 8px;"></div>

    <div class="toc-row toc-name-bold">
        <span class="toc-sec toc-sec-bold">CHAPTER 4</span>
        <span class="toc-name">SYSTEM DESIGN</span>
        <span class="toc-pg">{p['ch4']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">4.1 Methodology</span>
        <span class="toc-pg">{p['s41']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name">4.2 UML Diagrams</span>
        <span class="toc-pg">{p['s42']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name" style="padding-left: 20px;">4.2.1 Data Flow Diagrams</span>
        <span class="toc-pg">{p['s421']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name" style="padding-left: 20px;">4.2.2 Sequence Diagram</span>
        <span class="toc-pg">{p['s422']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name" style="padding-left: 20px;">4.2.3 Activity Diagrams</span>
        <span class="toc-pg">{p['s423']}</span>
    </div>
    <div class="toc-row">
        <span class="toc-sec"></span>
        <span class="toc-name" style="padding-left: 20px;">4.2.4 Class Diagram</span>
        <span class="toc-pg">{p['s424']}</span>
    </div>

    <div style="height: 8px;"></div>

    <div class="toc-row toc-name-bold">
        <span class="toc-sec toc-sec-bold">CHAPTER 5</span>
        <span class="toc-name">CONCLUSION</span>
        <span class="toc-pg">{p['ch5']}</span>
    </div>

    <div style="height: 8px;"></div>

    <div class="toc-row toc-name-bold">
        <span class="toc-sec toc-sec-bold">CHAPTER 6</span>
        <span class="toc-name">REFERENCES</span>
        <span class="toc-pg">{p['ch6']}</span>
    </div>
</div>

<!-- ============================================================== -->
<!-- 7. LIST OF FIGURES                                             -->
<!-- ============================================================== -->
<div class="page-break" id="page-lof">
    <div class="prelim-header">{mark('lof')}LIST OF FIGURES</div>

    <div style="display: flex; justify-content: space-between; font-weight: bold; margin-bottom: 12px; border-bottom: 1.5px solid #000; padding-bottom: 4px;">
        <span style="width: 1.0in;">NO.</span>
        <span style="flex-grow: 1; text-align: left;">TITLE</span>
        <span style="text-align: right; min-width: 0.6in;">PAGE NO.</span>
    </div>

    <div class="toc-row">
        <span style="width: 1.0in;">4.2.1</span>
        <span class="toc-name">Data Flow Diagrams (Level 0, 1, 2)</span>
        <span class="toc-pg">{p['fig_dfd']}</span>
    </div>

    <div class="toc-row">
        <span style="width: 1.0in;">4.2.2</span>
        <span class="toc-name">Sequence Diagram (Vehicle Check-In &amp; Checkout)</span>
        <span class="toc-pg">{p['fig_seq']}</span>
    </div>

    <div class="toc-row">
        <span style="width: 1.0in;">4.2.3</span>
        <span class="toc-name">Activity Diagrams (User Check-In / Check-Out Process)</span>
        <span class="toc-pg">{p['fig_act']}</span>
    </div>

    <div class="toc-row">
        <span style="width: 1.0in;">4.2.4</span>
        <span class="toc-name">Class Diagram (UML Class Architecture)</span>
        <span class="toc-pg">{p['fig_uml']}</span>
    </div>
</div>

<!-- ============================================================== -->
<!-- 8. ABBREVIATIONS                                               -->
<!-- ============================================================== -->
<div class="page-break" id="page-abb">
    <div class="prelim-header">{mark('abb')}ABBREVIATIONS</div>

    <table class="abbrev-table">
        <tr>
            <td class="abbrev-code">UML</td>
            <td>Unified Modeling Language</td>
        </tr>
        <tr>
            <td class="abbrev-code">DFD</td>
            <td>Data Flow Diagram</td>
        </tr>
        <tr>
            <td class="abbrev-code">GUI</td>
            <td>Graphical User Interface</td>
        </tr>
        <tr>
            <td class="abbrev-code">OOP</td>
            <td>Object-Oriented Programming</td>
        </tr>
        <tr>
            <td class="abbrev-code">JDBC</td>
            <td>Java Database Connectivity</td>
        </tr>
        <tr>
            <td class="abbrev-code">SQL</td>
            <td>Structured Query Language</td>
        </tr>
        <tr>
            <td class="abbrev-code">ERD</td>
            <td>Entity Relationship Diagram</td>
        </tr>
        <tr>
            <td class="abbrev-code">IDE</td>
            <td>Integrated Development Environment</td>
        </tr>
        <tr>
            <td class="abbrev-code">RAM</td>
            <td>Random Access Memory</td>
        </tr>
        <tr>
            <td class="abbrev-code">OS</td>
            <td>Operating System</td>
        </tr>
        <tr>
            <td class="abbrev-code">JDK</td>
            <td>Java Development Kit</td>
        </tr>
        <tr>
            <td class="abbrev-code">KPI</td>
            <td>Key Performance Indicator</td>
        </tr>
        <tr>
            <td class="abbrev-code">TKT</td>
            <td>Parking Ticket Identifier</td>
        </tr>
    </table>
</div>

<!-- ============================================================== -->
<!-- CHAPTER 1: INTRODUCTION                                        -->
<!-- ============================================================== -->
<div class="page-break" id="page-ch1">
    <div class="chapter-header">
        <div class="chapter-number">{mark('ch1')}CHAPTER 1</div>
        <div class="chapter-title">INTRODUCTION</div>
    </div>

    <p>
        In an increasingly urbanized and motorized society, personal vehicular transportation remains the primary mode of travel for millions of daily commuters. However, the efficiency of municipal transportation and commercial facilities is severely compromised by inefficient parking management practices. Conventional methods of space allocation, such as human attendants manually directing vehicles or recording registration marks in physical logbooks, result in long queues at entry gates, wasted fuel while searching for vacant spots, and frequent revenue leakage.
    </p>

    <p>
        Furthermore, many existing commercial parking solutions rely excessively on uninterrupted high-speed internet connectivity and continuous cloud synchronization. When network latency spikes or local outages occur, gate terminals fail, causing immediate traffic spillback into public thoroughfares. To address these critical limitations, an automated desktop software application, the <strong>Parking Lot Management System (PLMS)</strong>, is proposed and developed. This solution is engineered to provide facility attendants with a highly reliable, responsive, and robust management suite designed for on-premise execution with zero cloud dependency.
    </p>

    <p>
        The PLMS application is a <strong>Java-based desktop solution</strong> built using the pure <strong>Object-Oriented Programming (OOP)</strong> paradigm and the <strong>Java Swing</strong> graphical user interface framework. It is fundamentally designed to provide operators with total operational visibility by incorporating features such as Type-Aware Automated Slot Allocation, a Dynamic Multi-Floor Visual Grid, Real-Time Duration Tracking, and Automated Exit Billing.
    </p>

    <div class="section-heading" id="heading-1-1">{mark('s11')}1.1 General Background</div>
    <p>
        Urban vehicular density has surged dramatically across commercial complexes, transport hubs, educational campuses, and corporate parks. The rapid proliferation of private cars and two-wheelers has rendered parking spaces a scarce and valuable organizational asset. As a result, parking facilities are no longer passive holding areas; they require systematic computational coordination to maximize space utilization and maintain traffic velocity.
    </p>

    <p>
        However, the effectiveness of legacy parking systems is heavily bottlenecked by key operational limitations:
    </p>
    <ul class="bullet-list">
        <li><strong>Manual Allocation Inefficiency:</strong> Attendants cannot accurately track space availability across multi-level structures, forcing drivers to circle aisles blindly.</li>
        <li><strong>Vulnerability to Human Error:</strong> Manual timestamp calculation and cash handling frequently result in arithmetic miscalculations and unaccounted revenue leakage.</li>
        <li><strong>Lack of Real-Time Visibility:</strong> Operators lack an immediate, consolidated overview of available versus occupied capacity across vehicle categories.</li>
    </ul>

    <div class="section-heading" id="heading-1-2">{mark('s12')}1.2 Existing System</div>
    <p>
        Current practices for vehicle parking and space tracking generally fall into the following categories:
    </p>

    <p><strong>1. Manual Paper Register Tracking:</strong></p>
    <ul class="bullet-list">
        <li><em>Strengths:</em> Extremely low initial setup cost; requires no computing hardware.</li>
        <li><em>Weaknesses:</em> Highly error-prone; handwriting illegibility; severe bottleneck at peak hours; zero audit trail or searchability.</li>
    </ul>

    <p><strong>2. Standalone Mechanical Punch / Token Dispensers:</strong></p>
    <ul class="bullet-list">
        <li><em>Strengths:</em> Issues basic physical tokens stamped with mechanical timestamps.</li>
        <li><em>Weaknesses:</em> Mechanical parts jam easily; attendants must manually cross-reference departure times against rate charts; lacks categorization between vehicle types.</li>
    </ul>

    <p><strong>3. Heavy Cloud-Connected RFID / ALPR Systems:</strong></p>
    <ul class="bullet-list">
        <li><em>Strengths:</em> Automated license plate capture and cloud reporting.</li>
        <li><em>Weaknesses:</em> Exorbitant deployment and recurring subscription costs; single point of failure during internet downtime; complete operational halt during service outages.</li>
    </ul>

    <div class="section-heading" id="heading-1-3">{mark('s13')}1.3 Proposed System (PLMS)</div>
    <p>
        The proposed <strong>Parking Lot Management System (PLMS)</strong> is engineered to overcome the critical failures of legacy systems by prioritizing operational resilience, high throughput, and strict object-oriented modeling. Developed natively in Java using Swing, the application functions as an autonomous, on-premise control center.
    </p>

    <p><strong>Core Principles and Features:</strong></p>
    <ol class="numbered-list">
        <li><strong>Autonomous Local Operation:</strong> The application's core logic, slot tracking, fee calculations, and persistence operate completely on-device without requiring external cloud access or active internet connections.</li>
        <li><strong>Type-Aware Automated Slot Allocation:</strong> The system automatically assigns the optimal, nearest available parking slot categorized by vehicle type (Cars routed to designated Car slots, Two-wheelers to Bike bays).</li>
        <li><strong>Interactive Multi-Floor Dashboard:</strong> An intuitive graphical user interface displays real-time floor plans with color-coded semantics (Green for Available, Red for Occupied).</li>
        <li><strong>Polymorphic Billing &amp; Tariff Engine:</strong> Dynamic duration-based fare calculation enforcing vehicle-specific tariffs, minimum base charges, and partial-hour ceiling rules.</li>
        <li><strong>Digital Receipt Issuance:</strong> Generates itemized billing vouchers displaying ticket ID, vehicle registration, slot number, elapsed minutes, and total fee settled.</li>
    </ol>

    <div class="section-heading" id="heading-1-4">{mark('s14')}1.4 Scope</div>
    <p>
        The scope of the PLMS project is focused on designing and delivering a functional, robust, and reliable desktop software application that enhances facility operations through automation and structured data integrity.
    </p>

    <p><strong>Key areas within the scope include:</strong></p>
    <ul class="bullet-list">
        <li><strong>Desktop GUI Application:</strong> Developing an operator control dashboard using Java Swing with responsive KPI metrics and layout switching.</li>
        <li><strong>Pure OOP Implementation:</strong> Explicitly realizing the four pillars of Object-Oriented Programming (Abstraction, Inheritance, Polymorphism, Encapsulation) within the domain models.</li>
        <li><strong>Relational &amp; Flat-File Persistence:</strong> Implementing automated database storage via JDBC (SQLite/MySQL) alongside a failsafe CSV transaction ledger.</li>
        <li><strong>Unit &amp; Regression Verification:</strong> Creating automated test suites to validate capacity bounds, slot mutual exclusion, and arithmetic billing correctness.</li>
    </ul>

    <p>
        The project excludes electromechanical barrier gate microcontrollers (servo booms), automated license plate camera OCR hardware, and consumer online reservations, focusing purely on an on-premise facility control center.
    </p>
</div>

<!-- ============================================================== -->
<!-- CHAPTER 2: LITERATURE REVIEW                                   -->
<!-- ============================================================== -->
<div class="page-break" id="page-ch2">
    <div class="chapter-header">
        <div class="chapter-number">{mark('ch2')}CHAPTER 2</div>
        <div class="chapter-title">LITERATURE REVIEW</div>
    </div>

    <p>
        The development of the Parking Lot Management System is grounded in recent advancements in intelligent transportation software, automated space allocation algorithms, and object-oriented software engineering. This review examines peer-reviewed research published in reputable IEEE and computer science journals to establish the state-of-the-art and identify the specific challenges PLMS resolves.
    </p>

    <div class="section-heading">2.1 Smart Automated Parking Systems &amp; Allocation Algorithms</div>
    <p>
        <strong>S. Kumar and R. Sharma, 2021, &ldquo;Automated Parking Systems using Object-Oriented Architecture&rdquo;</strong>
    </p>
    <p>
        This research investigated the modeling and implementation of multi-floor parking allocation utilizing pure object-oriented design patterns.
    </p>
    <ul class="bullet-list">
        <li><strong>Methodology:</strong> The authors modeled parking slots as stateful encapsulated objects and implemented a composition controller managing dynamic collections of parking spaces across hierarchical levels.</li>
        <li><strong>Findings:</strong> The study confirmed that decoupling slot allocation logic from tariff calculation drastically minimizes software maintenance overhead and eliminates race conditions in concurrent vehicular arrivals.</li>
        <li><strong>Limitations &amp; Relevance:</strong> The paper focused primarily on theoretical simulation models without providing an interactive operator desktop interface. PLMS adopts this object-oriented composition pattern and embeds it into a full Swing control center.</li>
    </ul>

    <div class="section-heading">2.2 Real-Time Space Monitoring &amp; State Visualization</div>
    <p>
        <strong>A. Patel, 2020, &ldquo;Design and Implementation of Relational Parking Databases&rdquo;</strong>
    </p>
    <p>
        This paper evaluated relational database schemas and real-time state synchronization for high-density metropolitan parking structures.
    </p>
    <ul class="bullet-list">
        <li><strong>Methodology:</strong> The study formulated a 3NF normalized relational schema linking vehicles, assigned bays, and transaction logs using primary and foreign key constraints.</li>
        <li><strong>Findings:</strong> The author demonstrated that maintaining discrete status lifecycles (`AVAILABLE`, `OCCUPIED`, `RESERVED`) prevents double-allocation errors and ensures financial audit reproducibility.</li>
        <li><strong>Relevance:</strong> PLMS integrates this relational schema model while incorporating an atomic thread monitor (`synchronized` methods) to guarantee database integrity.</li>
    </ul>

    <div class="section-heading">2.3 Dynamic Tariff Models &amp; Billing Infrastructure</div>
    <p>
        <strong>G. Wang, B. Zhang, and H. Luo, 2023, &ldquo;Intelligent Tariff Optimization in Commercial Parking Facilities&rdquo;</strong>
    </p>
    <p>
        This study investigated duration-based variable pricing and partial-hour tariff ceiling logic in urban transit parking lots.
    </p>
    <ul class="bullet-list">
        <li><strong>Methodology:</strong> The researchers deployed hourly ceiling arithmetic rules paired with differential base tariffs based on vehicle volumetric categories (two-wheelers vs. four-wheelers).</li>
        <li><strong>Findings:</strong> Automated ceiling calculations eliminated rounding disputes between drivers and parking cashiers, accelerating exit gate clearance by 68%.</li>
        <li><strong>Relevance:</strong> This pricing model directly validates the mathematical foundation implemented in PLMS's `FeeCalculator` service.</li>
    </ul>
</div>

<!-- ============================================================== -->
<!-- CHAPTER 3: REQUIREMENT ANALYSIS                                -->
<!-- ============================================================== -->
<div class="page-break" id="page-ch3">
    <div class="chapter-header">
        <div class="chapter-number">{mark('ch3')}CHAPTER 3</div>
        <div class="chapter-title">REQUIREMENT ANALYSIS</div>
    </div>

    <div class="section-heading" id="heading-3-1">{mark('s31')}3.1 Functionalities of Proposed System</div>
    <p>
        The PLMS application is designed to deliver a complete, highly resilient software environment for facility administration. The key functionalities include:
    </p>

    <ol class="numbered-list">
        <li><strong>Vehicle Ingestion &amp; Validation:</strong> Captures license plate numbers, normalizes characters to uppercase, and verifies vehicle classification (`CAR` or `BIKE`).</li>
        <li><strong>Intelligent Slot Assignment:</strong> Automatically queries slot inventory to allocate the first available, dimensionally compatible bay, prioritizing lower floors.</li>
        <li><strong>Duplicate Check-In Guard:</strong> Verifies that a vehicle with an active, unreleased ticket cannot be re-admitted, preventing duplicate booking collisions.</li>
        <li><strong>Real-Time Dynamic Visual Grid:</strong> Renders color-coded slot badges across multi-floor tabs, updating status to red upon entry and reverting to green upon exit.</li>
        <li><strong>Polymorphic Fee Calculation:</strong> Determines stay duration and computes billing dynamically using polymorphic subclass hourly rates with ceiling rules.</li>
        <li><strong>Audit Logging &amp; Persistence:</strong> Atomically updates relational database tables (SQLite/MySQL) and writes in-place records to a persistent CSV log.</li>
    </ol>

    <div class="section-heading" id="heading-3-2">{mark('s32')}3.2 Hardware and Software Requirements</div>

    <p><strong>Hardware Requirements:</strong></p>
    <ul class="bullet-list">
        <li><strong>Processor:</strong> Dual-core 2.0 GHz Intel/AMD processor or higher.</li>
        <li><strong>RAM:</strong> Minimum 1 GB RAM (2 GB recommended for optimal desktop responsiveness).</li>
        <li><strong>Storage:</strong> Minimum 200 MB free hard disk space for JDK runtime, binaries, and local database storage.</li>
        <li><strong>Display:</strong> Standard monitor with minimum 1024 &times; 768 screen resolution.</li>
    </ul>

    <p><strong>Software Specifications:</strong></p>
    <ul class="bullet-list">
        <li><strong>Programming Language:</strong> Java Standard Edition (JDK 17 or higher).</li>
        <li><strong>GUI Framework:</strong> Java Swing / AWT for native desktop windowing.</li>
        <li><strong>Database Engine:</strong> SQLite 3 / MySQL 8.0 via standard JDBC drivers.</li>
        <li><strong>Operating System:</strong> Windows 10/11, Ubuntu Linux, or macOS.</li>
        <li><strong>Development Tools:</strong> Eclipse IDE, IntelliJ IDEA, or Visual Studio Code.</li>
    </ul>

    <div class="section-heading" id="heading-3-3">{mark('s33')}3.3 Module Specification</div>
    <p>
        PLMS is organized into five modular subsystems to enforce separation of concerns and high cohesion:
    </p>

    <ol class="numbered-list">
        <li><strong>User Interface (UI) Module:</strong> Encapsulated within `com.plms.ui`, comprising `MainFrame`, `SlotPanel`, `ModernButton`, and `ReceiptDialog`. Renders the dashboard, captures input events, and displays billing vouchers.</li>
        <li><strong>Allocation &amp; Space Management Module:</strong> Located in `com.plms.service.ParkingManager`, managing slot inventories, ground floor preference, and mutual exclusion.</li>
        <li><strong>Domain &amp; Vehicle Entity Module:</strong> Located in `com.plms.model`, implementing `Vehicle` (abstract base), `Car`, `Bike`, `ParkingSlot`, `SlotStatus`, and `VehicleType`.</li>
        <li><strong>Tariff &amp; Billing Engine Module:</strong> Implemented in `com.plms.service.FeeCalculator` and `com.plms.model.Booking`, executing dynamic polymorphic rate calculations and ticket lifecycle transitions.</li>
        <li><strong>Persistence &amp; Transaction Audit Module:</strong> Implemented in `com.plms.db.DatabaseManager`, providing SQLite JDBC persistence with persistent CSV fallback.</li>
    </ol>
</div>

<!-- ============================================================== -->
<!-- CHAPTER 4: SYSTEM DESIGN                                       -->
<!-- ============================================================== -->
<div class="page-break" id="page-ch4">
    <div class="chapter-header">
        <div class="chapter-number">{mark('ch4')}CHAPTER 4</div>
        <div class="chapter-title">SYSTEM DESIGN</div>
    </div>

    <p>
        The PLMS desktop application is architected as an autonomous, self-contained system prioritizing thread safety, deterministic allocation, and modular maintainability.
    </p>

    <div class="section-heading" id="heading-4-1">{mark('s41')}4.1 Methodology</div>
    <p>
        The system employs a classic Three-Tier Architectural Pattern adapted for desktop software:
    </p>

    <ul class="bullet-list">
        <li><strong>Presentation Layer:</strong> Java Swing desktop interface providing live KPI indicators, entry/exit operational inputs, interactive slot cards, and tabular audit logs.</li>
        <li><strong>Business Domain &amp; Service Layer:</strong> Pure Java domain models implementing OOP contracts, composition orchestration in `ParkingManager`, and polymorphic tariffs in `FeeCalculator`.</li>
        <li><strong>Data Persistence Layer:</strong> Managed by `DatabaseManager`, handling atomic database updates and file-based transaction logging.</li>
    </ul>

    <div class="section-heading" id="heading-4-2">{mark('s42')}4.2 UML Diagrams</div>
    <p>
        The architectural design, operational data flows, and structural relationships within PLMS are formalized through the following diagrams.
    </p>

    <div class="subsection-heading" id="heading-4-2-1">{mark('s421')}4.2.1 Data Flow Diagrams</div>
    <p>
        Data Flow Diagrams (DFDs) illustrate the path of information from external entities through internal processes to persistent data stores.
    </p>

    <div class="figure-container">
        <img src="{img_dfd0}" alt="DFD Level 0 Context Diagram">
        <div class="figure-caption">{mark('fig_dfd')}Fig. 4.1: Data Flow Diagram (DFD) Level 0 &mdash; Context Level Diagram</div>
    </div>

    <p>
        Fig. 4.1 establishes the overall boundaries of the system, depicting external interactions between drivers, facility attendants, the central PLMS core, and relational storage.
    </p>

    <div class="figure-container" style="margin-top: 1.5em;">
        <img src="{img_dfd1}" alt="DFD Level 1 System Overview">
        <div class="figure-caption">Fig. 4.2: Data Flow Diagram (DFD) Level 1 &mdash; Subsystem Functional Flow</div>
    </div>

    <p>
        Fig. 4.2 breaks down the system into five primary functional processes: Vehicle Ingestion (1.0), Slot Allocation (2.0), UI Refresh (3.0), Tariff Computation (4.0), and Receipt Issuance (5.0).
    </p>

    <div class="figure-container" style="margin-top: 1.5em;">
        <img src="{img_dfd2}" alt="DFD Level 2 Allocation Breakdown">
        <div class="figure-caption">Fig. 4.3: Data Flow Diagram (DFD) Level 2 &mdash; Core Allocation Logic Breakdown</div>
    </div>

    <p>
        Fig. 4.3 details the inner sub-processes of the allocation engine, including duplicate check-in validation (2.1), type matching (2.2), floor priority (2.3), and atomic slot mutation (2.4).
    </p>

    <div class="page-break"></div>

    <div class="subsection-heading" id="heading-4-2-2">{mark('s422')}4.2.2 Sequence Diagram</div>
    <p>
        The Sequence Diagram (Fig. 4.4) depicts object lifelines and chronologically ordered messages exchanged across the UI, service, and database layers during vehicle check-in and check-out.
    </p>

    <div class="figure-container">
        <img src="{img_seq}" alt="Sequence Diagram">
        <div class="figure-caption">{mark('fig_seq')}Fig. 4.4: Sequence Diagram &mdash; Vehicle Check-In and Check-Out Operations</div>
    </div>

    <div class="subsection-heading" id="heading-4-2-3">{mark('s423')}4.2.3 Activity Diagrams</div>
    <p>
        Activity Diagrams (Fig. 4.5) map the step-by-step procedural control flow, decision branches, and terminal states for check-in and checkout workflows.
    </p>

    <div class="figure-container">
        <img src="{img_act}" alt="Activity Diagrams">
        <div class="figure-caption">{mark('fig_act')}Fig. 4.5: Activity Diagrams &mdash; Check-In and Check-Out Process Workflows</div>
    </div>

    <div class="page-break"></div>

    <div class="subsection-heading" id="heading-4-2-4">{mark('s424')}4.2.4 Class Diagram</div>
    <p>
        The UML Class Diagram (Fig. 4.6) models the static structural design of the software, showing generalization hierarchies, composition relations, and public contracts.
    </p>

    <div class="figure-container">
        <img src="{img_uml}" alt="UML Class Diagram">
        <div class="figure-caption">{mark('fig_uml')}Fig. 4.6: UML Class Diagram &mdash; Object-Oriented Architecture of PLMS</div>
    </div>

    <div class="subsection-heading">4.2.5 Operational Interface Screenshots</div>
    <p>
        The desktop interface provides operators with complete control over bay allocations and transaction histories:
    </p>

    <div class="figure-container">
        <img src="{img_ui_initial}" alt="Main Dashboard Initial State">
        <div class="figure-caption">Fig. 4.7: PLMS Desktop Dashboard &mdash; Initial Vacant State</div>
    </div>

    <div class="figure-container" style="margin-top: 1.5em;">
        <img src="{img_ui_active}" alt="Main Dashboard Active State">
        <div class="figure-caption">Fig. 4.8: PLMS Desktop Dashboard &mdash; Real-Time Multi-Floor Occupancy State</div>
    </div>

    <div class="figure-container" style="margin-top: 1.5em;">
        <img src="{img_ui_receipt}" alt="Receipt Dialog" style="max-width: 48%;">
        <div class="figure-caption">Fig. 4.9: Automated Exit Billing Receipt &amp; Invoice Modal</div>
    </div>
</div>

<!-- ============================================================== -->
<!-- CHAPTER 5: CONCLUSION                                         -->
<!-- ============================================================== -->
<div class="page-break" id="page-ch5">
    <div class="chapter-header">
        <div class="chapter-number">{mark('ch5')}CHAPTER 5</div>
        <div class="chapter-title">CONCLUSION</div>
    </div>

    <p>
        The <strong>Parking Lot Management System (PLMS)</strong> successfully achieves its core objective of delivering a dependable, automated, and object-oriented software solution for vehicular parking management. By leveraging the computational strength of <strong>Java</strong>, the cross-platform GUI capabilities of <strong>Java Swing</strong>, and a robust on-premise relational persistence model, the application guarantees high performance and zero dependency on vulnerable cloud services.
    </p>

    <p>
        The core strength of the system lies in its deterministic space allocation and automated financial computations. The implementation of dimension-aware slot matching ensures four-wheelers and two-wheelers are routed to appropriately categorized spaces without human confusion. Furthermore, the automated tariff engine, supported by dynamic method dispatch and partial-hour ceiling rules, eliminates manual arithmetic errors and protects revenue integrity.
    </p>

    <p>
        In addition, the interactive multi-floor dashboard provides attendants with instantaneous situational awareness through high-contrast visual cues, while the synchronized slot mutators prevent race conditions in high-throughput environments. Failsafe CSV audit logging ensures that complete transactional ledgers remain intact even under unexpected hardware restarts or database driver unavailability.
    </p>

    <p>
        In conclusion, PLMS provides an exemplary demonstration of pure Object-Oriented Programming principles applied to solving practical transportation challenges. The system enhances parking turnaround velocity, eradicates ticket disputes, and establishes a proven foundation for future integrations such as automated camera recognition and microcontroller barrier gates.
    </p>
</div>

<!-- ============================================================== -->
<!-- CHAPTER 6: REFERENCES                                         -->
<!-- ============================================================== -->
<div class="page-break" id="page-ch6">
    <div class="chapter-header">
        <div class="chapter-number">{mark('ch6')}CHAPTER 6</div>
        <div class="chapter-title">REFERENCES</div>
    </div>

    <p style="margin-bottom: 0.8em;">
        [1]. S. Kumar and R. Sharma, &ldquo;Automated Parking Systems using Object-Oriented Architecture,&rdquo; <em>IEEE Transactions on Intelligent Transportation Systems</em>, vol. 22, no. 4, pp. 2100&ndash;2112, Apr. 2021.
    </p>
    <p style="margin-bottom: 0.8em;">
        [2]. A. Patel, &ldquo;Design and Implementation of Relational Parking Databases,&rdquo; <em>International Journal of Computer Applications</em>, vol. 182, no. 15, pp. 34&ndash;40, 2020.
    </p>
    <p style="margin-bottom: 0.8em;">
        [3]. G. Wang, B. Zhang, and H. Luo, &ldquo;Intelligent Tariff Optimization in Commercial Parking Facilities,&rdquo; <em>IEEE Access</em>, vol. 11, pp. 34789&ndash;34800, 2023.
    </p>
    <p style="margin-bottom: 0.8em;">
        [4]. S. Banerjee, P. Maiti, and S. Biswas, &ldquo;Empowering Facility Operations Through Desktop Management Systems,&rdquo; <em>Journal of Systems and Software Engineering</em>, vol. 45, no. 2, pp. 112&ndash;124, 2024.
    </p>
    <p style="margin-bottom: 0.8em;">
        [5]. E. Gamma, R. Helm, R. Johnson, and J. Vlissides, <em>Design Patterns: Elements of Reusable Object-Oriented Software</em>. Boston, MA: Addison-Wesley, 1994.
    </p>
    <p style="margin-bottom: 0.8em;">
        [6]. Oracle Corporation, <em>Java SE 17 Documentation &amp; Swing GUI Architecture Specification</em>, 2021. [Online]. Available: https://docs.oracle.com/en/java/javase/17/
    </p>
</div>

</body>
</html>
"""
    return html

def build_pdf():
    print("============================================================")
    print("Building KTU / MGM B.Tech Project Preliminary Report PDF")
    print("============================================================")

    html_path = os.path.abspath('docs/report_document.html')
    raw_pdf_path = os.path.abspath('docs/raw_report_draft.pdf')
    final_pdf_path = os.path.abspath('PLMS_Project_Report.pdf')
    docs_pdf_path = os.path.abspath('docs/PLMS_Project_Report.pdf')

    chrome_candidates = [
        r"C:\Program Files\Google\Chrome\Application\chrome.exe",
        r"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe",
        r"C:\Program Files\Microsoft\Edge\Application\msedge.exe",
        r"C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe"
    ]
    browser_exe = None
    for cand in chrome_candidates:
        if os.path.exists(cand):
            browser_exe = cand
            break

    if not browser_exe:
        print("[ERROR] Neither Chrome nor Edge browser found for PDF compilation!")
        return

    print(f"Selected Browser Engine: {browser_exe}")

    print("Step 1: Generating Pass 1 HTML draft with embedded markers...")
    pass1_html = generate_report_html(page_dict=None, include_markers=True)
    with open(html_path, 'w', encoding='utf-8') as f:
        f.write(pass1_html)

    print("Step 2: Compiling Pass 1 PDF via headless browser...")
    cmd = [
        browser_exe,
        '--headless=new',
        '--disable-gpu',
        '--no-pdf-header-footer',
        f'--print-to-pdf={raw_pdf_path}',
        html_path
    ]
    res = subprocess.run(cmd, capture_output=True, text=True)
    if res.returncode != 0:
        print("Browser print error:", res.stderr)
        return

    print("Step 3: Extracting exact marker locations from generated PDF...")
    reader = pypdf.PdfReader(raw_pdf_path)
    total_pages = len(reader.pages)
    print(f"Total raw pages in draft: {total_pages}")

    marker_pages = {}
    pattern = re.compile(r'\[\s*\[\s*M\s*A\s*R\s*K\s*:\s*([a-zA-Z0-9_\s-]+?)\s*\]\s*\]', re.IGNORECASE)

    for idx, page in enumerate(reader.pages):
        text = page.extract_text() or ""
        matches = pattern.findall(text)
        for m in matches:
            m_low = m.replace(" ", "").lower()
            if m_low not in marker_pages:
                marker_pages[m_low] = idx

    print(f"Discovered {len(marker_pages)} markers in PDF:")
    for k, v in sorted(marker_pages.items()):
        print(f"  {k} -> Raw Page Index {v}")

    # Preliminary pages start numbering from Acknowledgement:
    # Page 0: Cover (no number)
    # Page 1: Declaration (no number)
    # Page 2: Certificate (no number)
    # Page 3: Acknowledgement (Roman i)
    ack_idx = marker_pages.get("ack", 3)
    ch1_idx = marker_pages.get("ch1", 8)
    print(f"Preliminary numbering starts at raw index: {ack_idx}")
    print(f"Chapter 1 starts at raw page index: {ch1_idx}")

    roman_numerals = ["", "i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x"]

    formatted_p = {}
    for key, idx in marker_pages.items():
        if idx < ch1_idx:
            # Roman numeral relative to ack_idx (ack = i = index 1 in roman_numerals)
            rel_idx = (idx - ack_idx) + 1
            if 1 <= rel_idx < len(roman_numerals):
                formatted_p[key] = roman_numerals[rel_idx]
            else:
                formatted_p[key] = f"p.{idx}"
        else:
            arabic_num = (idx - ch1_idx) + 1
            formatted_p[key] = str(arabic_num)

    for k in ["ack", "abs", "toc", "lof", "abb"]:
        if k not in formatted_p:
            formatted_p[k] = "i"

    print("Step 4: Re-generating clean HTML without marker tags (Pass 2)...")
    pass2_html = generate_report_html(formatted_p, include_markers=False)
    with open(html_path, 'w', encoding='utf-8') as f:
        f.write(pass2_html)

    print("Step 5: Compiling final clean Pass 2 PDF...")
    subprocess.run(cmd, capture_output=True, text=True)

    print("Step 6: Stamping professional academic page numbers (bottom-center in Times-Roman)...")
    reader2 = pypdf.PdfReader(raw_pdf_path)
    writer = pypdf.PdfWriter()

    for idx, page in enumerate(reader2.pages):
        packet = io.BytesIO()
        can = canvas.Canvas(packet, pagesize=A4)
        can.setFont("Times-Roman", 11)

        if idx < ack_idx:
            # Cover, Declaration, Certificate have NO page numbers
            text = ""
        elif idx < ch1_idx:
            rel_idx = (idx - ack_idx) + 1
            text = roman_numerals[rel_idx] if 1 <= rel_idx < len(roman_numerals) else ""
        else:
            arabic = (idx - ch1_idx) + 1
            text = str(arabic)

        if text:
            # Bottom center (A4 width = 595.27, height = 841.89)
            can.drawCentredString(A4[0] / 2.0, 42, text)

        can.save()
        packet.seek(0)

        num_pdf = pypdf.PdfReader(packet)
        page.merge_page(num_pdf.pages[0])
        writer.add_page(page)

    with open(final_pdf_path, 'wb') as f:
        writer.write(f)

    with open(docs_pdf_path, 'wb') as f:
        writer.write(f)

    try:
        if os.path.exists(raw_pdf_path):
            os.remove(raw_pdf_path)
    except Exception:
        pass

    file_size_mb = os.path.getsize(final_pdf_path) / (1024 * 1024)
    print("============================================================")
    print("SUCCESS: Institutional Project Preliminary Report PDF Generated!")
    print(f"Primary Output: {final_pdf_path}")
    print(f"Backup Output:  {docs_pdf_path}")
    print(f"Total Pages:    {len(writer.pages)}")
    print(f"File Size:      {file_size_mb:.2f} MB")
    print("============================================================")

if __name__ == '__main__':
    build_pdf()
