# Manuel d’utilisation de la simulation « La Gaule »

Ce document explique comment utiliser pas à pas l’application de simulation « La Gaule ». Il propose plusieurs scénarios d’utilisation concrets pour prendre en main l’interface et comprendre le déroulement d’une simulation.

> Remarque : ce manuel est rédigé pour un utilisateur ne connaissant pas le code. Aucune connaissance en Java n’est nécessaire.

---

## 1. Présentation générale

### 1.1. Objectif de la simulation

« La Gaule » est une simulation mettant en scène des **Gaulois**, des **Romains** et éventuellement des **créatures mythiques**. La simulation gère automatiquement :

- des **lieux** (villages gaulois, camps romains, villes, champs de bataille, enclos, etc.) ;
- des **personnages** (légionnaires, généraux, chefs de clan, druides, marchands…) ;
- l’**évolution en temps réel** : déplacements, combats, soins, alimentation, etc. ;
- un **temps de simulation** limité (compte à rebours).

Votre rôle est d’**observer**, mais aussi **d’agir** sur certains paramètres : recruter de nouveaux personnages, en soigner d’autres, nourrir les habitants, les déplacer entre les lieux…

### 1.2. Vocabulaire de base

- **Lieu** : zone de la carte (village gaulois, camp romain, ville, enclos, champ de bataille…).
- **Personnage** : habitant d’un lieu (gaulois, romain, chef de clan, druide, marchand, etc.).
- **Chef de clan** : personnage spécial permettant d’exécuter certaines actions (recruter, soigner, rappeler…).
- **Simulation** : déroulement automatique des événements pendant un temps donné.

---
## 2. Découverte de l’interface

Après le lancement, deux grands écrans se succèdent :

1. **Écran de configuration** : pour choisir les paramètres de la simulation.
2. **Écran principal de jeu** : pour suivre et influencer la simulation.

### 2.1. Écran de configuration

Cet écran comporte généralement :

- un champ **« Nombre de Zones »** : nombre de lieux différents dans la simulation ;
- un champ **« Nombre de Personnages »** : nombre total de personnages ;
- un champ **« Durée (secondes) »** : durée totale de la simulation ;
- un bouton **« LANCER LA SIMULATION »**.

Quand vous remplissez ces informations puis cliquez sur **« LANCER LA SIMULATION »**, la simulation est créée et l’écran principal s’affiche.

En cas de problème (valeur non numérique, champ vide, valeur absurde…), un **message d’erreur** apparaît pour vous indiquer quoi corriger.

### 2.2. Écran principal de jeu

L’écran principal est organisé en plusieurs zones :

- **Haut de l’écran** :
  - un bouton **« MENU »** pour revenir à l’écran de configuration ;
  - un **label d’information** indiquant le **temps restant** et la **population totale** (ex. : `TEMPS : 04:32 | POPULATION : 25`).
- **Colonne de gauche** : liste des **Lieux** (villages, camps, champs de bataille, etc.).
- **Colonne centrale** : liste des **Habitants** du lieu sélectionné.
- **Colonne de droite (Inspecteur)** : zone de texte affichant les détails du **lieu** ou du **personnage** sélectionné.
- **Bandeau inférieur** : boutons d’actions, par exemple :
  - **« Recruter »**
  - **« Soigner »**
  - **« Nourrir »**
  - **« Potion »**
  - **« Transférer »**
  - **« Rappeler »**

L’ensemble de ces zones se **met à jour automatiquement** au fur et à mesure que la simulation progresse.

---

## 4. Scénario 1 – Lancer une nouvelle simulation

### Objectif

Démarrer une nouvelle simulation avec vos propres paramètres (nombre de zones, personnages, durée).

### Prérequis

- L’application est lancée.
- Vous êtes sur l’**écran de configuration**.

### Étapes pas à pas

1. **Choisir le nombre de zones**
   - Dans le champ **« Nombre de Zones »**, saisir une valeur entière (par exemple **5**).
2. **Choisir le nombre de personnages**
   - Dans le champ **« Nombre de Personnages »**, saisir une valeur entière (par exemple **20**).
3. **Choisir la durée de la simulation**
   - Dans le champ **« Durée (secondes) »**, saisir la durée totale (par exemple **300** secondes = 5 minutes).
4. **Lancer la simulation**
   - Cliquer sur le bouton **« LANCER LA SIMULATION »**.

### Ce qui se passe à l’écran

- La simulation est **initialisée** :
  - les lieux sont créés ;
  - les personnages sont répartis dans ces lieux ;
  - le temps de simulation est pris en compte.
- Vous êtes automatiquement redirigé vers l’**écran principal de jeu** :
  - la liste des **Lieux** est remplie ;
  - la liste des **Habitants** dépend du lieu sélectionné ;
  - le **compte à rebours** démarre.

