import os

def create_svgs():
    os.makedirs('docs/images', exist_ok=True)

    # 0. MGM Group of Institutions Emblem / Logo
    logo_svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 320 320" width="320" height="320">
  <defs>
    <radialGradient id="goldGrad" cx="50%" cy="50%" r="50%" fx="40%" fy="40%">
      <stop offset="0%" stop-color="#fff8db"/>
      <stop offset="45%" stop-color="#e5b842"/>
      <stop offset="100%" stop-color="#b8860b"/>
    </radialGradient>
    <filter id="shadow" x="-10%" y="-10%" width="120%" height="120%">
      <feDropShadow dx="1" dy="2" stdDeviation="2" flood-opacity="0.25"/>
    </filter>
  </defs>

  <!-- Laurel Wreath -->
  <g fill="url(#goldGrad)" stroke="#8b6508" stroke-width="0.8">
    <!-- Left Leaves -->
    <ellipse cx="68" cy="145" rx="14" ry="7" transform="rotate(-40 68 145)"/>
    <ellipse cx="74" cy="115" rx="14" ry="7" transform="rotate(-25 74 115)"/>
    <ellipse cx="88" cy="88" rx="14" ry="7" transform="rotate(-10 88 88)"/>
    <ellipse cx="108" cy="68" rx="14" ry="7" transform="rotate(10 108 68)"/>
    <ellipse cx="134" cy="54" rx="13" ry="6" transform="rotate(30 134 54)"/>
    <ellipse cx="72" cy="178" rx="14" ry="7" transform="rotate(-60 72 178)"/>
    <ellipse cx="88" cy="210" rx="14" ry="7" transform="rotate(-80 88 210)"/>
    <ellipse cx="115" cy="238" rx="14" ry="7" transform="rotate(-105 115 238)"/>

    <!-- Right Leaves -->
    <ellipse cx="252" cy="145" rx="14" ry="7" transform="rotate(40 252 145)"/>
    <ellipse cx="246" cy="115" rx="14" ry="7" transform="rotate(25 246 115)"/>
    <ellipse cx="232" cy="88" rx="14" ry="7" transform="rotate(10 232 88)"/>
    <ellipse cx="212" cy="68" rx="14" ry="7" transform="rotate(-10 212 68)"/>
    <ellipse cx="186" cy="54" rx="13" ry="6" transform="rotate(-30 186 54)"/>
    <ellipse cx="248" cy="178" rx="14" ry="7" transform="rotate(60 248 178)"/>
    <ellipse cx="232" cy="210" rx="14" ry="7" transform="rotate(80 232 210)"/>
    <ellipse cx="205" cy="238" rx="14" ry="7" transform="rotate(105 205 238)"/>
  </g>

  <!-- Main Circular Medallion -->
  <circle cx="160" cy="148" r="78" fill="#ffffff" stroke="#b8860b" stroke-width="3.5" filter="url(#shadow)"/>
  <circle cx="160" cy="148" r="71" fill="none" stroke="#1a2b49" stroke-width="1.8" stroke-dasharray="3,2"/>
  <circle cx="160" cy="148" r="54" fill="#faf8f5" stroke="#b8860b" stroke-width="2"/>

  <!-- Curved Text: MGM GROUP OF INSTITUTIONS -->
  <path id="textArc" d="M 98,144 A 62,62 0 0,0 222,144" fill="none"/>
  <text font-family="Times New Roman, serif" font-size="9.5" font-weight="bold" fill="#1a2b49" letter-spacing="1.2">
    <textPath href="#textArc" startOffset="50%" text-anchor="middle">
      MGM GROUP OF INSTITUTIONS
    </textPath>
  </text>

  <!-- Central Torch Rays -->
  <g stroke="#d4af37" stroke-width="1.5" opacity="0.8">
    <line x1="160" y1="108" x2="160" y2="96"/>
    <line x1="148" y1="112" x2="140" y2="102"/>
    <line x1="172" y1="112" x2="180" y2="102"/>
    <line x1="138" y1="120" x2="128" y2="114"/>
    <line x1="182" y1="120" x2="192" y2="114"/>
  </g>

  <!-- Flame -->
  <path d="M 160,102 C 156,112 154,116 160,124 C 166,116 164,112 160,102 Z" fill="#e11d48"/>
  <path d="M 160,107 C 158,113 157,116 160,121 C 163,116 162,113 160,107 Z" fill="#fbbf24"/>

  <!-- Open Book -->
  <g filter="url(#shadow)">
    <!-- Left Page -->
    <path d="M 160,152 C 148,144 134,142 124,144 L 124,166 C 134,164 148,166 160,174 Z" fill="#ffffff" stroke="#b8860b" stroke-width="1.5"/>
    <line x1="129" y1="150" x2="154" y2="150" stroke="#cbd5e1" stroke-width="1"/>
    <line x1="129" y1="155" x2="154" y2="155" stroke="#cbd5e1" stroke-width="1"/>
    <line x1="129" y1="160" x2="154" y2="160" stroke="#cbd5e1" stroke-width="1"/>

    <!-- Right Page -->
    <path d="M 160,152 C 172,144 186,142 196,144 L 196,166 C 186,164 172,166 160,174 Z" fill="#ffffff" stroke="#b8860b" stroke-width="1.5"/>
    <line x1="166" y1="150" x2="191" y2="150" stroke="#cbd5e1" stroke-width="1"/>
    <line x1="166" y1="155" x2="191" y2="155" stroke="#cbd5e1" stroke-width="1"/>
    <line x1="166" y1="160" x2="191" y2="160" stroke="#cbd5e1" stroke-width="1"/>
  </g>

  <!-- Foundation Year: 1994 -->
  <rect x="136" y="182" width="48" height="18" rx="3" fill="#ffffff" stroke="#b8860b" stroke-width="1.5"/>
  <text x="160" y="195" font-family="Times New Roman, serif" font-size="11" font-weight="bold" text-anchor="middle" fill="#1a2b49">1994</text>

  <!-- Motto Banner: तमसो मा ज्योतिर्गमय -->
  <path d="M 92,214 C 112,210 140,212 160,212 C 180,212 208,210 228,214 L 222,230 C 204,226 180,227 160,227 C 140,227 116,226 98,230 Z" fill="url(#goldGrad)" stroke="#8b6508" stroke-width="1.2" filter="url(#shadow)"/>
  <polygon points="92,214 98,230 84,224" fill="#8b6508"/>
  <polygon points="228,214 222,230 236,224" fill="#8b6508"/>

  <text x="160" y="224" font-family="'Segoe UI', Arial, sans-serif" font-size="9" font-weight="bold" text-anchor="middle" fill="#1a2b49">तमसो मा ज्योतिर्गमय</text>

  <!-- Stars -->
  <g fill="#b8860b">
    <polygon points="160,238 162,243 167,243 163,246 164,251 160,248 156,251 157,246 153,243 158,243"/>
    <polygon points="144,239 145.5,243 150,243 146.5,245.5 147.5,249.5 144,247 140.5,249.5 141.5,245.5 138,243 142.5,243"/>
    <polygon points="176,239 177.5,243 182,243 178.5,245.5 179.5,249.5 176,247 172.5,249.5 173.5,245.5 170,243 174.5,243"/>
  </g>
