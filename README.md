# ProjetLaGaule
Projet de simulation Java développé dans le cadre du cours de qualité de développement


## Description : 

La Gaule est une application de simulation d’envahissement de l’Armorique au temps de l’Empire autocratique romain et de la Gaule.
Le projet implémente un système de personnages, de lieux et d'événements, avec gestion de la nourriture, de la santé et des interactions entre différents characters.
Chaque personnage possède ses propres caractéristiques et peut interagir avec son environnement. 

### Fonctionnalités :

Différents types de personnages (druides, forgerons, légionnaires, généraux...)
Système de simulation par tours
Gestion de la santé et de la nourriture
Événements aléatoires
Sauvegarde de l'état du jeu
Exécution parallèle avec threads


### Installation et configuration

#### 1. Cloner le repository
```bash
git clone https://github.com/BAURENS-TORRENTE-Valentin-24006115/ProjetLaGaule.git
cd ProjetLaGaule
```

#### 2. Structure du projet
Le projet suit une architecture Maven standard :
```
LaGaule/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── fr/
│   │           └── iut/
│   │               └── laGaule/
│   └── test/
│       └── java/
├── pom.xml
└── target/
```

#### 3. Build du projet
```bash
cd LaGaule
mvn clean install
```

#### 4. Exécution des tests
```bash
mvn test
```

#### 5. Génération de la documentation Javadoc
```bash
mvn javadoc:javadoc
```
La documentation sera générée dans le dossier `Documentation/`.

#### 6. Génération de la couverture de code Jacoco
```bash
mvn jacoco:report
```

La documentation sera générée dans le dossier "LaGaule/target/site"

---


