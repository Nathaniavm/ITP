# Release 3
<em>Dette prosjektet krever Java version **16**, maven versjon **4.0.0**, maven compiler.source **16** og javafx version **17.0.2**. 

Eclipse-che link: https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2315/gr2315?new

Merk: Det er mulig at eclipse-che krever at man har en gyldig personal access token.</em>

## Gruppesamarbeid
Nå som prosjektet nærmer seg slutt, kommer nå en oppsummering av gruppesamarbeidet.

Gruppesamarbeidet har fungert svært godt gjennom hele prosjektet. I henhold til gruppekontrakten har alle gruppemedlemmer klart å holde seg til det man ble enige om. Gruppen ble enige om å ha én felles "work-session" i uken, men etterhvert som utviklingen utvidet seg og man innså at det ble hensiktsmessig med flere møter, økte dette dermed til tre ganger i uken. Grunnen til at gruppen så på dette som nødvendig, var for at gruppemedlemmene fikk diskutert implementasjoner og endringer i fellesskap. Samtidig var det fint å ha sin "co-author" ved siden av seg "in-person", for gruppen følte at flyten da ble smidigere. I tillegg passet dette godt med den praksisen gruppen fulgte for kodevurdering (se arbeidsvaner). Selv om alle var tilstede i samme rom, var det ikke slik at man endte opp med at alle jobbet med én og samme ting. Oppgavene ble fordelt to og to, slik som forventet. Det var heller den forsikringen om at hvis de to som jobbet med en oppgave satt fast, kunne man spørre de andre gruppemedlemmene (i tillegg sparte man tid).

Videre gikk de ukentlige møtene bra. Alle gikk gjennom hvordan de lå an, og vi diskuterte videre utvikling. Oppgaver ble fordelt underveis, også utenom møtet. Grunnen til dette var fordi det var vanskelig for oss å predikere hvor lang tid man kom til å bruke på en oppgave. Oppgaver ble valgt ut ifra det som lå inne som <em>issues</em> på git.

Alle gruppemedlemmer nådde opp til de forventningene beskrevet i gruppekontrakten. Det ble ikke støtt på noen konflikter, og gruppen opplevede ikke noen avvik eller uenigheter.

## Arbeidsvaner
Mellom release 2 og 3 fortsatte gruppen med praksisen der man byttet mellom hvem man jobbet sammen med (begrunnelse ligger i release 2). Dette fortsatte å fungere fint. 

Arbeid ble fordelt ut ifra hvilke <em>issues</em> som måtte løses, og hva som trengtes å jobbes med med tanke på tidsfrister. Når det kommer til tidsfrister, ble det ikke satt noen tydelige frister for flere <em>issues</em>. Hvor stor en <em>issue</em> var, kunne være veldig varierende. Noen var større enn andre. I tillegg hastet ikke nødvendigvis alle <em>issues</em> å få løst raskt. En annen faktor som også spilte inn var ting som skjedde ellers i livene våre. På grunn av dette var det igjen ikke enkelt for gruppen å se for seg tidsfrister. Tidsfrister ble derfor bare lagt inn for <em>issues</em> der gruppen var sikre på at de måtte løses innen en viss tid.

Utviklingsoppgaver ble lagt inn på Git i form av <em>issues</em> for alle større endringer eller implementasjoner. For svært små endringer ble det ikke lagt inn noen <em> issues</em> inne på Git. Grunnen til det var at vi ikke ønsket å dytte mange små endringer, og overvelde <em> issue-board-et</em> inne på Git med unødvendig mange issues. 

Gruppen fortsatte med praksisen der en <em>push</em> ble gjort til vår <em>Development Branch</em>. Mellom release 2 og 3 ble det pushet til <em>master</em> én gang. Grunnen til dette var fordi vi hadde problemer med <em>Jacoco</em> før innlevering av release 2. Dette problemet ble løst noen dager etter innleveringen. I samsvar med svar fra undervisningsassistenter og læringsassistent ble versjonen med <em>Jacoco</em>-problemet rettet, pushet til master. Situasjonen er ytterligere forklart i dokumentasjonen for release 2. Utover denne éne gangen, ble det ikke pushet til master utenom ved innlevering. 

Gruppen benyttet kodevurdering i form av "par-programmering" og "over-skulderen-vudering". Gruppen gikk for disse metodene å vurdere kode på, siden det enkelt kunne komme opp naturlig når gruppen møtte hverandre fysisk. Med disse metodene fikk man to forskjellige perspektiv i hvordan noe skulle implementeres, når koden ble skrevet "der og da". Dette viste seg å fungere godt, så gruppen så ikke behov for å endre på denne praksisen. 

