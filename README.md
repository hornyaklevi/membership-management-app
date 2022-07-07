# Membership Management App
## Iskolai öregdiák szervezet tagnyilvántartó alkalmazás
### Vizsgaprojekt - Készítette: Hornyák Levente

A projekt egy iskolai öregdiák szervezet tagjainak, osztályközösségeinek nyilvántartására szolgáló alkalmazás.
Az alkalmazás egy 3 rétegű (`Controller`,`Service`,`Repository`) REST API alkalmazás `Spring Boot` és `Spring Data JPA` felhasználásával,
amelyben az iskolák-, az iskolai osztályok- és a tagok (entitások) adatainak tárolásához `PostgreSQL` adatbázist használunk.
Az adatbázis séma létrehozása és a minta adatok betöltése a `Flyway` segítségével történik.

Az alkalmazás **indítása**: `start_app.bat` / `start_app.sh` állományok indításával

Az alkalmazás **tesztelése**: `test_app.bat` / `test_app.sh` állományok indításával

Az API végpontok **tesztelése `Postman` segítségével**:
`MembershipManagementApp.postman_collection.json` futtatása:

`newman run MembershipManagementApp.postman_collection.json`

OpenAPI dokumentáció elérhetősége: http://localhost:8080/swagger-ui/index.html

---

## Entitások
### School - Iskolák

A `School` entitás attribútumai:

- id: `String` - az iskola egyedi azonosítója
- groupId: `Short` - az iskola csoport azonosítója
- name: `String` - az iskola neve
- address: `Address` - az iskola címe
- isActive: `Boolean` - jelöli, hogy az adott iskola működik-e
- schoolClasses: `List<SchoolClass>` - az adott iskolához tartozó osztályok listája

Az adatokat tároló tábla neve az adatbázisban: `school`

### SchoolClass - Iskolai osztáyok

A `SchoolClass` entitás attribútumai:

- id: `String` - az iskolai osztály egyedi azonosítója
- yearOfGraduation: `Short` - a végzés/érettségi éve
- markOfClass: `String` - az osztály betűjelzése (A, B, ...)
- formTeacher: `String` - az osztályfőnök neve
- school: `School` - az osztályhoz tartozó iskola
- members: `List<Member>` - az adott osztály tagjai

Az adatokat tároló tábla neve az adatbázisban: `schoolclass`

### Member - Tagok

A `Member` entitás attribútumai:

- memberId: `Long` - a tag egyedi azonosítója (automatikusan generált)
- personName: `PersonName` - tag neve
- schoolClass: `SchoolClass` - a tag iskolai osztálya
- placeOfBirth: `String` - a tag születési helye
- dateOfBirth: `LocalDate` - a tag születési ideje
- address: `Address` - a tag címe
- email: `String` - a tag e-mail címe
- phoneNumber: `String` - a tag telefonszáma
- membershipStatus: `MembershipStatus` - státusz: Enum, a következő opciókkal: `ACTIVE, INACTIVE, RIP`
- membershipType: `MembershipType` - típus: Enum, a következő opciókkal: `REGULAR, CONTRIBUTING, HONORARY`
- comment: `String` - megjegyzés
- isAllowNewsletter: `Boolean` - jelöli, hogy az adott tag kér-e hírlevelet
- createdAt: `LocalDateTime` - létrehozás dátuma és ideje
- updatedAt: `LocalDateTime` - módosítás dátuma és ideje

Az adatokat tároló tábla neve az adatbázisban: `member`

### Country - Országok

A `Country` entitás attribútumai:

- code: `String` - ország 2 betűs kódja (ISO-3166 szabvány szerint)
- nameOfCountryHun: `String` - az ország magyar neve
- nameOfCountryEng: `String` - az ország angol neve

Az adatokat tároló tábla neve az adatbázisban: `country`

### PersonName - Név (@Embeddable osztály)

- prefixOfName: `String` - név előtag
- lastName: `String` - vezetéknév
- firstName: `String` - keresztnév
- suffixOfName: `String` - név utótag
- nickName: `String` - becenév

### Address - Cím (@Embeddable osztály)

- country: `Country` - ország
- zipCode: `String` - irányítószám
- city: `String` - város
- stateOrProvince: `String` - megye
- street: `String` - utca, házszám

---

## API Végpontok
- Minden végpont JSON formátumban kommunikál.

### School

| HTTP kérés | Erőforrás                | Leírás                                                                                                                                 |
|------------|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| `GET`      | `/api/v1/schools`        | visszaadja az adatbázisban tárolt összes iskolát                                                                                       |
| `GET`      | `/api/v1/school/{id}`    | visszaadja a megadott id-val rendelkező iskolát                                                                                        |
| `POST`     | `/api/v1/school`         | _request body_-jában kapott JSON formátumú új iskolát, `School`-t menti az adatbázisba, visszatér a lementett entitással               |
| `PUT`      | `/api/v1/school/{id}`    | a _request body_-ban kapott `School`-t felülírja az adatbázisban                                                                       |
| `DELETE`   | `/api/v1/school/{id}`    | törli az adatbázisból a megadott id-jú iskolát                                                                                         |