</svg>'''
    with open('docs/images/mgm_college_logo.svg', 'w', encoding='utf-8') as f:
        f.write(logo_svg)

    # 1. System Architecture Diagram
    arch_svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 800 480" width="100%" height="auto">
  <defs>
    <filter id="shadow" x="-5%" y="-5%" width="110%" height="110%">
      <feDropShadow dx="2" dy="2" stdDeviation="3" flood-opacity="0.15"/>
    </filter>
    <marker id="arrow" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
      <path d="M 0 1 L 10 5 L 0 9 z" fill="#1e3a8a"/>
    </marker>
    <marker id="arrow-green" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
      <path d="M 0 1 L 10 5 L 0 9 z" fill="#15803d"/>
    </marker>
  </defs>

  <rect width="800" height="480" fill="#f8fafc" rx="8" stroke="#cbd5e1" stroke-width="1.5"/>

  <!-- Tier 1: Presentation Layer -->
  <rect x="40" y="30" width="720" height="115" rx="6" fill="#eff6ff" stroke="#3b82f6" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="55" y="55" font-family="Times New Roman" font-size="14" font-weight="bold" fill="#1e3a8a">PRESENTATION LAYER (JAVA SWING GUI)</text>
  
  <rect x="70" y="70" width="190" height="60" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2"/>
  <text x="165" y="95" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#1e293b">MainFrame</text>
  <text x="165" y="115" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#64748b">KPI Cards &amp; Action Panels</text>

  <rect x="290" y="70" width="200" height="60" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2"/>
  <text x="390" y="95" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#1e293b">SlotPanel Grid</text>
  <text x="390" y="115" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#64748b">Visual Multi-Floor Layout</text>

  <rect x="520" y="70" width="210" height="60" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2"/>
  <text x="625" y="95" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#1e293b">ReceiptDialog</text>
  <text x="625" y="115" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#64748b">Checkout Invoice Modal</text>

  <!-- Arrows 1 -> 2 -->
  <line x1="165" y1="145" x2="165" y2="185" stroke="#1e3a8a" stroke-width="2" marker-end="url(#arrow)"/>
  <line x1="390" y1="145" x2="390" y2="185" stroke="#1e3a8a" stroke-width="2" marker-end="url(#arrow)"/>
  <line x1="625" y1="145" x2="625" y2="185" stroke="#1e3a8a" stroke-width="2" marker-end="url(#arrow)"/>

  <!-- Tier 2: Business Logic / Service Layer -->
  <rect x="40" y="185" width="720" height="135" rx="6" fill="#f0fdf4" stroke="#22c55e" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="55" y="210" font-family="Times New Roman" font-size="14" font-weight="bold" fill="#14532d">BUSINESS LOGIC &amp; DOMAIN SERVICE LAYER</text>

  <rect x="70" y="225" width="200" height="75" rx="4" fill="#ffffff" stroke="#86efac" stroke-width="1.2"/>
  <text x="170" y="250" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#1e293b">ParkingManager</text>
  <text x="170" y="270" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#64748b">Slot Allocation Orchestrator</text>
  <text x="170" y="285" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#047857">Composition Pillar</text>

  <rect x="290" y="225" width="200" height="75" rx="4" fill="#ffffff" stroke="#86efac" stroke-width="1.2"/>
  <text x="390" y="250" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#1e293b">Vehicle (Car &amp; Bike)</text>
  <text x="390" y="270" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#64748b">Abstract Contract &amp; Subclasses</text>
  <text x="390" y="285" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#047857">Inheritance &amp; Polymorphism</text>

  <rect x="510" y="225" width="220" height="75" rx="4" fill="#ffffff" stroke="#86efac" stroke-width="1.2"/>
  <text x="620" y="250" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#1e293b">FeeCalculator &amp; Slot</text>
  <text x="620" y="270" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#64748b">Duration Tariffs &amp; Slot State</text>
  <text x="620" y="285" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#047857">Encapsulation &amp; SoC</text>

  <!-- Arrows 2 -> 3 -->
  <line x1="400" y1="320" x2="400" y2="355" stroke="#15803d" stroke-width="2" marker-end="url(#arrow-green)"/>

  <!-- Tier 3: Persistence Layer -->
  <rect x="40" y="355" width="720" height="100" rx="6" fill="#fefce8" stroke="#eab308" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="55" y="380" font-family="Times New Roman" font-size="14" font-weight="bold" fill="#713f12">DATA PERSISTENCE &amp; STORAGE LAYER</text>

  <rect x="120" y="395" width="250" height="48" rx="4" fill="#ffffff" stroke="#fde047" stroke-width="1.2"/>
  <text x="245" y="418" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#1e293b">DatabaseManager (JDBC)</text>
  <text x="245" y="433" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#64748b">MySQL / SQLite Relational Engine</text>

  <rect x="430" y="395" width="250" height="48" rx="4" fill="#ffffff" stroke="#fde047" stroke-width="1.2"/>
  <text x="555" y="418" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#1e293b">Audit Transaction Logger</text>
  <text x="555" y="433" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#64748b">CSV Fallback &amp; Operational Ledger</text>
</svg>'''
    with open('docs/images/system_architecture.svg', 'w', encoding='utf-8') as f:
        f.write(arch_svg)

    # 2. DFD Level 0 (Context Diagram)
    dfd0_svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 780 340" width="100%" height="auto">
  <defs>
    <filter id="shadow" x="-5%" y="-5%" width="110%" height="110%">
      <feDropShadow dx="2" dy="2" stdDeviation="3" flood-opacity="0.12"/>
    </filter>
    <marker id="arr" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
      <path d="M 0 1 L 10 5 L 0 9 z" fill="#334155"/>
    </marker>
  </defs>

  <rect width="780" height="340" fill="#ffffff" rx="6" stroke="#cbd5e1" stroke-width="1"/>

  <!-- External Entity: Driver -->
  <rect x="40" y="80" width="140" height="80" rx="4" fill="#f1f5f9" stroke="#475569" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="110" y="115" font-family="Times New Roman" font-size="14" font-weight="bold" text-anchor="middle" fill="#0f172a">Vehicle Driver</text>
  <text x="110" y="135" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#475569">(Customer)</text>

  <!-- External Entity: Parking Attendant / Admin -->
  <rect x="40" y="200" width="140" height="80" rx="4" fill="#f1f5f9" stroke="#475569" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="110" y="235" font-family="Times New Roman" font-size="14" font-weight="bold" text-anchor="middle" fill="#0f172a">Parking Operator</text>
  <text x="110" y="255" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#475569">(Facility Admin)</text>

  <!-- Central Process: 0.0 PLMS -->
  <circle cx="390" cy="170" r="85" fill="#eff6ff" stroke="#2563eb" stroke-width="2" filter="url(#shadow)"/>
  <text x="390" y="155" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#1e40af">0.0</text>
  <text x="390" y="175" font-family="Times New Roman" font-size="15" font-weight="bold" text-anchor="middle" fill="#1e3a8a">Parking Lot</text>
  <text x="390" y="195" font-family="Times New Roman" font-size="15" font-weight="bold" text-anchor="middle" fill="#1e3a8a">Management System</text>

  <!-- Data Store: Relational DB / CSV -->
  <path d="M 600,120 L 730,120 A 15,15 0 0,1 730,220 L 600,220 Z" fill="#fefce8" stroke="#ca8a04" stroke-width="1.5" filter="url(#shadow)"/>
  <line x1="620" y1="120" x2="620" y2="220" stroke="#ca8a04" stroke-width="1.5"/>
  <text x="670" y="165" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#854d0e">D1: PLMS Store</text>
  <text x="670" y="185" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#a16207">(Relational DB)</text>

  <!-- Flows Driver <-> Process -->
  <line x1="180" y1="105" x2="305" y2="140" stroke="#334155" stroke-width="1.5" marker-end="url(#arr)"/>
  <text x="235" y="112" font-family="Times New Roman" font-size="10.5" fill="#1e293b">1. Vehicle Entry &amp; Plate</text>

  <line x1="305" y1="165" x2="180" y2="135" stroke="#334155" stroke-width="1.5" marker-end="url(#arr)"/>
  <text x="230" y="162" font-family="Times New Roman" font-size="10.5" fill="#1e293b">2. Ticket &amp; Assigned Slot</text>

  <line x1="180" y1="150" x2="305" y2="190" stroke="#334155" stroke-width="1.5" marker-end="url(#arr)"/>
  <text x="245" y="180" font-family="Times New Roman" font-size="10.5" fill="#1e293b">3. Departure / Settlement</text>

  <!-- Flows Operator <-> Process -->
  <line x1="180" y1="240" x2="310" y2="215" stroke="#334155" stroke-width="1.5" marker-end="url(#arr)"/>
  <text x="235" y="248" font-family="Times New Roman" font-size="10.5" fill="#1e293b">Query Status / Reset</text>

  <line x1="320" y1="235" x2="180" y2="265" stroke="#334155" stroke-width="1.5" marker-end="url(#arr)"/>
  <text x="240" y="272" font-family="Times New Roman" font-size="10.5" fill="#1e293b">Live Occupancy Grid</text>

  <!-- Flows Process <-> DB -->
  <line x1="475" y1="155" x2="600" y2="155" stroke="#334155" stroke-width="1.5" marker-end="url(#arr)"/>
  <text x="535" y="145" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">Persist Bookings</text>

  <line x1="600" y1="185" x2="475" y2="185" stroke="#334155" stroke-width="1.5" marker-end="url(#arr)"/>
  <text x="535" y="202" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">Slot States &amp; Tariffs</text>