### Résultat attendu

Vous voyez la simulation démarrer, avec des lieux et des personnages affichés. Les boutons d’action en bas sont visibles et seront activés en fonction de ce que vous sélectionnez.

### Erreurs fréquentes

- **Valeur non numérique** (par ex. « cinq » au lieu de 5) :
  - Un message d’erreur apparaît. Corrigez en mettant un nombre entier.
- **Valeur négative ou nulle** :
  - Selon l’implémentation, un message d’erreur peut apparaître ou la simulation peut se comporter de manière inattendue. Utilisez des valeurs positives raisonnables.

---

## 5. Scénario 2 – Explorer les lieux et les habitants

### Objectif

Apprendre à naviguer entre les différents lieux et à consulter les habitants et leurs caractéristiques.

### Prérequis

- Une simulation est **en cours** (Scénario 1 déjà réalisé).
- Vous êtes sur l’**écran principal de jeu**.

### Étapes pas à pas

1. **Sélectionner un lieu**
   - Dans la liste **« Lieux »** (colonne de gauche), cliquer sur un lieu.
   - Exemples de lieux possibles : `Village Gaulois`, `Camp Romain`, `Ville Romaine`, `Enclos`, `Champ de Bataille`…
2. **Observer les habitants du lieu**
   - La liste **« Habitants »** (colonne centrale) se met à jour automatiquement.
   - Elle affiche maintenant tous les personnages présents dans le lieu sélectionné.
3. **Consulter les détails d’un personnage**
   - Dans la liste **« Habitants »**, cliquer sur un personnage.
   - L’**Inspecteur** (colonne de droite) affiche alors les informations détaillées :
     - type de personnage (gaulois, romain, chef de clan, etc.) ;
     - caractéristiques (points de vie, rôle, éventuellement d’autres attributs) ;
     - lieu d’appartenance.
4. **Revenir à l’affichage d’un lieu**
   - Cliquer à nouveau sur un lieu dans la liste **« Lieux »**.
   - L’Inspecteur affiche désormais les informations du lieu (type de lieu, nombre d’habitants, etc.).

### Ce qui se passe à l’écran

- L’interface se **rafraîchit automatiquement** au fil du temps :
  - les listes d’habitants se mettent à jour (arrivées, départs, morts éventuelles) ;
  - l’Inspecteur reflète toujours le lieu ou le personnage **actuellement sélectionné**.

### Résultat attendu

Vous êtes capable de :

- vous repérer dans les différents lieux ;
- afficher la liste de leurs habitants ;
- consulter les détails d’un personnage individuel.

---

## 6. Scénario 3 – Agir sur les personnages (recruter, soigner, nourrir, etc.)

### Objectif

Utiliser les boutons d’actions pour influencer la simulation : ajouter des personnages, les soigner, les nourrir, les déplacer…

### Prérequis

- Simulation **en cours**.
- Au moins un lieu avec un **chef de clan** (chef gaulois ou chef romain).
- Vous êtes sur l’**écran principal de jeu**.

### 6.1. Recruter un nouveau personnage

#### Objectif

Ajouter un nouveau personnage dans un lieu disposant d’un chef de clan.

#### Étapes pas à pas

1. **Choisir un lieu avec chef de clan**
   - Dans la liste **« Lieux »**, sélectionner un village ou un camp qui possède un chef de clan.
2. **Cliquer sur « Recruter »**
   - Le bouton **« Recruter »** (bandeau inférieur) doit être **actif**.
   - Cliquer dessus.
3. **Choisir le type de personnage**
   - Une fenêtre peut vous proposer plusieurs types de recrues, par exemple :
     - marchand gaulois, druide gaulois ;
     - légionnaire romain, général romain ;
     - autre type selon l’implémentation.
   - Sélectionner le type souhaité et valider.

#### Ce qui se passe à l’écran

- Le chef de clan du lieu crée un nouveau personnage.
- La liste des **Habitants** du lieu est **actualisée** : le nouveau personnage apparaît.

#### Résultat attendu

Le lieu sélectionné compte désormais **un habitant de plus**, correspondant au type choisi.

---

### 6.2. Soigner tous les personnages d’un lieu

#### Objectif

Redonner des points de vie aux personnages d’un lieu.

#### Étapes pas à pas

1. **Sélectionner un lieu**
   - Dans la liste **« Lieux »**, choisir un lieu disposant d’un chef de clan.
2. **Cliquer sur « Soigner »**
   - Dans le bandeau inférieur, cliquer sur **« Soigner »**.

#### Ce qui se passe à l’écran

- Tous les personnages du lieu reçoivent des **soins** (augmentation de leurs points de vie).
- Un message de confirmation peut apparaître (par exemple : « Tous les personnages ont été soignés ! »).

#### Résultat attendu

