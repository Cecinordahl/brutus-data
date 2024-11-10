# Brutus Data

En fullstack-applikasjon utviklet for Brutus A/S for å håndtere og presentere brukerdata på en oversiktlig måte. 
Applikasjonen gir HR-avdelingen enkel tilgang til dataene, med funksjonalitet for å søke, filtrere, og presentere statistikk.

## Innhold

- [Beskrivelse](#beskrivelse)
- [Teknologivalg](#teknologivalg)
- [Installasjon og oppsett](#installasjon-og-oppsett)
- [Bruk](#bruk)
- [API-endepunkter](#api-endepunkter)
- [Kjøre applikasjonen](#kjøre-applikasjonen)

## Beskrivelse

Denne applikasjonen gir Brutus A/S mulighet til å:
- Laste opp og lagre brukerdata.
- Presentere dataene i en brukergrensesnitt med funksjonalitet for søk og filtrering.
- Se statistikk om datasettet.

Applikasjonen består av en backend skrevet i Java (Spring Boot) og en frontend utviklet i React med TypeScript, som samhandler via REST API-er.

## Teknologivalg

Prosjektet bruker følgende teknologier:

- **Backend med REST API**: Java og Spring Boot  
  Spring Boot ble valgt på grunn av sin evne til å bygge produksjonsklare REST API-er raskt og effektivt. 
Det tilbyr god støtte for databaser, enkel konfigurasjon og rask utvikling av mikrotjenester.
- **Frontend-applikasjon**: React med TypeScript  
  TypeScript ble valgt som språk for frontend-applikasjonen fordi det gir fordelen av statisk typing, noe som hjelper med å unngå feil ved bruk av API-data. TypeScript gir bedre autokomplettering og gjør koden mer robust og vedlikeholdbar, spesielt i større prosjekter. React ble valgt for sin fleksibilitet, raske responstid, og gode mulighet for gjenbruk av komponenter.
- **Database**: PostgreSQL  
  PostgreSQL ble valgt for sin stabilitet og pålitelighet i håndtering av store datamengder. Med støtte for avanserte datatyper og indeksering er PostgreSQL et godt valg for applikasjoner som krever kompleks datahåndtering.





### Vurderinger under utvikling
I denne seksjonen beskrives noen av de viktigste vurderingene og beslutningene som ble tatt under utviklingen av prosjektet. Dette inkluderer valg av datalastingsmetode, sikkerhetshensyn, og teknologivalg.

#### Hvorfor disse spesifikke teknologiene?
- **Maven vs Gradle**: Maven ble valgt som byggesystem for prosjektet fordi det er enkelt å bruke, og strukturen i XML-format gjør det lett å lese og håndtere for mindre og middels store prosjekter. Maven har vært standarden i Java-miljøet i mange år og er kjent for sin pålitelighet og forutsigbarhet.
- **Spring Data JPA vs JDBC**: Spring Data JPA ble valgt over JDBC på grunn av det høyere abstraksjonsnivået det gir. Med Spring Data JPA kan CRUD-operasjoner (Create, Read, Update, Delete) håndteres uten å skrive mye SQL, og det gir en enklere datatilgang og lagring gjennom objekt-relasjonsmapping. Dette gjør koden kortere og lettere å vedlikeholde.
- **PostgreSQL vs Andre Databaser**: PostgreSQL ble valgt som database på grunn av dens robuste funksjonalitet og støtte for komplekse dataspørringer. Sammenlignet med alternativer som SQLite (som mangler støtte for avanserte spørringer) og MySQL (som mangler noen funksjoner for komplekse datasett), er PostgreSQL godt egnet for produksjonsmiljøer og gir god ytelse og skalerbarhet.

**Valg av datalastingsmetode**:
For å laste inn eksempeldata til databasen, ble det valgt å inkludere CSV-filen i prosjektet og bruke en data-loader som leser CSV-en ved oppstart. Denne tilnærmingen gjør det enkelt for andre brukere å klone prosjektet og få databasefylt med eksempeldata uten ekstra steg. Andre alternativer som ble vurdert var å konvertere CSV-filen til et SQL-innskript eller å lage et separat migreringsverktøy. Å konvertere til SQL-skript gir rask datalasting, men gir mindre fleksibilitet om CSV-formatet endres, mens et separat migreringsverktøy gir ryddig separasjon, men er mer komplisert å sette opp og krever manuell kjøring.


**Merk om sikkerhet for produksjonsmiljø**:  
Dette prosjektet bruker et enkelt passord for enkelhet i lokal utvikling.
Hvis det tas i bruk i et produksjonsmiljø, er det viktig å bruke sterke, unike passord for alle database- og systemtilganger.
I tillegg bør alle sensitive opplysninger krypteres og lagres i miljøvariabler eller i et verktøy for hemmelighetsforvaltning,
i stedet for å være hardkodet eller lagret som ren tekst i konfigurasjonsfiler.





## Bruk

### Last opp data
For å laste opp data kan du bruke et POST-endepunkt eller opplaste CSV-data fra frontend (hvis implementert). Dataene vil bli lagret i PostgreSQL-databasen.

### Filtrering og søk
Applikasjonen tilbyr muligheter for filtrering og søk gjennom brukergrensesnittet, som gjør det mulig for HR-avdelingen å se spesifikke brukere eller grupper av brukere basert på ønskede kriterier.

### Statistikkside
Frontend-applikasjonen viser statistikk over datasettet, som kan inkludere total antall brukere, gjennomsnittsalder, osv. Denne informasjonen oppdateres kontinuerlig basert på data i databasen.

## API-endepunkter

| HTTP Method | Endpoint           | Beskrivelse                    |
|-------------|---------------------|--------------------------------|
| POST        | `/api/users`       | Laster opp ny brukerdata       |
| GET         | `/api/users`       | Henter alle brukere            |
| GET         | `/api/users/{id}`  | Henter bruker basert på ID     |
| PATCH       | `/api/users/{id}`  | Oppdaterer en bruker basert på ID |
| DELETE      | `/api/users/{id}`  | Sletter en bruker basert på ID |


## Kjøre applikasjonen

1. Start PostgreSQL-databasen.
2. Start backend med Spring Boot.
3. Start frontend-applikasjonen.
4. Gå til `http://localhost:3000` for å begynne å bruke applikasjonen.

## Installasjon og Oppsett

### Forutsetninger
- [Java 23](https://jdk.java.net/23/) eller nyere
- [Node.js](https://nodejs.org/) og npm
- [PostgreSQL](https://www.postgresql.org/download/) database
- Maven

### Kloning av prosjektet
Klon repository:
```bash
git clone https://github.com/ditt-brukernavn/brutus-data-app.git
cd brutus-data-app
```

### Konfigurer database
1. Opprett en PostgreSQL-database kalt `brutusdb`.
2. Oppdater `src/main/resources/application.properties` med dine databaseinnstillinger:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/brutusdb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

### Bygg og kjør backend
Gå til rotmappen av prosjektet og bygg Spring Boot-applikasjonen:
```bash
./mvnw clean install
./mvnw spring-boot:run
```   
Backend-serveren vil kjøre på http://localhost:8080.

### Installer og start frontend
Gå til frontend-mappen, installer avhengigheter og start frontend-serveren:
```bash
cd frontend
npm install
npm start
```
Frontend-applikasjonen vil kjøre på http://localhost:3000.

## Fremtidig Funksjonalitet
- Lagre minAge og maxAge som miljø variabler som frontend kan hente via api siden disse er brukt mange steder og endringer kan føre til feil.