</svg>'''
    with open('docs/images/dfd_level_0.svg', 'w', encoding='utf-8') as f:
        f.write(dfd0_svg)

    # 3. DFD Level 1 (System Overview)
    dfd1_svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 800 520" width="100%" height="auto">
  <defs>
    <filter id="shadow" x="-5%" y="-5%" width="110%" height="110%">
      <feDropShadow dx="2" dy="2" stdDeviation="3" flood-opacity="0.12"/>
    </filter>
    <marker id="ar" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
      <path d="M 0 1 L 10 5 L 0 9 z" fill="#1e293b"/>
    </marker>
  </defs>

  <rect width="800" height="520" fill="#ffffff" rx="6" stroke="#cbd5e1" stroke-width="1"/>

  <!-- External Entity: Driver -->
  <rect x="30" y="30" width="120" height="60" rx="4" fill="#f1f5f9" stroke="#475569" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="90" y="65" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#0f172a">Driver</text>

  <!-- Process 1.0: Vehicle Ingestion -->
  <circle cx="270" cy="60" r="45" fill="#eff6ff" stroke="#2563eb" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="270" y="55" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1d4ed8">1.0</text>
  <text x="270" y="70" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">Vehicle Ingestion</text>

  <!-- Process 2.0: Slot Allocation Engine -->
  <circle cx="490" cy="60" r="45" fill="#eff6ff" stroke="#2563eb" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="490" y="55" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1d4ed8">2.0</text>
  <text x="490" y="70" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">Slot Allocation</text>

  <!-- Process 3.0: Real-time UI Display -->
  <circle cx="690" cy="180" r="45" fill="#eff6ff" stroke="#2563eb" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="690" y="175" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1d4ed8">3.0</text>
  <text x="690" y="190" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">UI Grid Refresh</text>

  <!-- Process 4.0: Tariff & Duration Calculation -->
  <circle cx="270" cy="380" r="45" fill="#eff6ff" stroke="#2563eb" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="270" y="375" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1d4ed8">4.0</text>
  <text x="270" y="390" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">Fee Calculator</text>

  <!-- Process 5.0: Exit Clearance & Receipt -->
  <circle cx="490" cy="380" r="45" fill="#eff6ff" stroke="#2563eb" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="490" y="375" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1d4ed8">5.0</text>
  <text x="490" y="390" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">Receipt Issuance</text>

  <!-- Data Stores in Center -->
  <path d="M 330,190 L 450,190" stroke="#ca8a04" stroke-width="2"/>
  <path d="M 330,230 L 450,230" stroke="#ca8a04" stroke-width="2"/>
  <text x="390" y="215" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#854d0e">D1: Slots Inventory</text>

  <path d="M 330,270 L 450,270" stroke="#ca8a04" stroke-width="2"/>
  <path d="M 330,310 L 450,310" stroke="#ca8a04" stroke-width="2"/>
  <text x="390" y="295" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#854d0e">D2: Active Bookings</text>

  <!-- Interconnecting Arrows -->
  <line x1="150" y1="60" x2="225" y2="60" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="185" y="52" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#334155">Plate &amp; Type</text>

  <line x1="315" y1="60" x2="445" y2="60" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="380" y="52" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#334155">Validated Vehicle</text>

  <line x1="490" y1="105" x2="430" y2="190" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <line x1="410" y1="190" x2="470" y2="105" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="475" y="145" font-family="Times New Roman" font-size="9.5" fill="#334155">Query / Assign Slot</text>

  <line x1="535" y1="60" x2="650" y2="150" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="615" y="95" font-family="Times New Roman" font-size="9.5" fill="#334155">Status = OCCUPIED</text>

  <line x1="490" y1="105" x2="410" y2="270" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="420" y="250" font-family="Times New Roman" font-size="9.5" fill="#334155">Record Check-In</text>

  <line x1="90" y1="90" x2="240" y2="350" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="130" y="225" font-family="Times New Roman" font-size="10" fill="#334155">Exit Request (Plate/Ticket)</text>

  <line x1="330" y1="290" x2="295" y2="335" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="270" y="300" font-family="Times New Roman" font-size="9.5" fill="#334155">Read Entry Time</text>

  <line x1="315" y1="380" x2="445" y2="380" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="380" y="372" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#334155">Calculated Fee</text>

  <line x1="535" y1="380" x2="670" y2="225" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="625" y="320" font-family="Times New Roman" font-size="9.5" fill="#334155">Vacate Slot (AVAILABLE)</text>

  <line x1="490" y1="425" x2="110" y2="475" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <polyline points="110,475 90,475 90,95" stroke="#1e293b" stroke-width="1.5" fill="none"/>
  <text x="260" y="470" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#334155">Issued Receipt Modal &amp; Gate Clearance</text>
</svg>'''
    with open('docs/images/dfd_level_1.svg', 'w', encoding='utf-8') as f:
        f.write(dfd1_svg)

    # 4. DFD Level 2 (Core Logic Breakdown)
    dfd2_svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 800 480" width="100%" height="auto">
  <defs>
    <filter id="shadow" x="-5%" y="-5%" width="110%" height="110%">
      <feDropShadow dx="2" dy="2" stdDeviation="3" flood-opacity="0.12"/>
    </filter>
    <marker id="ar" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
      <path d="M 0 1 L 10 5 L 0 9 z" fill="#1e293b"/>
    </marker>
  </defs>

  <rect width="800" height="480" fill="#ffffff" rx="6" stroke="#cbd5e1" stroke-width="1"/>

  <rect x="250" y="20" width="300" height="35" rx="4" fill="#f1f5f9" stroke="#94a3b8" stroke-width="1"/>
  <text x="400" y="43" font-family="Times New Roman" font-size="14" font-weight="bold" text-anchor="middle" fill="#1e293b">DFD Level 2: Core Allocation Logic Breakdown</text>

  <!-- Process 2.1: Duplicate Plate Check -->
  <circle cx="160" cy="140" r="48" fill="#eff6ff" stroke="#2563eb" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="160" y="132" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1d4ed8">2.1</text>
  <text x="160" y="148" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">Duplicate Guard</text>

  <!-- Process 2.2: Slot Type Matcher -->
  <circle cx="400" cy="140" r="48" fill="#eff6ff" stroke="#2563eb" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="400" y="132" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1d4ed8">2.2</text>
  <text x="400" y="148" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">Type Matching</text>

  <!-- Process 2.3: Floor Priority Locator -->
  <circle cx="640" cy="140" r="48" fill="#eff6ff" stroke="#2563eb" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="640" y="132" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1d4ed8">2.3</text>
  <text x="640" y="148" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">Floor Priority</text>

  <!-- Process 2.4: Atomic Slot Mutator -->
  <circle cx="400" cy="330" r="50" fill="#f0fdf4" stroke="#16a34a" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="400" y="322" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#15803d">2.4</text>
  <text x="400" y="338" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">Atomic Mutator</text>
  <text x="400" y="352" font-family="Times New Roman" font-size="9" text-anchor="middle" fill="#15803d">(assignVehicle)</text>

  <!-- Process 2.5: Ticket Generator -->
  <circle cx="640" cy="330" r="50" fill="#fefce8" stroke="#ca8a04" stroke-width="1.5" filter="url(#shadow)"/>
  <text x="640" y="322" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#ca8a04">2.5</text>
  <text x="640" y="338" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#1e293b">Ticket Token</text>
  <text x="640" y="352" font-family="Times New Roman" font-size="9" text-anchor="middle" fill="#854d0e">(TKT-XXXXX)</text>

  <!-- Connectors -->
  <line x1="208" y1="140" x2="352" y2="140" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="280" y="132" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#475569">Unregistered Plate</text>

  <line x1="448" y1="140" x2="592" y2="140" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="520" y="132" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#475569">Car/Bike Validated</text>

  <line x1="640" y1="188" x2="450" y2="310" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="570" y="245" font-family="Times New Roman" font-size="10" fill="#475569">Available Candidate (F1 -&gt; F2)</text>

  <line x1="450" y1="330" x2="590" y2="330" stroke="#1e293b" stroke-width="1.5" marker-end="url(#ar)"/>
  <text x="520" y="322" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#475569">Slot Locked</text>
