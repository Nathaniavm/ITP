# Bankapp - Profiles API

## Endpoints

Metoder:

* GET
  * **Beskrivelse**: Henter en liste av alle profiler som er lagret.
  * **URI**: host:port/profiles
  <http://localhost:8080/profiles>
  * **Parametre**: Ingen
  * **Respons**: JSON med liste av profiler.
  * Tilgjengelig med Springboot
  * Eksempel respons

```json
[
    {
        "@id": "9ad50990-b233-47ac-9e52-c298476ecc43",
        "name": "Klein Nordmann",
        "email": "klein@gmail.com",
        "tlf": "98989811",
        "password": "passord1",
        "accounts": [],
        "bankCards": [],
        "bills": []
    },
    {
        "@id": "94bd5834-bb4a-4ebe-9411-9089f8af6501",
        "name": "Ada Lovelace",
        "email": "adalovelace@gmail.no",
        "tlf": "98987765",
        "password": "koding1234",
        "accounts": [
            {
                "accountType": "SpendingsAccount",
                "@id": "db7c907e-2f7e-41ce-853a-c76d0e1fa84e",
                "name": "Spendings account",
                "profile": "94bd5834-bb4a-4ebe-9411-9089f8af6501",
                "balance": 100,
                "accNr": "1234 56 88979",
                "bankCard": null,
                "transaction": []
            }
        ],
        "bankCards": [],
        "bills": []
    }
]
```

* PUT
  * **Beskrivelse**: Lager en en ny profil eller oppdaterer en eksisterende profil.
  * **URI**: host:port/profiles
  * **Parametre**:
    * Body - application/json
      * Profil objekt
  * **Respons**: Ingen
  * Tilgjengelig med springboot
  * Eksempel request body:

```json
{
    "@id": "9ad50990-b233-47ac-9e52-c298476eca98",
    "name": "Aleksandhros Kurti",
    "email": "lxkurti@gmail.com",
    "tlf": "34753687",
    "password": "passord1",
    "accounts": [],
    "bankCards": [],
    "bills": []
}
```

* GET
  * **Beskrivelse**: Henter profil med gitt email.
  * **URI**: host:port/profiles/{email}
     <http://localhost:8080/profiles/klein@gmail.com>
  * **Parametre**:
    * Path - String
  * **Respons**: Profil som json objekt
  * Tilgjengelig med springboot
  * Eksempel respons:

```json
{
    "@id": "9ad50990-b233-47ac-9e52-c298476ecc43",
    "name": "Klein Nordmann",
    "email": "klein@gmail.com",
    "tlf": "98989811",
    "password": "passord1",
    "accounts": [],
    "bankCards": [],
    "bills": []
}
```

* DELETE
  * **Beskrivelse**: Sletter profil med gitt email.
  * **URI**: host:port/profiles/{email}
     <http://localhost:8080/profiles/klein@gmail.com>
  * **Parametre**:
    * Path parameter - String
  * **Respons**: Ingen
  * Tilgjengelig med springboot

* POST
  * **Beskrivelse**: Lagrer ny transaction
  * **URI**: host:port/profiles/transaction
     <http://localhost:8080/profiles/transaction>
  * **Parametre**:
    * Body - application/json
      * Transaction objekt
  * **Respons**: Ingen
  * Tilgjengelig med springboot

* GET
  * **Beskrivelse**: Henter transactions som hører til profil med gitt email.
  * **URI**: host:port/profiles/{email}/transactions
     <http://localhost:8080/profiles/klein@gmail.com/transactions>
  * **Parametre**:
    * Path parameter - String
  * **Respons**: Liste med transactions
  * Eksempel respons:

```json
[
    {
        "@id": "d9be040f-4447-47a2-a6f6-b977aec28ded",
        "email": "klein@gmail.com",
        "transactionTo": "1234 77 99570",
        "name": "Klein Nordmann",
        "transactionFrom": "1234 27 70978",
        "amount": 70
    },
    {
        "@id": "19148901-cdd9-4ce4-ac86-0fcf4c50f642",
        "email": "klein@gmail.com",
        "transactionTo": "1234 27 70978",
        "name": "Klein Nordmann",
        "transactionFrom": "1234 77 99570",
        "amount": -70
    }
]
```
