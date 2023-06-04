Projekt ZTBZ

Wykorzystane technologie bazodanowe:
- PostgreSQL
- MongoDB

Endpointy:
Na poczatku endpointa dodajmy z jakiej bazy chcemy skorzystac np /POSTGRES/movies

Movie:
GETy
/movies/search?genre:horror?year:2021?platform:netflix?pageSize:10?offset:10?sort:SORT_OPTION?searchText:TYTUL
- tytul
- ocena
- rok
- czas trwania
- url do zdjecia
- skrocony opis
- gatunki -lista

/movies/{id}
- tytul
- opis filmu
- aktorzy - lista
- rezyserowie - lista
- ocena
- czas trwania
- url ze zdjeciem
- platformy - lista
- budzet
- rok
- gatunki - lista

POSTy

/movies
- te same dane co /movies/{id}

DELETE

/movies/{id}

Mozliwe sortowania:
- ocena
- dlugosc filmu
- alfabetycznie po tytule

Defaultowe sortowanie na bazie to po ocenie

Peoeple

GETy

/people/{id}
- id
- kim jest - to jest lista bo moze byc jednoczesnie aktorem i rezyserem, czyli jakis enum pewnie
- data urodzenia
- data smierci - optional
- filmy gdzie byl aktorem - tytuly wraz z idkami filmow
- filmy gdzie byl rezyserem - tytuly wraz z idkami filmow
- imie
- nazwisko
- opis
- zdjecie - opcjonalne

/people?searchText:IMIE-NAZWISKO?pageSize:10?offset:10
- imie
- nazwisko
- id
- url ze zdjeciem

Tu nie dajemy sortowania w endpoincie, sortujemy na bazie alfabetycznie po imionach

POST

/people/

- te same dane co zwraca nam /people/{id}

DELETE

/people/{id}

Jeszcze jedna tabelka na platformy
- ID
- NAZWA
- URL-z logiem

Statystyki ktore zwracamy w endpointach to poprostu czas ile trwalo zapytanie do bazy danych.
Pamiec mozemy recznie porownac sprawdzajac ile pamieci zajmuja bazy danych

# MongoDB
## Generowanie danych MongoDB przy uruchomienia backendu
Jeżeli chcesz wygenerować dane, zaraz po uruchomieniu aplikacji, to trzeba dodać argument `generateMongoData` przy uruchomieniu programu. Bez tej flagi dane nie będę generowane po uruchomieniu backendu.

Po wygenerowaniu trzeba eksportować dane z użyciem poniższych komend
```bash
docker exec -it mongodb mongoexport --db ztbzDatabase --collection movies --out /movies.json --jsonArray --authenticationDatabase ztbzDatabase --username ztbzBackend --password example
docker exec -it mongodb mongoexport --db ztbzDatabase --collection people --out /people.json --jsonArray --authenticationDatabase ztbzDatabase --username ztbzBackend --password example
```

Następnie trzeba skopiować pliki do lokalnej maszyny
```bash
docker cp mongodb:/movies.json /path/to/your/movies.json
docker cp mongodb:/people.json /path/to/your/people.json
```

## Jak dodać mockowe dane z MongoDB
Przed przystąpieniem do działania, kontener z bazą danych powinien być uruchomiony, oraz pliki `movies.json` i `people.json` powinny być pobrane.

Jeżeli powyższe rzeczy mamy przygotwane, to kopiujemy pliki `*.json` do naszego kontenera
```bash
docker cp /path/to/your/movies.json mongodb:/movies.json
docker cp /path/to/your/people.json mongodb:/people.json
```

Następnie korzystając z narzędzia `mongoimport` importujemy pliki do odpowiednich kolekcji
```bash
docker exec -it mongodb mongoimport --db ztbzDatabase --collection movies --file /movies.json --jsonArray --authenticationDatabase ztbzDatabase --username ztbzBackend --password example
docker exec -it mongodb mongoimport --db ztbzDatabase --collection people --file /people.json --jsonArray --authenticationDatabase ztbzDatabase --username ztbzBackend --password example
```