Fra og med etter release 1 begynte gruppen å kjøre en praksis der den personen som aksepterte en <em>merge request</em> var en som selv ikke selv deltok i kodingen av innholdet i <em>merge request-en</em>. Altså en form for ekstern kilde som godkjente koden. På denne måten fikk gruppen en form for "dobbelt-sjekk" for å sørge for at de nye implementeringene faktisk kom til å fungere som tenkt, og at det ikke ble dyttet inn eventuelle feil (dersom dette ble oppdaget av den eksterne godkjenneren). Etter release 2 oppdaget gruppen også at man kunne legge til <em>reviewer</em> inne på Git, for hver <em>merge request</em>, slik at det ble tydeliggjort hvem som hadde godkjent en <em>merge request</em>. Alle <em>merge requests</em> etter release 2 skal dermed ha dette. 

## Implementeringer ved release 3
### Funksjonalitet 

Applikasjonen har fått ny funksjonalitet siden release 2. Det er nå mulig å se transaksjoner i applikasjonen, se hvor mye man har på kontoen sin, opprette ulike kontotyper og slette disse, slette profilen og å overføre penger mellom egne kontoer og andre profilers kontoer. Alt dette er i henhold til brukerhistorien. Likevel klarte ikke gruppen å implementere regninger og betaling av dem, noe som var beskrevet i brukerhistorien. Ved release 2 fantes det en <em>Bill</em>-klasse, men da fristen nærmet seg oppdaget gruppen plutselig en feil der springboot ikke klarte å serialisere riktig, etter at en regning ble opprettet, dersom en profil refererte til en annen profil i JSON. Gruppen prøvde lenge å løse problemet, også med hjelp, men vi kom dessverre ikke fram til noen løsning. Problemet førte til at applikasjonen ikke skrev eller leste fra fil riktig, så for å unngå å levere et produkt som ikke fungerte ordentlig, fjernet gruppen implementeringen av regninger helt. 

Den store utvidelsen vi implementerte var å få på plass en oversikt over transaksjoner i applikasjonen. Vi begynte med å implementere egen logikk for transaksjoner, ved å skille det fra filhåndteringen. Videre ble filhåndteringsklassen endret på. Deretter ble logikken koblet opp med applikasjonen og REST-API. Utvidelsen stemmer, i samsvar med brukerhistorien. 

En annen stor endring var implementering av ulike kontotyper. I applikasjonen er tre kontotyper tilgjengelige:
- Brukskonto 
- Sparekonto
- BSU
  
Mange av metodene i klassene for disse kontotypene var ganske like, så det ble derfor laget en abstrakt klasse med alle generelle metoder, som de ulike konto-klassene kunne arve fra. Klassespesifikk logikk for å skille kontotypene fra hverandre ble så implementert for de ulike konto-klassene. 

Applikasjonen har blitt satt opp med et REST-API. REST-API-et ble satt opp med <em>springboot</em> og har blitt koblet opp med applikasjonen. Som følge av dette har <em> JSON </em>-filene blitt flyttet inn i <em>springboot</em>-mappen. Filhåndteringen foregår gjennom REST-API-et. 
<em> Øvrig dokumentasjon av REST-API kan leses i egen fil.</em>

Gruppen valgte å fortsette med å utvide javafx-applikasjonen, fremfor å benytte seg av mobil-klient, react eller lignende. Grunnen til at gruppen valgte å fortsette med å utvide javafx-applikasjonen var fordi gruppen ikke hadde stor erfaringer med de andre metodene, og derfor ikke ønsket å forsøke å våge seg ut på ukjent farvann. Gruppen følte seg derfor mer komfortabel med javafx. 

### Mappestruktur
Mappestrukturen har igjen gått gjennom en endring, som følge av oppsetting av REST-API og implementering av ulike kontoer. 
Endelig mappestruktur er som følger:
![](../../images/Release3/Mappestruktur-Core.png)
![](../../images/Release3/Mappestruktur-Core2.png%0D) 
![](../../images/Release3/Mappestruktur-Fxui1.png%0D) 
![](../../images/Release3/Mappestruktur-Fxui2.png%0D) 
![](../../images/Release3/Mappestruktur-Rest.png) 


