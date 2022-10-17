# Application

## Set up

### Requierement

- [PostgreSQL 14.5](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
- [Anaconda](https://www.anaconda.com/products/distribution)

### Installation base de donnée 
- Ouvrez PostGresSQL 
- Si ce n'est pas déjà fait, créez un rôle appelé "postgres" et comme mot de passe "password".
**Le login de base est nommé postgres, assurez vous bien que son mot de passe soit le bon**

![Screenshot 2022-10-10 122051](https://user-images.githubusercontent.com/56387759/194850943-78fbf032-1a5e-43a1-a28c-ea356a5f554f.png)
![Screenshot 2022-10-10 122524](https://user-images.githubusercontent.com/56387759/194851057-76e4b29e-8bc4-4474-9d52-f831bed7b1f3.png)

- Créer une base de donnée 
![Screenshot 2022-10-10 122702](https://user-images.githubusercontent.com/56387759/194851082-2b5e18ce-7907-40f1-be9c-a5af6c37edb7.png)

- Nommez la "SmartVote" et assignez-en le propriétaire à postgres
![Screenshot 2022-10-10 123105](https://user-images.githubusercontent.com/56387759/194851101-60fbfbd2-b5c2-4fd7-a54f-c03f2cfa8301.png)

### Pré-requis des des dépendances
- Créer un environnement virtuel python 3.9.12:
```shell
conda create -n voteEnv python=3.9.12
```

- Activez l'environnement virtuel 
```shell
conda activate voteEnv
```

- Localiser vous dans "**Decentralized-Elections/Application**"

- Installer les requirements 
```shell
pip install -r requirements.txt
```

Si vous avez une erreure, modifier le Character Path Limit en allant dans l'éditeur de registre et changez
LongPathsEnabled à 1 localiser dans **HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\FileSystem**
![Screenshot 2022-10-10 124806](https://user-images.githubusercontent.com/56387759/194851121-656f42d9-18cf-42ab-a9e7-256dd5c2e3f2.png)

- Localiser vous dans "**Decentralized-Elections/Application/SmartVote**"

- Créer un superuser 
```shell
python manage.py createsuperuser
```
Login : admin
email :
password : password

- Mettez à jour la base de donnée
```shell
python manage.py migrate
python manage.py makemigrations
```

- Lancez l'application
```shell
python manage.py runserver
```
