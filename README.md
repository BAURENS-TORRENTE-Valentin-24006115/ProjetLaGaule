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


## Comment l'installer

Étape 1 - Cloner le repository :
```
git clone https://github.com/BAURENS-TORRENTE-Valentin-24006115/ProjetLaGaule
```

Étape 2 - Compiler le projet :
```
mvn clean install
```

Étape 3 - Lancer l'application :
```
mvn exec:java -Dexec.mainClass="fr.iut.laGaule.Main"
```


