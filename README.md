# Decentralized-Elections

## Blockchain

La blockchain est créée en Java.
Nous fournissons une Api Rest avec plusieurs endpoint permettant d'intéragir avec la Blockchain et de gérer son élection.

### Liste des endpoints

[Endpoint pour créer l'élection](docs/endpoints/create-election/post.md) : `POST /smart-vote/api/v1/create-election`

[Endpoint pour récupérer les données de l'élection](docs/endpoints/read-results/get.md): `GET /smart-vote/api/v1/get-election/{election_name}`

[Endpoint pour enregistrer un vote dans la blockchain](docs/endpoints/vote/post.md) : `POST /smart-vote/api/v1/vote`

[Endpoint pour récupérer les données sandbox](docs/endpoints/read-results/sandbox.md) : `POST /smart-vote/api/v1/get-sandbox/`

---

### Lancer le projet blockchain

**En utilisant IntelliJ IDEA**

Aller sur la classe [Application](Blockchain/src/main/java/com/septgrandcorsaire/blockchain/Application.java)

Cliquez sur le bouton pour run

![run_projet_intelliJ](https://user-images.githubusercontent.com/32906777/197355177-b7fea5be-03d3-4a70-9a3a-0e69481aed8f.png)

**En utilisant VS Code**

Installer l'extension [Spring Boot Dashboard](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-spring-boot-dashboard) ou directement sur VSCode

Aller sur l'onglet ```Spring Boot Dashboard``` et lancer l'application

![run_projet_vscode](https://user-images.githubusercontent.com/32906777/197355308-82d0dda7-9895-4e6a-9ab0-3b31b3429ddb.png)


### Interagir avec la blockchain en utilisant Postman

Une collection Postman est fournie [ici](docs/Smart vote.postman_collection.json)

Téléchargez le fichier et importez le sur [Postman](https://www.postman.com/downloads/)

Vous pourrez ensuite réaliser vos premières requêtes sur l'API

### Resources

[Importer une collection Postman](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/)

[Tuto Spring Boot in Visual Studio Code](https://code.visualstudio.com/docs/java/java-spring-boot#_run-the-application)