</svg>'''
    with open('docs/images/dfd_level_2.svg', 'w', encoding='utf-8') as f:
        f.write(dfd2_svg)

    # 5. Sequence Diagram (Vehicle Check-In & Check-Out)
    seq_svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 820 540" width="100%" height="auto">
  <defs>
    <filter id="shadow" x="-5%" y="-5%" width="110%" height="110%">
      <feDropShadow dx="2" dy="2" stdDeviation="2" flood-opacity="0.12"/>
    </filter>
    <marker id="ar" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
      <path d="M 0 1 L 10 5 L 0 9 z" fill="#1e293b"/>
    </marker>
  </defs>

  <rect width="820" height="540" fill="#ffffff" rx="6" stroke="#cbd5e1" stroke-width="1"/>

  <!-- Lifelines -->
  <!-- 1. Operator / UI -->
  <rect x="30" y="30" width="100" height="35" rx="4" fill="#f1f5f9" stroke="#475569" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="80" y="52" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#0f172a">:MainFrame</text>
  <line x1="80" y1="65" x2="80" y2="500" stroke="#94a3b8" stroke-width="1" stroke-dasharray="4,4"/>

  <!-- 2. ParkingManager -->
  <rect x="180" y="30" width="120" height="35" rx="4" fill="#eff6ff" stroke="#2563eb" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="240" y="52" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#1e40af">:ParkingManager</text>
  <line x1="240" y1="65" x2="240" y2="500" stroke="#94a3b8" stroke-width="1" stroke-dasharray="4,4"/>

  <!-- 3. ParkingSlot -->
  <rect x="350" y="30" width="110" height="35" rx="4" fill="#f0fdf4" stroke="#16a34a" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="405" y="52" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#15803d">:ParkingSlot</text>
  <line x1="405" y1="65" x2="405" y2="500" stroke="#94a3b8" stroke-width="1" stroke-dasharray="4,4"/>

  <!-- 4. FeeCalculator -->
  <rect x="500" y="30" width="115" height="35" rx="4" fill="#fefce8" stroke="#ca8a04" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="557" y="52" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#854d0e">:FeeCalculator</text>
  <line x1="557" y1="65" x2="557" y2="500" stroke="#94a3b8" stroke-width="1" stroke-dasharray="4,4"/>

  <!-- 5. DatabaseManager -->
  <rect x="655" y="30" width="135" height="35" rx="4" fill="#faf5ff" stroke="#9333ea" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="722" y="52" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#6b21a8">:DatabaseManager</text>
  <line x1="722" y1="65" x2="722" y2="500" stroke="#94a3b8" stroke-width="1" stroke-dasharray="4,4"/>

  <!-- Section 1: Check-In Sequence -->
  <rect x="40" y="85" width="740" height="18" fill="#f1f5f9" stroke="#cbd5e1"/>
  <text x="410" y="98" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#334155">VEHICLE CHECK-IN WORKFLOW</text>

  <line x1="80" y1="120" x2="240" y2="120" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>
  <text x="160" y="113" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">1. checkInVehicle(vehicle, null)</text>

  <line x1="240" y1="145" x2="405" y2="145" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>
  <text x="322" y="138" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">2. assignVehicle(vehicle)</text>

  <line x1="405" y1="170" x2="240" y2="170" stroke="#1e293b" stroke-width="1.4" stroke-dasharray="3,3" marker-end="url(#ar)"/>
  <text x="322" y="163" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">3. success = true</text>

  <line x1="240" y1="195" x2="722" y2="195" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>
  <text x="480" y="188" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">4. recordCheckIn(booking)</text>

  <line x1="240" y1="220" x2="80" y2="220" stroke="#1e293b" stroke-width="1.4" stroke-dasharray="3,3" marker-end="url(#ar)"/>
  <text x="160" y="213" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">5. Booking ticket created</text>

  <!-- Section 2: Check-Out Sequence -->
  <rect x="40" y="255" width="740" height="18" fill="#f1f5f9" stroke="#cbd5e1"/>
  <text x="410" y="268" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#334155">VEHICLE CHECK-OUT &amp; BILLING WORKFLOW</text>

  <line x1="80" y1="295" x2="240" y2="295" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>
  <text x="160" y="288" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">6. checkOutVehicle(slotOrPlate)</text>

  <line x1="240" y1="325" x2="557" y2="325" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>
  <text x="398" y="318" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">7. calculateFee(vehicle, duration)</text>

  <line x1="557" y1="350" x2="240" y2="350" stroke="#1e293b" stroke-width="1.4" stroke-dasharray="3,3" marker-end="url(#ar)"/>
  <text x="398" y="343" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">8. totalFee computed (hourly ceiling)</text>

  <line x1="240" y1="380" x2="405" y2="380" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>
  <text x="322" y="373" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">9. vacate()</text>

  <line x1="405" y1="405" x2="240" y2="405" stroke="#1e293b" stroke-width="1.4" stroke-dasharray="3,3" marker-end="url(#ar)"/>
  <text x="322" y="398" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">10. status = AVAILABLE</text>

  <line x1="240" y1="435" x2="722" y2="435" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>
  <text x="480" y="428" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">11. recordCheckOut(booking)</text>

  <line x1="240" y1="465" x2="80" y2="465" stroke="#1e293b" stroke-width="1.4" stroke-dasharray="3,3" marker-end="url(#ar)"/>
  <text x="160" y="458" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#1e293b">12. Open ReceiptDialog</text>
</svg>'''
    with open('docs/images/sequence_diagram.svg', 'w', encoding='utf-8') as f:
        f.write(seq_svg)

    # 6. Activity Diagrams (Check-In & Check-Out Flows)
    act_svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 800 520" width="100%" height="auto">
  <defs>
    <filter id="shadow" x="-5%" y="-5%" width="110%" height="110%">
      <feDropShadow dx="2" dy="2" stdDeviation="2" flood-opacity="0.12"/>
    </filter>
    <marker id="ar" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse">
      <path d="M 0 1 L 10 5 L 0 9 z" fill="#1e293b"/>
    </marker>
  </defs>

  <rect width="800" height="520" fill="#ffffff" rx="6" stroke="#cbd5e1" stroke-width="1"/>

  <!-- Left Column: Check-In Activity Flow -->
  <rect x="30" y="20" width="350" height="480" rx="6" fill="#f8fafc" stroke="#cbd5e1"/>
  <text x="205" y="45" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#1e3a8a">ACTIVITY: VEHICLE CHECK-IN</text>

  <!-- Start Node -->
  <circle cx="205" cy="70" r="10" fill="#0f172a"/>
  <line x1="205" y1="80" x2="205" y2="105" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 1 -->
  <rect x="130" y="105" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="205" y="125" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Enter Plate &amp; Category</text>
  <line x1="205" y1="137" x2="205" y2="160" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Decision: Duplicate Check -->
  <polygon points="205,160 255,185 205,210 155,185" fill="#fefce8" stroke="#ca8a04" stroke-width="1.2"/>
  <text x="205" y="188" font-family="Times New Roman" font-size="9" text-anchor="middle" fill="#854d0e">Parked?</text>

  <line x1="255" y1="185" x2="310" y2="185" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>
  <text x="280" y="177" font-family="Times New Roman" font-size="9" fill="#dc2626">Yes</text>
  <rect x="270" y="210" width="90" height="28" rx="4" fill="#fee2e2" stroke="#ef4444" stroke-width="1"/>
  <text x="315" y="228" font-family="Times New Roman" font-size="9" text-anchor="middle" fill="#991b1b">Show Alert</text>

  <line x1="205" y1="210" x2="205" y2="245" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>
  <text x="215" y="228" font-family="Times New Roman" font-size="9" fill="#15803d">No</text>

  <!-- Step 2: Slot Search -->
  <rect x="130" y="245" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="205" y="265" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Find Available Slot</text>
  <line x1="205" y1="277" x2="205" y2="305" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 3: Atomic Lock & Ticket -->
  <rect x="130" y="305" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="205" y="325" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Assign &amp; Issue Ticket</text>
  <line x1="205" y1="337" x2="205" y2="365" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 4: Persist -->
  <rect x="130" y="365" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="205" y="385" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Save Booking to DB</text>
  <line x1="205" y1="397" x2="205" y2="425" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 5: Refresh UI -->
  <rect x="130" y="425" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="205" y="445" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Update Slot Grid (Red)</text>
  <line x1="205" y1="457" x2="205" y2="480" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- End Node -->
  <circle cx="205" cy="488" r="8" fill="none" stroke="#0f172a" stroke-width="1.5"/>
  <circle cx="205" cy="488" r="5" fill="#0f172a"/>


  <!-- Right Column: Check-Out Activity Flow -->
  <rect x="420" y="20" width="350" height="480" rx="6" fill="#f8fafc" stroke="#cbd5e1"/>
  <text x="595" y="45" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#1e3a8a">ACTIVITY: VEHICLE CHECK-OUT</text>

  <!-- Start Node -->
  <circle cx="595" cy="70" r="10" fill="#0f172a"/>
  <line x1="595" y1="80" x2="595" y2="105" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 1 -->
  <rect x="520" y="105" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="595" y="125" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Input Slot ID or Plate</text>
  <line x1="595" y1="137" x2="595" y2="165" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 2 -->
  <rect x="520" y="165" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="595" y="185" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Compute Duration</text>
  <line x1="595" y1="197" x2="595" y2="225" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 3: Dynamic Fee Calculation -->
  <rect x="520" y="225" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="595" y="245" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Calculate Tariff (Ceiling)</text>
  <line x1="595" y1="257" x2="595" y2="285" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 4: Vacate Slot -->
  <rect x="520" y="285" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="595" y="305" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Vacate Bay (AVAILABLE)</text>
  <line x1="595" y1="317" x2="595" y2="345" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 5: Mark Paid & Persist -->
  <rect x="520" y="345" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="595" y="365" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Update DB (Status=PAID)</text>
  <line x1="595" y1="377" x2="595" y2="405" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- Step 6: Render Receipt Dialog -->
  <rect x="520" y="405" width="150" height="32" rx="4" fill="#ffffff" stroke="#60a5fa" stroke-width="1.2" filter="url(#shadow)"/>
  <text x="595" y="425" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">Show ReceiptDialog</text>
  <line x1="595" y1="437" x2="595" y2="465" stroke="#1e293b" stroke-width="1.4" marker-end="url(#ar)"/>

  <!-- End Node -->
  <circle cx="595" cy="475" r="8" fill="none" stroke="#0f172a" stroke-width="1.5"/>
  <circle cx="595" cy="475" r="5" fill="#0f172a"/>