### SchoolClass

| HTTP kérés | Erőforrás                | Leírás                                                                                                                                 |
|------------|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| `GET`      | `/api/v1/classes`        | visszaadja az adatbázisban tárolt összes iskolai osztályt                                                                              |
| `GET`      | `/api/v1/class/{id}`     | visszaadja a megadott id-val rendelkező iskolai osztályt                                                                               |
| `POST`     | `/api/v1/class`          | _request body_-jában kapott JSON formátumú új iskolai osztályt, `SchoolClass`-t menti az adatbázisba, visszatér a lementett entitással |
| `PUT`      | `/api/v1/class/{id}`     | a _request body_-ban kapott `SchoolClass`-t felülírja az adatbázisban                                                                  |
| `DELETE`   | `/api/v1/class/{id}`     | törli az adatbázisból a megadott id-jú iskolai osztályt                                                                                |

### Member

| HTTP kérés | Erőforrás                | Leírás                                                                                                                                 |
|------------|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| `GET`      | `/api/v1/members`        | visszaadja az adatbázisban tárolt összes tagot                                                                                         |
| `GET`      | `/api/v1/member/{id}`    | visszaadja a megadott id-val rendelkező tagot                                                                                          |
| `POST`     | `/api/v1/member`         | _request body_-jában kapott JSON formátumú új tagot, `Member`-t menti az adatbázisba, visszatér a lementett entitással                 |
| `PUT`      | `/api/v1/member/{id}`    | a _request body_-ban kapott `Member`-t felülírja az adatbázisban                                                                       |
| `DELETE`   | `/api/v1/member/{id}`    | törli az adatbázisból a megadott id-jú tagot                                                                                           |

### Country

| HTTP kérés | Erőforrás                | Leírás                                                                                                                                 |
|------------|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| `GET`      | `/api/v1/countries`      | visszaadja az adatbázisban tárolt összes országot                                                                                      |
| `GET`      | `/api/v1/country/{code}` | visszaadja a megadott code-dal rendelkező országot                                                                                     |
| `GET`      | `/api/v1/country`        | ország keresése a magyar név alapján, a keresendő ország nevet a `name_hu` _pathvariable_-ben kapja                                    |
| `POST`     | `/api/v1/country`        | _request body_-jában kapott JSON formátumú új országot, `Country`-t menti az adatbázisba, visszatér a lementett entitással             |
| `PUT`      | `/api/v1/country/{code}` | a _request body_-ban kapott `Country`-t felülírja az adatbázisban                                                                      |
| `DELETE`   | `/api/v1/country/{code}` | törli az adatbázisból a megadott code-ú országot                                                                                       |

## Kiegészítő UML osztály diagram - model réteg

 ```mermaid
classDiagram
direction LR
class Address {
  - Country country
  - String zipCode
  - String city
  - String stateOrProvince
  - String street
}
class Country {
  - String code
  - String nameOfCountryHun
  - String nameOfCountryEng
}
class Member {
  - Long memberId
  - PersonName personName
  - SchoolClass schoolClass
  - String placeOfBirth
  - LocalDate dateOfBirth
  - Address address
  - String email
  - String phoneNumber
  - MembershipStatus membershipStatus
  - MembershipType membershipType
  - String comment
  - Boolean isAllowNewsletter
  - LocalDateTime createdAt
  - LocalDateTime updatedAt
}
class MembershipStatus {
<<enumeration>>
  +  ACTIVE
  +  INACTIVE
  +  RIP
}
class MembershipType {
<<enumeration>>
  +  REGULAR
  +  CONTRIBUTING
  +  HONORARY
}
class PersonName {
  - String prefixOfName
  - String firstName
  - String lastName
  - String suffixOfName
  - String nickName
}
class School {
  - String id
  - Short groupId
  - String name
  - Address address
  - Boolean isActive
  - List~SchoolClass~ schoolClasses
}
class SchoolClass {
  - String id
  - Short yearOfGraduation
  - String markOfClass
  - String formTeacher
  - School school
  - List~Member~ members
}

Address "1" *--> "country 1" Country 
Member "1" *--> "address 1" Address 
Member --> MembershipStatus: has-a 
Member --> MembershipType: has-a 
Member "1" *--> "personName 1" PersonName 
Member "1" *--> "schoolClass 1" SchoolClass 
School "1" *--> "address 1" Address 
School "1" *--> "schoolClasses *" SchoolClass 
SchoolClass "1" *--> "members *" Member 
SchoolClass "1" *--> "school 1" School 
```