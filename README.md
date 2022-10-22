# Decentralized-Elections

## Blockchain

La blockchain est créée en Java.
Nous fournissons une Api Rest avec plusieurs endpoint permettant d'intéragir avec la Blockchain et de gérer son élection.

### liste des endpoints

---

**Endpoint pour créer l'élection**
  * méthode HTTP : POST
  * url : http://localhost:8080/smart-vote/api/v1/create-election
  * request body format :
```json
{
    "candidates" : [ "one", "two"],
    "starting_date" : "2022-10-19T08:30:00",
    "closing_date" : "2022-10-20T08:30:00",
    "election_name" : "value"
}
```
 * response type : JSON
 * response format
```json
{
    "election_name": "value",
    "blocks": [
        {
            "hash": "3a443a445d36acfad71cc15453b11267fd8b9218ded2244597203237cdf82180b61092bc963c64ab6d67d552c331c8c62947d56040182facc3d548242119fce1",
            "data": {
                "candidates": [
                    "one",
                    "two"
                ],
                "starting_date": "2022-10-19T08:30:00",
                "closing_date": "2022-10-20T08:30:00",
                "election_name": "value"
            }
        }
    ],
    "blockchain_valid": true
}
```
---

**Endpoint pour récupérer les données de l'élection**
* méthode HTTP : GET
* url : http://localhost:8080/smart-vote/api/v1/get-election/{election_name}
* response type : JSON
* response format
```json
{
   "election_name": "value",
   "blocks": [
      {
         "hash": "062abc9ff91eadeb5d75ef76e166d1ffdcba8d560e311e9698d86838d487f18e5f0fc6d386dc504c96f4ab87ccc7acad16a8b0957cfe52bbfa1ab6c6f4db6ebc",
         "data": {
            "candidates": [
               "one",
               "two"
            ],
            "starting_date": "2022-10-19T08:30:00",
            "closing_date": "2022-10-20T08:30:00",
            "election_name": "value"
         }
      },
      {
         "hash": "09e37554f4afa241919bd1a96a6e60091139e724c10226b1109f3799d3e06d1225b3ac034af15813a467de61806ee399a180d9af53a796e0f1c0e575f274883c",
         "previous_hash": "062abc9ff91eadeb5d75ef76e166d1ffdcba8d560e311e9698d86838d487f18e5f0fc6d386dc504c96f4ab87ccc7acad16a8b0957cfe52bbfa1ab6c6f4db6ebc",
         "data": {
            "election_name": "value",
            "candidate_name": "one",
            "voting_date": "2022-10-22T12:30:00"
         }
      }
   ],
   "blockchain_valid": true
}
```
---
**Endpoint pour enregistrer un vote dans la blockchain**
* méthode HTTP : POST
* url : http://localhost:8080/smart-vote/api/v1/vote
* request body format :
```json
{
   "election_name" : "value",
   "candidate_name" : "one",
   "voting_time" : "2022-10-22T12:30:00"
}
```
* response type : JSON
* response format
```json
{
   "hash": "09e37554f4afa241919bd1a96a6e60091139e724c10226b1109f3799d3e06d1225b3ac034af15813a467de61806ee399a180d9af53a796e0f1c0e575f274883c",
   "previous_hash": "062abc9ff91eadeb5d75ef76e166d1ffdcba8d560e311e9698d86838d487f18e5f0fc6d386dc504c96f4ab87ccc7acad16a8b0957cfe52bbfa1ab6c6f4db6ebc",
   "data": {
      "election_name": "value",
      "candidate_name": "one",
      "voting_date": "2022-10-22T12:30:00"
   }
}
```

---

### Lancer le projet blockchain

**En utilisant IntelliJ IDEA**

Aller sur la classe [Application](Blockchain/src/main/java/com/septgrandcorsaire/blockchain/Application.java)

Cliquez sur le bouton pour run

_ajouter l'image_

**En utilisant VS Code**

Installer l'extension [Spring Boot Dashboard](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-spring-boot-dashboard) ou directement sur VSCode

Aller sur l'onglet ```Spring Boot Dashboard``` et lancer l'application

_ajouter l'image_

### Interagir avec la blockchain en utilisant Postman

Une collection Postman est fournie [ici](docs/Smart vote.postman_collection.json)

Téléchargez le fichier et importez le sur [Postman](https://www.postman.com/downloads/)

Vous pourrez ensuite réaliser vos premières requêtes sur l'API

### Resources

[Importer une collection Postman](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/)

[Tuto Spring Boot in Visual Studio Code](https://code.visualstudio.com/docs/java/java-spring-boot#_run-the-application)