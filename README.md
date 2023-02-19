# Teenuskeskkonna arendus- ja hooldustööd 2023 - Proovitöö
## Projekti struktuur
Selles kaustas on kaks eraldi projekti, "Lepinguregister" ja "Akteerimine", mis töötavad eraldiseisvalt. Rakenduste ja Dockeri konteinerite käivitamise juhendid on vastavate rakenduste README failides.

Lepinguregistri rakendus on
ühendatud PostgreSQL andmebaasiga. Suhtlus kahe rakenduse vahel toimub läbi REST API päringute ja ActiveMQ sõnumite.


## Meiliaadressi sätestamine
Teenuste hinna muutuse kinnituse meilid tulevad meiliaadressilt "helmes.proov@gmail.com".
Sisendaadressi saab sätestada failis "rkas/lepinguregister/config/ApplicationProperties.java".

## REST API Kirjeldus
REST API kirjeldus on loodud kasutades Swagger-UI'd. Kui rakendused on tööle pandud, pääseb dokumentatsioonile ligi järgnevatelt aadressidelt:

Lepinguregister - http://localhost:9000/swagger-ui/index.html#/

Akteerimine - http://localhost:9001/swagger-ui/index.html#/

Lisaks kirjeldab API dokumentatsioon ka funktsionaalsuse lühikirjeldusi, sisendeid, valideerimis kontrolle ja andmebaasi muudatusi.


## Kasutaja protsessi kirjeldus
Protsess algab Aktide rakenduses

1. Kasutaja on Akteerimise rakenduses ja avab Aktid- nimekirja vaate
2. Süsteem kuvab olemas olevad aktid, sh Akti nimetus ja Olek
3. Kasutajal on vaja algatada Esitatud aktis teenuse hinna muutmine, selleks klikib lahti valitud akti
4. Süsteem kuvab akti objekti(d) ja Teenuste arvu
5. Selleks, et leida õige teenus ja alustada hinna muutmist, vajutab kasutaja õige objekti teenuste arvu lingile
6. Süsteem kuvab teenuste hinnainfo modaali: Teenuse nimetused, kehtiv Hind, hinna kehtivuse periood (sh algus/lõpp) ning Muuda nupp
7. Kasutaja valib soovitud teenuse, vajutab tema real Muuda.
8. Süsteem kuvab editeerimise vaate
9. Kasutaja muudab hinna ning lisab, mis kuupäevast uus hind kehtima hakkab. Algusekuupäev on kasutajale kohustuslik valida. Lõpukuupäeva võib sisestada, aga ei ole kohustuslik, ning seejuures lõpukuupäev ei tohi olla varasem kui algus.
10. Kasutaja vajutab Kinnita (võib ka Tühistada, kui ei soovi muudatusi rakendada)
11. Süsteem kuvab muutmise teavituse "Teenuse uuendamise taotlus loodud" ning muudetud teenuse juures kuvatakse olek "Kinnitamisele saadetud".
12. Kasutaja sulgeb modaali ning liigub tagasi aktide listi vaatesse.
13. Süsteem kuvab muudetud aktil lisainfot (muudetud), staatus on endiselt Esitatud.
14. Hinna muutmise algatus on tehtud, protsess Aktide rakenduses peatub.

Protsess jätkub Lepingute registris

14. Kasutaja klikib lahti Lepingud vaate
15. Aktide rakendusest on jõudnud info Lepingute rakendusse, st kuvatakse kinnitamist vajav hinna muudatus alajaotises "Lepingud, milles teenuse hind vajab muutmist"
16. Süsteem kuvab Lepingu numbri, Objekti, Teenuse, Uus hind (vana hind), Kehtiv alates (eelmise hinna kehtivuse algus) ning Kehtiv kuni (eelmise hinna kehtivuse lõpp) kui oli lisatud.
17. Kasutaja vajutab Kinnita
18. Süsteem kuvab teavitust "Teenus on uuendatud"
19. Proovitöö protsess Lepingute rakenduses lõppeb

Protsess jätkub Aktide registris

20. Kasutaja värskendab lehe, süsteem kuvab Aktide nimekirja
21. Kinnitatud muudatusega keerati muudetud Akti staatus Esitatud --> Koostamisel
22. Kasutaja avab aktiga seotud objekti teenused ning näeb uut hinda, süsteem ei kuva enam "Kinnitamisele saadetud".
23. Kasutaja saab akteerimise protsessiga jätkata. Proovitöö protsessid siin puntkis lõppevad.

## Rakenduste käivitamine kasutades Dockerit
### Käivita ActiveMQ Docker image
Käivitada "Lepinguregister" peakataloogis:
docker-compose -f src/main/docker/activemq.yml up -d

### Ehita rakenduste Docker image-d
Käivitada "Lepinguregister" ja "Akteerimine" peakataloogides:
./gradlew -Pprod bootJar jibDockerBuild

### Käivita rakenduste Docker image-d
Käivitada "Lepinguregister" ja "Akteerimine" peakataloogides:
docker-compose -f src/main/docker/app.yml up -d
