Projekt ZTBZ

Wykorzystane technologie bazodanowe:
- PostgreSQL
- MongoDB

Endpointy:

Movie:
GETy
/movies/search?genre:horror?year:2021?platform:netflix?pageSize:10?offset:10?sort:SORT_OPTION?searchText:TYTUL
    -tytul
    -ocena
    -rok
    -czas trwania
    -url do zdjecia
    -skrocony opis
    -gatunki -lista
/movies/{id}
    -tytul
    -opis filmu
    -aktorzy - lista
    -rezyserowie - lista
    -ocena
    -czas trwania
    -url ze zdjeciem
    -platformy - lista
    -budzet
    -rok
    -gatunki - lista

POSTy
/movies
    -te same dane co /movies/{id}

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
    -id
    -kim jest - to jest lista bo moze byc jednoczesnie aktorem i rezyserem, czyli jakis enum pewnie
    -data urodzenia
    -data smierci - optional
    -filmy gdzie byl aktorem - tytuly wraz z idkami filmow
    -filmy gdzie byl rezyserem - tytuly wraz z idkami filmow
    -imie
    -nazwisko
    -opis
    -zdjecie - opcjonalne

/people?searchText:IMIE-NAZWISKO?pageSize:10?offset:10
    -imie
    -nazwisko
    -id
    -url ze zdjeciem

Tu nie dajemy sortowania w endpoincie, sortujemy na bazie alfabetycznie po imionach

POST
/people/
    -te same dane co zwraca nam /people/{id}

DELETE
/people/{id}

Jeszcze jedna tabelka na platformy
ID
NAZWA
URL-z logiem

Statystyki ktore zwracamy w endpointach to poprostu czas ile trwalo zapytanie do bazy danych.
Pamiec mozemy recznie porownac sprawdzajac ile pamieci zajmuja bazy danych