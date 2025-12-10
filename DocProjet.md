# Documentation de la mise en place du ProjetLaGaule

## Table des matières
1. [Framework utilisé](#framework-utilisé)
2. [Organisation du projet](#organisation-du-projet)
3. [Gestion des tâches et sprints](#gestion-des-tâches-et-sprints)

---

## Framework utilisé

Afin de mettre en place un projet efficace et fonctionnel, nous avons utilisé le framework Agile.

### Principes Agile appliqués
- **Développement itératif** : Le projet a été divisé en sprints successifs
- **Collaboration continue** : Utilisation de GitHub pour le travail collaboratif

---

## Organisation du projet

### Outils de gestion utilisés

#### GitHub Issues
- **Création des issues** : Chaque fonctionnalité, bug ou amélioration est trackée via une issue GitHub
- **Labels** : Utilisation de labels pour catégoriser les issues (bug, feature, enhancement, documentation)
- **Assignation** : Chaque issue est assignée à un ou plusieurs membres de l'équipe

#### GitHub Projects
- **Board Kanban** : Utilisation d'un board avec les colonnes suivantes :
  - **To Do** : Tâches planifiées pour le sprint en cours
  - **In Progress** : Tâches en cours de développement
  - **Done** : Tâches terminées et validées

- **Vues personnalisées** : 
  - Vue par sprint
  - Vue par assigné
  - Vue par priorité

## Gestion des tâches et sprints

### Découpage en tâches

Chaque fonctionnalité majeure a été décomposée en tâches atomiques :

1. **Identification des User Stories** : Définition des besoins utilisateur
2. **Décomposition technique** : Chaque User Story est divisée en tâches techniques
3. **Estimation** : Chaque tâche est estimée en points de complexité ou en temps
4. **Priorisation** : Les tâches sont ordonnées selon leur importance et leurs dépendances

### Organisation en Sprints

#### Durée des sprints
- Durée standard : 1 semaines par sprint
- Sprints numérotés et planifiés via GitHub 

##### Sprint Review
- **Quand** : À la fin de chaque sprint
- **Objectif** : Démonstration des fonctionnalités développées
- **Résultat** : Validation ou feedback

##### Sprint Retrospective
- **Quand** : Après chaque sprint
- **Objectif** : Identifier les points d'amélioration
- **Résultat** : Actions d'amélioration pour le prochain sprint

## Bonnes pratiques adoptées

### Code
- **Clean Code** : Noms explicites, fonctions courtes
- **Tests** : Couverture de code avec JaCoCo
- **Documentation** : Javadoc pour toutes les classes et méthodes publiques
- **SOLID Principles** : Respect des principes de conception

### Collaboration
- **Communication** : Mise à jour régulière des issues et du board
- **Revue de code** : Validation par les pairs
- **Documentation** : Mise à jour continue de la documentation

### Qualité
- **Tests automatisés** : Exécution via Maven
- **Intégration continue** : Validation à chaque push
- **Standards de code** : Conventions Java respectées

---

## Ressources

- **Repository GitHub** : https://github.com/BAURENS-TORRENTE-Valentin-24006115/ProjetLaGaule
- **GitHub Projects** : https://github.com/BAURENS-TORRENTE-Valentin-24006115/ProjetLaGaule
- **Documentation Javadoc** : Disponible dans le dossier `Documentation/`
- **Maven Central** : Dépendances définies dans `pom.xml`

---

## Conclusion

Ce projet démontre l'application concrète des méthodologies Agile dans un contexte de développement logiciel. L'utilisation combinée de GitHub Issues, GitHub Projects et de sprints bien organisés permet une gestion efficace et transparente du projet, favorisant la collaboration et la livraison continue de valeur.

