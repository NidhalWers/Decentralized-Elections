# Gestion de projet 

## Organisation du travail

### Sprint
Notre travail sera organisé sous forme de sprint d'une durée de une semaine : 
- Tous les lundi : Sprint Planning
     - Plannification du sprint
     - Présentation des fonctionnalités à réaliser durant le sprint en se basant sur la colonne 'Backlog'
     - Création et formalisation des tickets à placer dans la colonne 'To-Do'  
- Le lundi suivant avant le sprint planning : Sprint Review
    - On voit ce qui a été fait
    - On fait un bilan, on cherche à améliorer notre façon de travailler

### Ticket
Un ticket représente une unité de travail assigné à une ou plusieurs personne.
Les tickets sont créés durant le sprint par les développeurs concernés et le scrum-master

### Sprint Planning 
On se retrouve en groupe, chaque pôle explique les priorité pour le sprint qui arrive.
Pour chaque pôle (Application, MachineLearning ou BlockChain) : 
- Les développeurs du pôle indiquent ce qu'il faudrait faire durant ce sprint
- Un certain nombre de carte du backlog sont choisi pour le sprint
- Les cartes de backlog sont transformés en ticket technique par le Scrum-master en collaborant avec les développeurs du pôle
   - Les tickets sont créés en suivant les templates
   - Une durée est determinée pour chaque ticket
- chaque ticket est assigné à un développeur durant le sprint planning

**Ensuite le développeur est libre de s'ajouter des tâches**

AUCUNE TICKET TECHNIQUE NE DOIT ËTRE CRÉÉ DURANT LE SPRINT, des cartes peuvent être ajoutées dans le backlog en cas de besoin
 
### Sprint Retrospective
On se retrouve en groupe, chaque pôle explique ce qu'il a pu faire durant le sprint.
Si des tickets ne sont pas fini ils seront analysés :
- si il y a des bugs on les voit ensemble
- si le ticket est trop gros on peut essayer de le réduire en plusieurs petits tickets
- si des problèmes techniques sont rencontrés on en discute et on peut assigner un autre développeur pour le ticket
<br>
Les branches seront merge sur les branches sources correpondants à leur projet


## Outils

Sur Trello on retrouve les tableaux 
- [Application](https://trello.com/b/D8Ap5Bh6/application) 
- [BlockChain](https://trello.com/b/Lzb7U1go/blockchain)
- [MachineLearning](https://trello.com/b/BsiMh4QB/machinelearning)

## Fonctionnement du Github

Chaque projet a sa propre branche main : 
- [Application](https://github.com/NidhalWers/Decentralized-Elections/tree/Application)
- [BlockChain](https://github.com/NidhalWers/Decentralized-Elections/tree/Blockchain)
- [MachineLearning](https://github.com/NidhalWers/Decentralized-Elections/tree/MachineLearning)
 
## Fonctionnement du Trello

### Constitution d'un ticket
Un ticket est constituté 
- un titre
- un label
- un numéro
- une User Story
- des Acceptance Criteria

### Création d'un ticket
Un ticket peut être créé à partir du template pour le type de ticket voulu
Les templates sont situé sur la liste 'To-Do' 

## Liaison entre Trello et Github
Sur Trello les tickets sont numérotés, pour chaque ticket (ou card) une branche 'Feature', 'Bug' ou 'Modification'  sera créée à partir de la branche principale liée au sujet



# Exemples

## Présentation d'un ticket

voici comment est présenté un ticket technique une fois créé lors du sprint planning

<img width="1267" alt="presentation_ticket" src="https://user-images.githubusercontent.com/32906777/194011461-17435f25-cc79-49f8-acb8-83eab37ce80f.png">


## Création de ticket à partir de template

### étape n°1
Un ticket technique est créé à partir du template qui correspond à sa nature
<br></br>
**ATTENTION : les templates ne doivent pas être modifiés**
<img width="1278" alt="creation_ticket_1" src="https://user-images.githubusercontent.com/32906777/194012872-b0832551-0992-473d-b160-b90b50543300.png">

### étape n°2
- Tout d'abord il faut donner un titre parlant au ticket
- Ensuite la description est éditée
- Le ticket aura une durée qui sera présentée sous forme de date de rendu
- Le ticket est ensuite assigné à un développeur
- Le développeur pourra ensuite créer une branche et l'associer à son ticket [voir la section correspondante](#branch) 
<img width="1263" alt="creation_ticket_2" src="https://user-images.githubusercontent.com/32906777/194013557-02e58220-b48a-4361-ba9a-9efb7b254ea5.png">


## Lier un ticket à une branche github

### étape n°1
Les informations du ticket tels que
- le label
- le card number
- le tableau dans lequel il est (MachineLearning, Application ou BlockChain)
serviront pour créer la branche github sous cette forme : <a name="branch_name"></a>
<br></br>
```
Domain's first letter + '/' + label + Card number
```
avec commme source la branche correspondant au tableau
<br></br>
dans l'exemple suivant, la branche aura comme nom 
```
B/Features#1
```

<img width="1267" alt="lier_ticket_github_2" src="https://user-images.githubusercontent.com/32906777/194014848-bf391db9-d357-4010-ac11-06e46fcd0425.png">

### étape n° 2 <a name ="branch"></a>
On crée une nouvelle branche pour chaque ticket commencé
<img width="1280" alt="lier_ticket_github_2" src="https://user-images.githubusercontent.com/32906777/194019532-ba7110ca-379e-4377-a22d-39328e25d9c2.png">

### étape n°3
On spécifie le nom de la branche comme présenté [ici](#branch_name)
<br></br>
ATTENTION : la source doit correspondre à la branche principale du projet (MachineLearning, Application ou BlockChain)
<img width="1280" alt="lier_ticket_github_3" src="https://user-images.githubusercontent.com/32906777/194019581-798443a5-9fc2-4fc0-8b92-9c5afd1272af.png">


### étape n°4
La branche est créée avec la bonne source et le bon nom
<img width="1277" alt="lier_ticket_github_1" src="https://user-images.githubusercontent.com/32906777/194014821-e14f6955-91b9-4e28-a4d6-e5b066cebad1.png">


