# Release 3
<em>Dette prosjektet krever Java version **16**, maven versjon **4.0.0**, maven compiler.source **16** og javafx version **17.0.2**. 

Eclipse-che link: https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2315/gr2315?new

Merk: Det er mulig at eclipse-che krever at man har en gyldig personal access token.</em>

## Gruppesamarbeid
Nå som prosjektet nærmer seg slutt, ønsker vi å komme med en oppsummering av gruppesamarbeidet. Gruppesamarbeidet har fungert svært godt gjennom hele prosjektet. I henhold til gruppekontrakten har alle gruppemedlemmer klart å holde seg til det man ble enige om. Gruppen ble enige om å ha én felels "work-session" i uken, men etterhvert som utviklingen utvidet seg, gikk dette fort over til tre ganger i uken. Dette så vi på som nødvendig og lurt, slik at gruppemedlemmene fikk diskutert implementasjoner og endringer i fellesskap. Samtidig var det fint å ha en "co-author" ved siden av seg "in-person", for gruppen følte at flyten da ble smidigere. Selv om alle var tilstede i samme rom, var det ikke slik at man endte opp med at alle jobbet med én og samme ting samtidig. Oppgavene ble fordelt to og to, slik som forventet. Det var heller den forsikringen om at hvis de to som jobbet med en oppgave satt fast, kunne man spørre de andre gruppemedlemmene (i tillegg sparte man tid).

Videre gikk de ukentlige møtene bra. Alle gikk gjennom hvordan de lå an, og vi diskuterte videre utvikling. Oppgaver ble fordelt underveis, også utenom møtet. Grunnen til dette var fordi det var vanskelig for oss å predikere hvor lang tid man kom til å bruke på en oppgave. Oppgaver ble valgt ut ifra det som lå inne som <em>issues</em> på git.

Alle gruppemedlemmer nådde opp til de forventningene beskrevet i gruppekontrakten. Det ble ikke støtt på noen konflikter, og gruppen opplevede ikke noen avvik eller uenigheter.

## Arbeidsvaner
Mellom release 2 og 3 fortsatte gruppen med praksisen der man byttet mellom hvem man jobbet sammen med (begrunnelse ligger i release 2). Dette fortsatte å fungere fint. 

Arbeid ble fordelt ut ifra hvilke <em>issues</em> som måtte løses, og hva som trengtes å jobbes med med tanke på tidsfrister. Når det kommer til tidsfrister, ble det ikke satt noen tydelige frister for flere <em>issues</em>. Hvor stor en <em>issue</em> var, kunne være veldig varierende. Noen var større enn andre. I tillegg hastet ikke nødvendigvis alle <em>issues</em> å få løst raskt. En annen faktor som også spilte inn var ting som skjedde ellers i livene våre. På grunn av dette var det igjen ikke enkelt for gruppen å se for seg tidsfrister. Tidsfrister ble derfor bare lagt inn for <em>issues</em> der gruppen var sikre på at de måtte løses innen en viss tid.

Utviklingsoppgaver ble lagt inn på Git i form av <em>issues</em> for alle større endringer eller implementasjoner. 
## Implementeringer ved release 3
### Funksjonalitet 

Applikasjonen har fått en rekke nye funksjonaliteter siden release 2. Det er nå mulig å se transaksjoner i applikasjonen, se hvor mye man har i kontoen sin, opprette ulike kontotyper og slette disse, slette profilen, opprette og betale regninger og se disse i applikasjonen, og å overføre penger mellom egne kontoer og andre profilers kontoer. Alt dette er i henhold til brukerhistorien. 

Applikasjonen har blitt satt opp med et REST-API. REST-API-et ble satt opp med <em>springboot</em>. Gruppen valgte å fortsette med å utvide javafx-applikasjonen, fremfor å benytte seg av mobil-klient, react eller lignende. Grunnen til at gruppen valgte å fortsette med å utvide javafx-applikasjonen var fordi gruppen ikke hadde så store erfaringer med de andre metodene, og derfor ikke ønsket å forsøke å våge seg ut på ukjent farvann. Gruppen følte seg mer derfor mer komfortabel med javafx. 




