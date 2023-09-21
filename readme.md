# Group gr2315 repository: Bankapp-prosjekt

Dette prosjektet krever Java version 17.0.8 og maven versjon 3.9.4. 
Dette prosjektte er et utviklingsprosjekt tilsvarende det en skal gjennom i IT1901. 

Prosjektet bruker maven til bygging og kjøring. For å bygge, kjør mvn install fra rot-prosjektet (Bankapp -mappa). Dette vil kjøre alle tester og kvalitetssjekker. 
Prosjektet må kjøres fra **fxui**-modulen, enten med `mvn javafx:run -f fxui/pom.xml` eller ved å først kjøre `cd fxui` og så `mvn javafx:run. Merk at man må først ha kjørt `mvn install` på modulene som **fxui**-modulen er avhengig av (pr. nå **core** og **fxutil**), for at det skal gå.

Appen vi har tenkt å lage er en bankapp. All form for fxui, fxml komponenter og kontrollere ligger inni bankapp-fxui mappen. All informasjon og logikken som blir brukt av appen ligger i bankapp-core. 
