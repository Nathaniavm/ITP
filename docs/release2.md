# Release 2
<em >Dette prosjektet krever Java version 17.0.8 og maven versjon 3.9.4 </em>

## Gruppesamarbeid
Gruppesamarbeidet fortsetter å fungere svært godt. Siden release 1 har gruppen endret litt på hvordan det arbeides, hovedsakelig ved å følge standarden der man jobber to og to sammen. Gruppen satt ingen spesifikke gruppemedlemmer til å alltid jobbe med hverandre, slik at man ikke låste seg til å være avhengig av denne personen "til enhver tid". Arbeidet ble fordelt ut ifra hvilke <em>issues </em> som måtte løses, og hva som trengtes å jobbes med med tanke på tidsfrister. De to som ønsket å jobbe med en "issue" eller oppgave sammen gjorde det. Derfra jobbet vi systematisk mot å nå de ulike milepælene og tidsfristene som ble satt, samtidig som vi videreutviklet applikasjonen. 

En endring som ble gjort siden release 1 var å ha en <em>Development Branch</em> i git repository. Dette er branchen som skal inneholde den nyeste versjonen under arbeidet, altså der vi dytter endringene våre etter å ha gjort oss ferdig i en branch. <em>Master-branch</em> er de endelige versjonene ved hver release, og den versjonen som man er helt sikker på at fungerer og er riktig til enhver tid. Å jobbe med denne standarden har fungert godt og gjort arbeidet mer oversiktlig, samt å sikre at vi alltid har en backup.



## Implementeringer ved release 2
Ved release 2 har gruppen lagd alle grunnleggende FXML-filer, og lagd dem inn i prosjektet. Disse vil virke som en form for grunnmur, der operasjoene og funksjonaliteten gruppen ønsker å ha i applikasjonen kommer til live. Alle disse filene har blitt koblet opp med kontrolleren, slik at det er mulig å bevege seg mellom alle filene. Ettersom FXML-filene ble lagt inn, ble flere FXML-tester laget for å tenke funksjonaliteten. 

For gjøre det enklere å dokumentere FXML-testene, ble en ny JUnit versjon lagt til. Med denne versjonen kan vi legge til <em>@Displayname</em> i test-klassene. 

En bug som ble med inn i release 1 var at <em>mvn javafx:run</em> og <em>mvn test</em> ikke ville kjøre på grunn av en feil i en av testene. Den kjørte likevel dersom man kommenterte ut testen. Dette er en bug som klarte å komme med ved forrige release. Dette har vi nå rettet opp i. 

SKRIV OM FILHÅNDTERING OG MAPPESTRUKTUR. ECLIPSE CHE, CHECHSTYLE/SPOTBUGS



## Videre arbeid
Stort sett har vi arbeidet i "egne" branch, der nye implementeringer gjøres inni denne. Deretter dyttes endringene til <em>development branch</em>, for så å begynne på nye implementeringer i den samme "egne" branchen. Fra nå av vil vi lage nye branch for hver issue eller oppgave, for å gjøre branches mer issue-spesifikke eller oppgavespesifikke. Dette er en standard som ofte følges, og vi vil derfor også følge denne standarden. 