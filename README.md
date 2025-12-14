# Configuration de l'application

Pour que l'application fonctionne correctement, vous devez créer un fichier de configuration `config.properties`.

---

## 1. Emplacement du fichier

Le fichier `config.properties` doit être placé dans le répertoire suivant :


> Si le dossier `META-INF` n'existe pas, créez-le.

---

## 2. Contenu du fichier

Copiez-collez le contenu ci-dessous dans votre fichier `config.properties` :

```properties
# Database Configuration
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true
db.username=root
db.password=
db.jndi.name=jdbc/studentdb

# Connection Pool Settings
db.pool.maxTotal=20
db.pool.maxIdle=5
db.pool.maxWaitMillis=10000
