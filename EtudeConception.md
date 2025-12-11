# Étude de Conception

---

## 1. Contexte et Objectifs

L'objectif de cette étude de ce projet est de réaliser une application de simulation automatique. Ce projet s'inscrit dans le cours de qualité de dévellopement a destinations des professeurs Michael Martin Nevot, Hadjem Eliesse et De Vido Marina.

---

## 2. Fonctionnalités

L'utilisateur doit pouvoir :
- Lancer une simulation 
- Explorer les lieux et les personnages
- Recruter, soigner, nourrir, transférer, rappeler des personnages
- Observer les batailles
- Gérer la fin de la simulation afin de pouvoir relancer une simulation.

---

## 3. Architecture technique

L'application a été développé en séparant le code par grande classe de fonctionnalité ( Lieux/Personnages/Items/Conssomables) eux meme dans des packages sous format MVC.

---

## 4. Comportement importants

Une simulation commence après avoir choisi les différents paramètres de celle-ci dans le menu de départ. Ces paramètres sont : le nombre de zones, le nombre de personnages et le temps avant la fin.
Suite à cela, tout est géré dans différentes classes. L'affichage se fait dans GameGUI. La création des lieux, des personnages et des chefs de clan, la mise en marche de la simulation sont assurées (ou "se trouvent"), la création de la nourriture, tout cela se trouve dans InvasionTheatre, et les actions des personnages sont choisies aléatoirement dans CharacterThread, lancé par InvasionTheatre.

---

## 5. Contraintes techniques

Technologies utilisées :
- Language utilisée : Java
- Bibliothèques : JavaFX pour l'interface graphique
- Outils de développement : IntelliJ IDEA
- Système de gestion de version : Git

Choix technique importants :
- Nous avons fait le choix d'utiliser des thread afin de pouvoir gerer plusieurs actions simultanément et creer se sentiment de simulation en temps réel (tour par tour).

---

# La cloture du travail

---

## 1. Synthèse du projet

L'entiereté du TP3 et TP4 a été implémenté. Cependant, certaines fonctionnalités n'ont pas été utiliser dans la simulation malgré leur leur présence dans les classes. Cependant une interface JavaFX propre et clair et présente en bonus afin de pouvoir mieux visualiser et suivre la simualtion.

---

## 2. Bilan technique

L'utilisation de Jacoco a permis de montrer le fait que c'e'st compliqué de couvrir l'entiereté du code avec les test unitaires mais cela a aussi permis de montrer l'utilité des test unitaires lors du projet. De plus la mise en lumière de certaines fonctionnalité comme les threads nous as été bénéfique afin de comprendre l'utilité de pouvoir gérer plusieurs actions en même temps.

---

## 3. Problèmes rencontrés

Tout au long du projet nous avons rencontré plusieurs problèmes. La plupart d'entre eux été liés aux dépendance pour Jacoco, JavaFX ou encore JUnit. Cepandant une fois c'est probleme reglés les parties les plus diffciles étaient de comprendre les instructions et de les adaptées par rappor a nos conaissance a notre maitrise de qualité de dev (Avec une utilisation efficace de tout ce qu'on as vu en cours).