Les personnages de ce lieu sont en meilleure santé. Vous pouvez vérifier l’augmentation en consultant leurs détails dans l’Inspecteur.

---

### 6.3. Nourrir un personnage

#### Objectif

Donner de la nourriture à un personnage pour modifier ses caractéristiques (santé, force, etc.).

#### Prérequis spécifiques

- Un **lieu** sélectionné.
- Un **personnage** sélectionné dans la liste des Habitants.
- Le lieu dispose de **nourriture** en réserve.

#### Étapes pas à pas

1. **Sélectionner un lieu**
   - Cliquer sur un lieu dans la liste **« Lieux »**.
2. **Sélectionner un personnage**
   - Dans la liste **« Habitants »**, cliquer sur le personnage à nourrir.
3. **Cliquer sur « Nourrir »**
   - Dans le bandeau inférieur, cliquer sur **« Nourrir »**.

#### Ce qui se passe à l’écran

- Si aucun lieu ou personnage n’est sélectionné, un message d’erreur apparaît (par exemple : « Sélectionnez un personnage et un lieu ! »).
- Si le garde-manger est vide, un message du type « Le garde-manger est vide ! » peut s’afficher.
- Sinon, une nourriture est utilisée :
  - les effets de cette nourriture sont appliqués au personnage (augmentation de certaines caractéristiques, etc.) ;
  - l’Inspecteur met à jour les informations du personnage.

#### Résultat attendu

Le personnage nourri voit modifier ses caractéristiques (par exemple, davantage de points de vie ou d’autres bonus).

---

### 6.4. Utiliser une potion

*(Selon les fonctionnalités graphiques effectivement disponibles dans votre version, cette action peut être plus ou moins détaillée.)*

#### Objectif

Appliquer l’effet d’une potion à un personnage.

#### Étapes pas à pas (génériques)

1. **Sélectionner un personnage**
   - Dans la liste **« Habitants »**, choisir un personnage pouvant recevoir une potion.
2. **Cliquer sur « Potion »**
   - Dans le bandeau inférieur, cliquer sur **« Potion »**.
3. **(Si proposé) Choisir le type d’effet**
   - Une boîte de dialogue peut vous laisser choisir l’effet ou le type de potion.

#### Ce qui se passe à l’écran

- L’effet de la potion est appliqué (par exemple bonus temporaire, augmentation de caractéristiques…).
- Les informations du personnage sont mises à jour dans l’Inspecteur.

#### Résultat attendu

Le personnage bénéficie des effets de la potion (meilleure force, meilleure résistance, etc., selon l’implémentation).

---

### 6.5. Transférer un personnage vers un autre lieu

#### Objectif

Déplacer un personnage d’un lieu vers un autre (par exemple, envoyer un soldat au champ de bataille).

#### Étapes pas à pas

1. **Sélectionner le personnage à déplacer**
   - Choisir un **lieu de départ** dans la liste **« Lieux »**.
   - Dans la liste **« Habitants »**, cliquer sur le personnage à transférer.
2. **Cliquer sur « Transférer »**
   - Dans le bandeau inférieur, cliquer sur **« Transférer »**.
3. **Choisir le lieu de destination**
   - Une fenêtre ou une liste vous permet de choisir le **lieu d’arrivée**.
   - Valider votre choix.

#### Ce qui se passe à l’écran

- Le personnage est retiré du lieu de départ.
- Il est ajouté dans le lieu de destination.
- Les listes d’habitants des deux lieux se mettent à jour.

#### Résultat attendu

Le personnage apparaît désormais dans la liste des habitants du **nouveau lieu**.

---

### 6.6. Rappeler des personnages

#### Objectif

Rassembler des personnages dispersés vers le lieu du chef de clan.

#### Étapes pas à pas (génériques)

1. **Sélectionner le lieu du chef de clan**
   - Dans la liste **« Lieux »**, choisir le lieu où se trouve le chef de clan.
2. **Cliquer sur « Rappeler »**
   - Dans le bandeau inférieur, cliquer sur **« Rappeler »**.

#### Ce qui se passe à l’écran

- Les personnages concernés peuvent être ramenés vers le lieu du chef de clan (en fonction des règles définies dans la simulation).
- Les listes d’habitants se mettent à jour pour refléter ces déplacements.

#### Résultat attendu

Les troupes sont **regroupées** dans le lieu du chef de clan, ce qui permet par exemple de préparer une grande bataille.

---

## 7. Scénario 4 – Observer une bataille

### Objectif

Comprendre comment la simulation gère les combats et suivre l’évolution d’un affrontement.

### Prérequis

- Simulation **en cours** avec plusieurs lieux en interaction (par exemple un champ de bataille).

### Étapes pas à pas