### Testing 
Det har blitt laget tester for alle klasser i logikken, applikasjonen og REST-API. Testdekningsgraden for de ulike testene er forsøkt å få en så høy score som mulig. For å sjekke dette brukte vi <em>Jacoco</em> aktivt når testene ble skrevet. Grunnen til at gruppen ønsket å få så høy dekningsgrad som mulig, var fordi gruppen så på det som en god kvalitetssikring av koden og applikasjonen. Ved å teste mange ulike metoder og sjekke at disse ikke feilet ved ulike tilstander, ble vi sikrere på at det ikke var eventuelle hull eller bugs i koden. Når gruppen sier at vi har forsøkt å få "så høy score som mulig", mener vi at det ikke var mulig å få testet absolutt alt. <em>Jacoco</em> er et bra program å benytte for å sjekke testdekningsgrad, men å klare å teste hver linje med kode, slik <em>Jacoco</em> ønsker, er ikke alltid mulig. Dette gjelder for eksempel for private metoder som kun brukes når de blir kalt på av andre metoder. Det er ikke helt mulig å teste disse private metodene alene, uten å gjennom andre metoder. På grunn av dette er det vanskelig å få testet alt. 

Gruppen hadde en rekke problemer med implementeringen av tester for fxui. Først ble det forsøkt å ha testene under nøstede-løkker for å gjøre koden kortere. Dette fungerte likevel ikke, da maven kjørte testen i tilfeldig rekkefølge. Slik testen var implementert på det tidspunktet, skulle testen logge inn på en allerede eksisterende profil, teste applikasjonen, og så avslutte med å teste profilen og opprette den på nytt. Opprettelsen kjørte av og til helt først, noe som slo ut feil. Gruppen prøvde å løse problemet ved å spesifisere rekkefølge på testene, men dette fungerte heller ikke. Vi fikk også hjelp, men kom ikke fram til noen løsning. Gruppen endte da med å endre testene. Dette førte derimot til et nytt problem. Den nye testen hadde implementert en *beforeEach*-metode, der testen begynte med å opprette en helt ny bruker. Mot slutten skulle testen så teste sletting av bruker ved å opprette enda en ny bruker, og så slette denne. Likevel skjedde det også en feil her der maven ikke klarte å initialisere vinduer (*stages*) korrekt. Teorien til gruppen er at det var et problem i *beforeEach*-metoden. Likevel klarte vi ikke å finne ut av noen løsninger, også denne gangen med hjelp. Dermed endte vi opp med å lage en test der en ny profil opprettes på nytt for hver eneste test. Som følge at dette har testen blitt ganske lang. Et annet resultat av denne implementeringen var at alle metoder ikke kan testes. 

Testing av springboot ligger på 74%. Grunnen til at denne ikke er høyere er fordi det ikke ble lagt til en test for å skrive transaksjoner. I logikken er det ikke lagt opp for en måte å slette transaksjoner. Skulle vi ha testet skriving og lesing av transaksjoner, måtte vi ha hatt en måte å slette dem i etterkant. For *profileManagement* ligger det inne en metode for sletting av profiler, så her var det mulig å teste alle metoder. Dette er altså ikke tilfellet for transaksjoner. Videre er det lagt opp unntakshåndtering for *IOException*. Dette kan ikke testes, siden kodens metoder ikke tar inn noen filer. Filene ligger allerede lagret i klassene. 

### Kodekvalitet 
Kodekvalitet har gått gjennom sjekker med både <em>Spotbugs</em> og <em>Checkstyle</em>. Ved hjelp av disse verktøyene har gruppen gjort nødvendige endringer for å sikre god kodekvalitet. Målet var å ikke ha noen <em>Spotbugs-errors</em>, ingen <em>Checkstyle-violations</em> og heller ingen <em>Checkstyle-warnings</em>. 


### Javadoc
Alle klasser har fått javadoc, da dette er en god praksis. Med <em>Checkstyle</em>-verktøyet har vi også sørget for at dette har blitt skrevet på et "riktig" og konsist format. Gruppen fulgte en Google-konvensjon for dette (hentet fra Todo-List-prosjektet).

### Øvrig dokumentasjon (diagrammer)
Pakkediagram, klassediagram og sekvensdiagram har blitt laget. Ut i fra det som ble skrevet på piazza virket det som at strukturen i klassediagrammet var en beslutning gruppene selv måtte komme til enighet om. Vi bestemte oss derfor for å gå for et diagram der man kun ser på klassene i <em>core</em>. Siden sekvensdiagrammet uansett tydeliggjør sammenhengen mellom de ulike modulene, valgte gruppen å heller fokusere spesifikt på <em>core</em> for å få fram hvordan de ulike klassene i logikken henger sammen. Essensen i et klassediagram er der fremdeles tilstede, altså tydeliggjøre strukturen i koden og hvordan klasser henger sammen. 



