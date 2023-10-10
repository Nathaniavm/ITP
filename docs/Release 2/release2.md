# Release 2
<em>Dette prosjektet krever Java version **16**, maven versjon **4.0.0**, maven compiler.source **16** og javafx version **17.0.2**. </em>

## Gruppesamarbeid og arbeidsvaner 
Gruppesamarbeidet fortsetter å fungere svært godt. Siden release 1 har gruppen endret litt på hvordan det arbeides, hovedsakelig ved å følge standarden der man jobber to og to sammen. Ved noen tilfeller har vi likevel jobbet hjemmefra, der man heller har valgt å jobbe for seg selv, da dette har vært enklere.Når gruppen jobbet sammen satt vi ingen faste gruppemedlemmer til å alltid jobbe med hverandre, slik at man ikke låste seg til å være avhengig av denne personen "til enhver tid". Dermed ble det bedre flyt i arbeidet.

Arbeidet ble fordelt ut ifra hvilke <em>issues </em> som måtte løses, og hva som trengtes å jobbes med med tanke på tidsfrister. De to som ønsket å jobbe sammen med en "issue" eller oppgave gikk sammen om det. Derfra ble det jobbet systematisk mot å nå de ulike milepælene og tidsfristene som ble satt, samtidig som vi videreutviklet applikasjonen. 

En endring som ble gjort siden release 1 var å ha en <em>Development Branch</em> i git repository. Dette er branchen som skal inneholde den nyeste versjonen under arbeidet, altså der vi dytter endringene våre etter å ha gjort oss ferdig i en branch. <em>Master-branch</em> er de endelige versjonene ved hver release, og den versjonen som man er helt sikker på at fungerer og er riktig til enhver tid. Å jobbe med denne standarden har fungert godt og gjort arbeidet mer oversiktlig, i tillegg til å sikre at vi alltid har en backup.


## Implementeringer ved release 2
### FXML

Ved release 2 har gruppen lagd alle grunnleggende FXML-filer, og lagt dem inn i prosjektet. Disse filene virke som en form for grunnmur for prosjektet, der funksjonaliteten gruppen ønsker å ha i applikasjonen blir realisert. Alle disse filene har ble koblet opp med kontrolleren, slik at det er mulig å bevege seg mellom alle filene. Ettersom FXML-filene ble lagt inn, har nå flere FXML-tester blitt laget for å teste funksjonaliteten til applikasjonen. 

For gjøre det enklere å dokumentere FXML-testene, ble en ny JUnit versjon lagt til. Med denne versjonen kan vi legge til <em>@Displayname</em> i test-klassene. 

### Filhåndtering
Persistens til fil med <em>JSON</em> er nå på plass. Gruppen valgte å bruke <em>Jackson</em>-biblioteket for å implementere denne logikken. <em>Jackson</em> gjør det mulig å skrive all informasjonen vi ønsker til en JSON-fil. <em>Jackson</em> sørger for å få dette på plass på en strukturert og oversiktlig måte, noe som også var en stor grunn til at gruppen bestemte seg for å benytte seg at akkurat dette biblioteket. 

Informasjonen gruppen ser på som nødvendig:
- Profile: 
    - Navn
    - E-post 
    - Telefonnummer 
    - Passord 
    - Kontoer 
    - Regninger
- Account: 
    - Kontonavn 
    - Tilknyttet profil 
    - Saldo 
    - Kontonummer 
    - Bankkort 
- Bankkort: 
    - Kortholder 
    - Kontonummer
    - Kortnummer
- Regninger: 
    - Beløp
    - Navn på regning 
    - Navn på den som skal betales til
    - Konto som skal betales til
        - Konto som skal betales til inneholder tilsvarende informasjon som punktene over


Skjermdump av et eksempel på hvordan <em>JSON</em>-fila kan se ut:
![](../../images/Release2/EksempelAvJsonFila.png)

Når det skrives til fil, lagrer <em>Jackson</em>-biblioteket informasjonen ut ifra hvilke <em>getter</em>-metoder man har i de ulike klassene. <em>Jackson</em>-biblioteket leser så fra fila og konstruerer objekter av typen til de ulike klassene ut ifra informasjonen lagret i fil. For å sørge for de ulike klassene finner riktig objekt, setter vi en id for noen av objektene. For eksempel har profile-klassen en id som skal sørge for at <em>Jackson</em> knytter kontoer, bankkort og regninger til riktig profil. Uten ID-ene vil relasjonen forsvinne. Tilsvarende logikk er brukt for de andre klassene, der dette var nødvendig.

De ulike klassene har ulike relasjoner med hverandre:
- Profile har en en-til-mange-relasjon med Account
- Profile har en en-til-mange-relasjon med Bill
- Bill har en en-til-to-relasjon med Account
- Account har en en-til-en-relasjon til BankCard

### Teknisk

En bug som ble med inn i release 1 var at <em>mvn javafx:run</em> og <em>mvn test</em> ikke ville kjøre på grunn av en feil i en av testene. Den kjørte likevel dersom man kommenterte ut testen. Dette er en bug som klarte å komme med ved forrige release. Dette har vi nå rettet opp i. 

Ser man videre på det tekniske har vi fått satt opp både checkstyle og spotsbugs. Selv om de i praksis utfører samme oppgave, satt vi opp begge for å prøve å finne ut av hvilken gruppen syntes var best. 

Videre har prosjektet blitt gjort eclipse che klart. 

### Mappestruktur 
Mappestrukturen har gått gjennom en stor endring. Ved release 1 hadde ikke prosjektet noen <em>module-info</em>. Dette er nå på plass, men som følge av dette ble mappestrukturen endret ganske mye. 

Ved release 2 ser mappestrukturen slik ut:


## Videre arbeid
### Teknisk
Siden vi begynte har vi jobbet i "egne" branch, der nye implementeringer gjøres inni disse. Deretter dyttes endringene til <em>development branch</em>, for så å begynne på nye implementeringer i den samme "egne" branchen. Det har likevel vært forskjell i gruppen på hvordan disse branches brukes. Noen lager en ny branch for hver issue eller oppgave, for å gjøre branches mer issue-spesifikke eller oppgavespesifikke. Dette er en praksis som er god og ha, og vi har nå blitt enige om at alle skal følge denne praksisen. Dette er en standard som ofte følges i det virkelige liv, og vi vil derfor også følge denne standarden. 

En annen ting gruppen skal sørge for at er helt på plass er å huske å ha med <em>Co-Author</em>. Det har vært tilfeller der man har glemt å ha det med på slutten av <em>commit</em>-meldinger, selv om man har jobbet sammen med en annen. 

### Applikasjonen
Videre i utviklingen skal vi få på plass en oversikt over transaksjoner i applikasjonen. Dette skal gjøres ved å sikre persistens til fil med <em>JSON</em>, der JSON-fila inneholder oversikt over transaksjoner. Dette skal applikasjonen så lese, og så vise fram for brukeren.

Det skal også lages flere tester som tester både logikk og FXML mens applikasjonen videreutvikles og ny funksjonalitet settes inn. 

I applikasjonen skal det være mulig å bestille nytt kort, sperre kort og slette bruker. Dette skal vi prøve å få plass ved neste release. 