1. **Repérer les lieux de conflit**
   - Dans la liste **« Lieux »**, identifier un lieu susceptible de combats (par exemple un **champ de bataille** ou un **camp assiégé**).
2. **Observer la composition des troupes**
   - Sélectionner ce lieu.
   - Regarder la liste des **Habitants** et distinguer :
     - les personnages gaulois ;
     - les personnages romains ;
     - éventuellement des créatures mythiques.
3. **Suivre l’évolution au cours du temps**
   - Laisser la simulation se dérouler quelques instants.
   - Observer les changements dans la liste des habitants : certains personnages peuvent disparaître (morts au combat), d’autres arriver ou repartir.
4. **Agir pendant ou avant la bataille**
   - Recruter de nouveaux personnages.
   - Soigner ceux qui sont blessés.
   - Transférer des renforts vers le lieu de conflit.

### Ce qui se passe à l’écran

- Le moteur de simulation calcule les résultats des combats.
- Les **habitants** des lieux engagés sont régulièrement mis à jour.
- Le **temps** continue de s’écouler, visible dans le label en haut.

### Résultat attendu

Vous visualisez la **dynamique de la bataille** et voyez l’impact de vos décisions (recrutement, soins, transferts) sur l’issue du conflit.

---

## 8. Scénario 5 – Gérer le temps et la fin de la simulation

### Objectif

Savoir interpréter le compte à rebours et comprendre ce qui se passe quand la simulation se termine.

### Prérequis

- Simulation **en cours**.

### Étapes pas à pas

1. **Observer le compte à rebours**
   - En haut de l’écran, repérer le label indiquant le temps :
     - `TEMPS : mm:ss | POPULATION : X`.
2. **Surveiller la fin de la simulation**
   - Quand le temps restant devient faible (par exemple **moins de 10 secondes**), la couleur du temps peut passer au **rouge** pour attirer votre attention.
   - Laisser le temps arriver à **zéro**.

### Ce qui se passe à l’écran

- Quand le temps est écoulé :
  - le **timer** de la simulation s’arrête ;
  - une **boîte de dialogue** s’affiche avec un **message de fin** (bilan de la partie).

### Résultat attendu

Vous voyez un **résumé** de la simulation (message de fin) et la simulation ne progresse plus. L’interface reste affichée, mais les listes ne changent plus.

---

## 9. Scénario 6 – Revenir au menu et lancer une nouvelle simulation

### Objectif

Arrêter la simulation actuelle et en démarrer une nouvelle avec d’autres paramètres.

### Prérequis

- Simulation **en cours** ou **terminée**.
- Vous êtes sur l’**écran principal de jeu**.

### Étapes pas à pas

1. **Cliquer sur « MENU »**
   - En haut de l’écran, cliquer sur le bouton **« MENU »**.
2. **Retour à l’écran de configuration**
   - L’interface revient à l’**écran de configuration** (nombre de zones, personnages, durée).
3. **Configurer une nouvelle simulation**
   - Reprendre les étapes du **Scénario 1** avec de nouveaux paramètres.

### Résultat attendu

Vous pouvez enchaîner **plusieurs simulations** différentes, par exemple pour comparer l’impact :

- d’un plus grand nombre de zones ;
- d’une population plus importante ;
- d’une durée plus longue ou plus courte.

---

## 10. Conseils d’utilisation et bonnes pratiques

### 10.1. Choisir de bons paramètres

- Commencer avec un **nombre modéré** de zones (3 à 5) et de personnages (10 à 30) pour bien comprendre le fonctionnement.
- Utiliser une durée de simulation suffisante (par exemple **120 à 300 secondes**) pour avoir le temps d’observer les effets de vos actions.

### 10.2. Stratégies possibles

- **Avant une bataille** : recruter des renforts et soigner vos troupes.
- **Pendant la simulation** :
  - surveiller régulièrement la santé des personnages clés ;
  - nourrir ou soigner ceux qui sont les plus importants ;
  - transférer des personnages vers les lieux stratégiques (camps, champs de bataille).

### 10.3. Bien lire les informations affichées

- Le **label en haut** vous donne un résumé global (temps + population).
- L’**Inspecteur** à droite donne des informations détaillées :
  - sur les lieux (type, nombre d’habitants, etc.) ;
  - sur les personnages (rôle, points de vie, autres caractéristiques).


---

## 11. Conclusion

Ce manuel présente les principaux scénarios d’utilisation de la simulation « La Gaule » :

- lancer une nouvelle simulation ;
- explorer les lieux et les habitants ;
- agir sur les personnages (recruter, soigner, nourrir, déplacer…) ;
- observer les batailles ;
- gérer la fin de la simulation et relancer une nouvelle partie.

N’hésitez pas à adapter les paramètres et à expérimenter plusieurs fois pour bien comprendre l’impact de vos actions sur le déroulement de la simulation.
