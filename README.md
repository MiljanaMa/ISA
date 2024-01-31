# ISA project team 35

U okviru projektnog zadatka iz predmeta Internet softverske arhitekture, implementirana je web aplikacija, koja predstavlja centralizovani informacioni sistem kompanija za nabavku medicinske opreme, preko kojeg će privatne bolnice moći da rezervišu i preuzmu opremu. Osnovna namena aplikacije je vođenje evidencije o zaposlenima, registrovanim kompanijama, rezervacijama opreme i terminima preuzimanja.

## Uputstvo za pokretanje aplikacije za rezervaciju medicinske opreme

Ovde se nalazi detaljno uputstvo o tome kako pokrenuti projekat za rezervisanje medicinske opreme na čistoj mašini. Projekat se sastoji od Spring Boot aplikacije za backend, Angular aplikacije za frontend, kao i dve eksterne Python skripte za simulaciju lokacija i ugovora. Za bazu podataka koristi se PostgreSQL, a za međusobnu komunikaciju aplikacija RabbitMQ.

### Instalacija neophodnih alata

1. **IntelliJ IDEA (Backend)**

   - Posetite [zvaničnu stranicu IntelliJ IDEA](https://www.jetbrains.com/idea/download/) i preuzmite Community verziju.
   - Instalirajte IntelliJ IDEA na vašem računaru.

2. **Visual Studio Code (Frontend)**

   - Preuzmite [Visual Studio Code](https://code.visualstudio.com/download) sa zvanične stranice.
   - Instalirajte Visual Studio Code na vašem računaru.

3. **Node.js i npm (Frontend)**

   - Posetite [zvaničnu stranicu Node.js](https://nodejs.org/) i preuzmite LTS verziju.
   - Instalirajte Node.js i npm na vašem računaru.

4. **Angular CLI (Frontend)**

   - Otvorite terminal (Command Prompt, PowerShell, ili Terminal na Linuxu/Mac-u) i izvršite komandu: `npm install -g @angular/cli`.

5. **PostgreSQL (Baza podataka)**

   - Preuzmite i instalirajte [PostgreSQL](https://www.postgresql.org/download/).
   - Kreirajte bazu podataka za projekat i nazovite je "isa". Baza će biti pokrenuta lokalno na podrazumevanom portu 5432.

6. **RabbitMQ (Message Broker)**

   - Preuzmite i instalirajte [RabbitMQ](https://www.rabbitmq.com/download.html).
   - Pokrenite Erlang pre RabbitMQ instalacije, a zatim pokrenite RabbitMQ (Management UI će biti dostupan na portu 15672, dok RabbitMQ radi na portu 5672).

7. **Python (Simulacija lokacija i ugovora)**
   - Posetite [zvaničnu stranicu Pythona](https://www.python.org/downloads/) i preuzmite Python verziju 3.x.
   - Instalirajte Python na vašem računaru.

### Kloniranje ili preuzimanje repozitorijuma

- Klonirajte ili preuzmite repozitorijum sa [https://github.com/MiljanaMa/ISA](https://github.com/MiljanaMa/ISA).

### Pokretanje Backend aplikacije (Spring Boot)

1. Otvorite IntelliJ IDEA.
2. Importujte projekat.
3. Podešavanje baze podataka: U `application.properties` fajlu postavite podatke za povezivanje sa PostgreSQL bazom podataka. Inicijalno, baza se zove "isa" i radi na portu 5432.
4. Pronađite klasu `Application` u `src/main/java` i pokrenite je.

### Pokretanje Frontend aplikacije (Angular)

1. Otvorite Visual Studio Code.
2. Otvorite folder `med-equip-system-front`.
3. Otvorite terminal u Visual Studio Code-u.
4. Izvršite komandu: `npm install` za instalaciju zavisnosti.
5. Nakon instalacije, izvršite komandu: `ng serve` za pokretanje Angular aplikacije.
6. Aplikacija će biti dostupna na `http://localhost:4200/` u vašem web pregledaču.

### Pokretanje Python skripti za simulaciju lokacija i ugovora

1. Otvorite terminal.
2. Navigirajte do foldera sa Python skriptama (`med-equip-system-contract-sim` i `med-equip-system-location-simulator`).
3. Instalirajte neophodne biblioteke iz `requirements.txt` komandom: `pip install -r requirements.txt`.
4. Pokrenite skripte komandama: `python main.py` za simulaciju slanja informacija o ugovorima i koordinatama glavnoj aplikaciji.