</svg>'''
    with open('docs/images/activity_diagrams.svg', 'w', encoding='utf-8') as f:
        f.write(act_svg)

    # 7. ER Diagram (Matching schema.sql)
    er_svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 780 400" width="100%" height="auto">
  <defs>
    <filter id="shadow" x="-5%" y="-5%" width="110%" height="110%">
      <feDropShadow dx="2" dy="2" stdDeviation="3" flood-opacity="0.12"/>
    </filter>
  </defs>

  <rect width="780" height="400" fill="#ffffff" rx="6" stroke="#cbd5e1" stroke-width="1"/>

  <!-- Entity 1: VEHICLES -->
  <rect x="40" y="50" width="200" height="200" rx="4" fill="#f8fafc" stroke="#3b82f6" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="40" y="50" width="200" height="35" rx="4" fill="#3b82f6"/>
  <text x="140" y="73" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#ffffff">VEHICLES</text>
  <text x="50" y="105" font-family="Times New Roman" font-size="12" font-weight="bold" fill="#1e293b">PK  license_plate (VARCHAR)</text>
  <line x1="45" y1="115" x2="235" y2="115" stroke="#cbd5e1" stroke-width="1"/>
  <text x="50" y="135" font-family="Times New Roman" font-size="11.5" fill="#334155">    vehicle_type (CAR | BIKE)</text>
  <text x="50" y="160" font-family="Times New Roman" font-size="11.5" fill="#334155">    owner_name (VARCHAR)</text>
  <text x="50" y="185" font-family="Times New Roman" font-size="11.5" fill="#334155">    contact_number (VARCHAR)</text>
  <text x="50" y="210" font-family="Times New Roman" font-size="11.5" fill="#334155">    registered_at (TIMESTAMP)</text>

  <!-- Entity 2: BOOKINGS -->
  <rect x="290" y="50" width="200" height="260" rx="4" fill="#f8fafc" stroke="#10b981" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="290" y="50" width="200" height="35" rx="4" fill="#10b981"/>
  <text x="390" y="73" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#ffffff">BOOKINGS</text>
  <text x="300" y="105" font-family="Times New Roman" font-size="12" font-weight="bold" fill="#1e293b">PK  ticket_id (VARCHAR)</text>
  <line x1="295" y1="115" x2="485" y2="115" stroke="#cbd5e1" stroke-width="1"/>
  <text x="300" y="135" font-family="Times New Roman" font-size="11.5" font-weight="bold" fill="#047857">FK  license_plate (VARCHAR)</text>
  <text x="300" y="160" font-family="Times New Roman" font-size="11.5" font-weight="bold" fill="#047857">FK  slot_id (VARCHAR)</text>
  <text x="300" y="185" font-family="Times New Roman" font-size="11.5" fill="#334155">    entry_time (TIMESTAMP)</text>
  <text x="300" y="210" font-family="Times New Roman" font-size="11.5" fill="#334155">    exit_time (TIMESTAMP)</text>
  <text x="300" y="235" font-family="Times New Roman" font-size="11.5" fill="#334155">    duration_minutes (INT)</text>
  <text x="300" y="260" font-family="Times New Roman" font-size="11.5" fill="#334155">    fee_amount (DECIMAL)</text>
  <text x="300" y="285" font-family="Times New Roman" font-size="11.5" fill="#334155">    payment_status (VARCHAR)</text>

  <!-- Entity 3: PARKING_SLOTS -->
  <rect x="540" y="50" width="200" height="180" rx="4" fill="#f8fafc" stroke="#8b5cf6" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="540" y="50" width="200" height="35" rx="4" fill="#8b5cf6"/>
  <text x="640" y="73" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#ffffff">PARKING_SLOTS</text>
  <text x="550" y="105" font-family="Times New Roman" font-size="12" font-weight="bold" fill="#1e293b">PK  slot_id (VARCHAR)</text>
  <line x1="545" y1="115" x2="735" y2="115" stroke="#cbd5e1" stroke-width="1"/>
  <text x="550" y="135" font-family="Times New Roman" font-size="11.5" fill="#334155">    slot_type (CAR | BIKE)</text>
  <text x="550" y="160" font-family="Times New Roman" font-size="11.5" fill="#334155">    status (AVAILABLE | OCCUPIED)</text>
  <text x="550" y="185" font-family="Times New Roman" font-size="11.5" fill="#334155">    floor_number (INT)</text>

  <!-- Relationship Lines -->
  <line x1="240" y1="140" x2="290" y2="140" stroke="#1e293b" stroke-width="2"/>
  <line x1="248" y1="133" x2="248" y2="147" stroke="#1e293b" stroke-width="2"/>
  <line x1="254" y1="133" x2="254" y2="147" stroke="#1e293b" stroke-width="2"/>
  <circle cx="275" cy="140" r="3" fill="#ffffff" stroke="#1e293b" stroke-width="1.5"/>
  <line x1="278" y1="140" x2="290" y2="134" stroke="#1e293b" stroke-width="1.5"/>
  <line x1="278" y1="140" x2="290" y2="146" stroke="#1e293b" stroke-width="1.5"/>
  <text x="265" y="128" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#0f172a">generates (1:N)</text>

  <line x1="540" y1="140" x2="490" y2="140" stroke="#1e293b" stroke-width="2"/>
  <line x1="532" y1="133" x2="532" y2="147" stroke="#1e293b" stroke-width="2"/>
  <line x1="526" y1="133" x2="526" y2="147" stroke="#1e293b" stroke-width="2"/>
  <circle cx="505" cy="140" r="3" fill="#ffffff" stroke="#1e293b" stroke-width="1.5"/>
  <line x1="502" y1="140" x2="490" y2="134" stroke="#1e293b" stroke-width="1.5"/>
  <line x1="502" y1="140" x2="490" y2="146" stroke="#1e293b" stroke-width="1.5"/>
  <text x="515" y="128" font-family="Times New Roman" font-size="10.5" text-anchor="middle" fill="#0f172a">assigned_to (1:N)</text>

  <rect x="180" y="335" width="420" height="45" rx="4" fill="#f1f5f9" stroke="#94a3b8" stroke-width="1"/>
  <text x="390" y="353" font-family="Times New Roman" font-size="11" font-weight="bold" text-anchor="middle" fill="#334155">RELATIONAL INTEGRITY RULES</text>
  <text x="390" y="368" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#475569">1 Vehicle has Many Bookings | 1 Parking Slot hosts Many Historical Bookings</text>
</svg>'''
    with open('docs/images/er_diagram.svg', 'w', encoding='utf-8') as f:
        f.write(er_svg)

    # 8. UML Class Diagram
    uml_svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 820 540" width="100%" height="auto">
  <defs>
    <filter id="shadow" x="-5%" y="-5%" width="110%" height="110%">
      <feDropShadow dx="2" dy="2" stdDeviation="3" flood-opacity="0.12"/>
    </filter>
    <marker id="uml-inherit" viewBox="0 0 12 12" refX="11" refY="6" markerWidth="9" markerHeight="9" orient="auto">
      <polygon points="0,0 12,6 0,12" fill="#ffffff" stroke="#1e293b" stroke-width="1.5"/>
    </marker>
    <marker id="uml-comp" viewBox="0 0 14 10" refX="0" refY="5" markerWidth="11" markerHeight="8" orient="auto">
      <polygon points="0,5 6,1 12,5 6,9" fill="#1e293b" stroke="#1e293b" stroke-width="1.2"/>
    </marker>
    <marker id="uml-agg" viewBox="0 0 14 10" refX="0" refY="5" markerWidth="11" markerHeight="8" orient="auto">
      <polygon points="0,5 6,1 12,5 6,9" fill="#ffffff" stroke="#1e293b" stroke-width="1.2"/>
    </marker>
  </defs>

  <rect width="820" height="540" fill="#ffffff" rx="6" stroke="#cbd5e1" stroke-width="1"/>

  <!-- Class: Vehicle (Abstract) -->
  <rect x="40" y="40" width="220" height="150" fill="#f8fafc" stroke="#1e293b" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="40" y="40" width="220" height="35" fill="#f1f5f9"/>
  <text x="150" y="55" font-family="Times New Roman" font-size="11" font-style="italic" text-anchor="middle" fill="#475569">&lt;&lt;abstract&gt;&gt;</text>
  <text x="150" y="70" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#0f172a">Vehicle</text>
  <line x1="40" y1="75" x2="260" y2="75" stroke="#1e293b" stroke-width="1"/>
  <text x="48" y="92" font-family="Times New Roman" font-size="11" fill="#1e293b">- licensePlate: String</text>
  <text x="48" y="108" font-family="Times New Roman" font-size="11" fill="#1e293b">- vehicleType: VehicleType</text>
  <text x="48" y="124" font-family="Times New Roman" font-size="11" fill="#1e293b">- entryTime: LocalDateTime</text>
  <line x1="40" y1="132" x2="260" y2="132" stroke="#1e293b" stroke-width="1"/>
  <text x="48" y="150" font-family="Times New Roman" font-size="11" font-style="italic" fill="#0f172a">+ calculateBaseHourlyRate()*: double</text>
  <text x="48" y="166" font-family="Times New Roman" font-size="11" fill="#1e293b">+ getLicensePlate(): String</text>

  <!-- Class: Car -->
  <rect x="30" y="240" width="115" height="95" fill="#f8fafc" stroke="#1e293b" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="30" y="240" width="115" height="25" fill="#f1f5f9"/>
  <text x="87" y="257" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#0f172a">Car</text>
  <line x1="30" y1="265" x2="145" y2="265" stroke="#1e293b" stroke-width="1"/>
  <text x="35" y="280" font-family="Times New Roman" font-size="10.5" fill="#1e293b">- numberOfDoors: int</text>
  <line x1="30" y1="288" x2="145" y2="288" stroke="#1e293b" stroke-width="1"/>
  <text x="35" y="304" font-family="Times New Roman" font-size="10.5" fill="#0f172a">+ calculateRate(): 20.0</text>
  <text x="35" y="322" font-family="Times New Roman" font-size="10.5" fill="#1e293b">+ getDoors(): int</text>

  <!-- Class: Bike -->
  <rect x="160" y="240" width="120" height="95" fill="#f8fafc" stroke="#1e293b" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="160" y="240" width="120" height="25" fill="#f1f5f9"/>
  <text x="220" y="257" font-family="Times New Roman" font-size="12" font-weight="bold" text-anchor="middle" fill="#0f172a">Bike</text>
  <line x1="160" y1="265" x2="280" y2="265" stroke="#1e293b" stroke-width="1"/>
  <text x="165" y="280" font-family="Times New Roman" font-size="10.5" fill="#1e293b">- helmetStorage: bool</text>
  <line x1="160" y1="288" x2="280" y2="288" stroke="#1e293b" stroke-width="1"/>
  <text x="165" y="304" font-family="Times New Roman" font-size="10.5" fill="#0f172a">+ calculateRate(): 10.0</text>
  <text x="165" y="322" font-family="Times New Roman" font-size="10.5" fill="#1e293b">+ hasStorage(): bool</text>

  <line x1="87" y1="240" x2="87" y2="215" stroke="#1e293b" stroke-width="1.5"/>
  <line x1="220" y1="240" x2="220" y2="215" stroke="#1e293b" stroke-width="1.5"/>
  <line x1="87" y1="215" x2="220" y2="215" stroke="#1e293b" stroke-width="1.5"/>
  <line x1="150" y1="215" x2="150" y2="190" stroke="#1e293b" stroke-width="1.5" marker-end="url(#uml-inherit)"/>

  <!-- Class: ParkingSlot -->
  <rect x="310" y="40" width="210" height="170" fill="#f8fafc" stroke="#1e293b" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="310" y="40" width="210" height="25" fill="#f1f5f9"/>
  <text x="415" y="58" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#0f172a">ParkingSlot</text>
  <line x1="310" y1="65" x2="520" y2="65" stroke="#1e293b" stroke-width="1"/>
  <text x="318" y="82" font-family="Times New Roman" font-size="11" fill="#1e293b">- slotId: String</text>
  <text x="318" y="98" font-family="Times New Roman" font-size="11" fill="#1e293b">- allowedType: VehicleType</text>
  <text x="318" y="114" font-family="Times New Roman" font-size="11" fill="#1e293b">- floorNumber: int</text>
  <text x="318" y="130" font-family="Times New Roman" font-size="11" fill="#1e293b">- status: SlotStatus</text>
  <text x="318" y="146" font-family="Times New Roman" font-size="11" fill="#1e293b">- currentVehicle: Vehicle</text>
  <line x1="310" y1="154" x2="520" y2="154" stroke="#1e293b" stroke-width="1"/>
  <text x="318" y="171" font-family="Times New Roman" font-size="11" fill="#1e293b">+ assignVehicle(v: Vehicle): bool</text>
  <text x="318" y="188" font-family="Times New Roman" font-size="11" fill="#1e293b">+ vacate(): Vehicle</text>
  <text x="318" y="204" font-family="Times New Roman" font-size="11" fill="#1e293b">+ isAvailable(): boolean</text>

  <line x1="310" y1="110" x2="260" y2="110" stroke="#1e293b" stroke-width="1.5" marker-start="url(#uml-agg)"/>
  <text x="285" y="102" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#334155">1..0..1</text>

  <!-- Class: ParkingManager -->
  <rect x="560" y="40" width="220" height="175" fill="#f8fafc" stroke="#1e293b" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="560" y="40" width="220" height="25" fill="#f1f5f9"/>
  <text x="670" y="58" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#0f172a">ParkingManager</text>
  <line x1="560" y1="65" x2="780" y2="65" stroke="#1e293b" stroke-width="1"/>
  <text x="568" y="82" font-family="Times New Roman" font-size="11" fill="#1e293b">- slots: List&lt;ParkingSlot&gt;</text>
  <text x="568" y="98" font-family="Times New Roman" font-size="11" fill="#1e293b">- feeCalculator: FeeCalculator</text>
  <text x="568" y="114" font-family="Times New Roman" font-size="11" fill="#1e293b">- dbManager: DatabaseManager</text>
  <line x1="560" y1="122" x2="780" y2="122" stroke="#1e293b" stroke-width="1"/>
  <text x="568" y="140" font-family="Times New Roman" font-size="11" fill="#1e293b">+ checkInVehicle(v, s): Booking</text>
  <text x="568" y="157" font-family="Times New Roman" font-size="11" fill="#1e293b">+ checkOutVehicle(id): Booking</text>
  <text x="568" y="174" font-family="Times New Roman" font-size="11" fill="#1e293b">+ findAvailableSlot(t): Slot</text>
  <text x="568" y="191" font-family="Times New Roman" font-size="11" fill="#1e293b">+ getAllSlots(): List&lt;Slot&gt;</text>

  <line x1="560" y1="110" x2="520" y2="110" stroke="#1e293b" stroke-width="1.5" marker-start="url(#uml-comp)"/>
  <text x="540" y="102" font-family="Times New Roman" font-size="10" text-anchor="middle" fill="#334155">1..*</text>

  <!-- Class: Booking -->
  <rect x="560" y="270" width="220" height="150" fill="#f8fafc" stroke="#1e293b" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="560" y="270" width="220" height="25" fill="#f1f5f9"/>
  <text x="670" y="288" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#0f172a">Booking</text>
  <line x1="560" y1="295" x2="780" y2="295" stroke="#1e293b" stroke-width="1"/>
  <text x="568" y="312" font-family="Times New Roman" font-size="11" fill="#1e293b">- ticketId: String</text>
  <text x="568" y="328" font-family="Times New Roman" font-size="11" fill="#1e293b">- licensePlate: String</text>
  <text x="568" y="344" font-family="Times New Roman" font-size="11" fill="#1e293b">- entryTime, exitTime: LocalDateTime</text>
  <text x="568" y="360" font-family="Times New Roman" font-size="11" fill="#1e293b">- feeAmount: double</text>
  <line x1="560" y1="368" x2="780" y2="368" stroke="#1e293b" stroke-width="1"/>
  <text x="568" y="386" font-family="Times New Roman" font-size="11" fill="#1e293b">+ completeBooking(exit, fee): void</text>
  <text x="568" y="404" font-family="Times New Roman" font-size="11" fill="#1e293b">+ getDurationMinutes(): long</text>

  <line x1="670" y1="215" x2="670" y2="270" stroke="#1e293b" stroke-width="1.5" stroke-dasharray="4 4"/>
  <polygon points="670,270 666,262 674,262" fill="#1e293b"/>
  <text x="685" y="245" font-family="Times New Roman" font-size="10.5" fill="#475569">&lt;&lt;creates&gt;&gt;</text>

  <!-- Class: FeeCalculator -->
  <rect x="320" y="270" width="190" height="110" fill="#f8fafc" stroke="#1e293b" stroke-width="1.5" filter="url(#shadow)"/>
  <rect x="320" y="270" width="190" height="25" fill="#f1f5f9"/>
  <text x="415" y="288" font-family="Times New Roman" font-size="13" font-weight="bold" text-anchor="middle" fill="#0f172a">FeeCalculator</text>
  <line x1="320" y1="295" x2="510" y2="295" stroke="#1e293b" stroke-width="1"/>
  <text x="328" y="312" font-family="Times New Roman" font-size="11" fill="#1e293b">- minimumFee: double</text>
  <line x1="320" y1="320" x2="510" y2="320" stroke="#1e293b" stroke-width="1"/>
  <text x="328" y="338" font-family="Times New Roman" font-size="11" fill="#1e293b">+ calculateFee(v, d): double</text>
  <text x="328" y="356" font-family="Times New Roman" font-size="11" fill="#1e293b">+ getMinimumFee(): double</text>

  <line x1="560" y1="180" x2="480" y2="270" stroke="#1e293b" stroke-width="1.5" stroke-dasharray="4 4"/>
  <polygon points="480,270 488,266 483,261" fill="#1e293b"/>
  <text x="535" y="235" font-family="Times New Roman" font-size="10.5" fill="#475569">&lt;&lt;uses&gt;&gt;</text>

  <rect x="60" y="470" width="700" height="40" rx="4" fill="#f1f5f9" stroke="#94a3b8" stroke-width="1"/>
  <text x="410" y="494" font-family="Times New Roman" font-size="11" text-anchor="middle" fill="#1e293b">UML Relationships: ──▷ Generalization/Inheritance  |  ──◆ Composition  |  ──◇ Aggregation  |  ┈▷ Dependency</text>
</svg>'''
    with open('docs/images/uml_class_diagram.svg', 'w', encoding='utf-8') as f:
        f.write(uml_svg)

    print("All SVGs created successfully in docs/images/")

if __name__ == '__main__':
    create_svgs()
