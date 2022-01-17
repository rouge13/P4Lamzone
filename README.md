# **Projet N°4 : "Maréu" OPENCLASSROOMS**
_________________________________________________________________________________________________________________

Ce dépot contient une mini-application pour le Projet 4 du parcours Grande École du Numérique pour le cursus Développeur d'Application Android

## Introduction de ce projet

"Maréu" est une application qui permet la gestion des réunions au sein d'une même entreprise avec les dates (début et fin de réunion), la salle et les participants de cette réunion.
_________________________________________________________________________________________________________________

## Android Studio et lien vers le projet **"Maréu"**
_________________________________________________________________________________________________________________
### 1. Installation et paramétrage d’Android Studio :  **[Lien d'installation](https://developer.android.com/studio)**
Avant de procéder à l’installation, identifiez le système d'exploitation de votre ordinateur. Si vous avez besoin d’aide pour l'installation et le paramétrage du logiciel, vous pouvez utiliser ce **[tutoriel](https://www.tutorialspoint.com/android/android_studio.htm)** en ligne

### 2. Téléchargez le code du projet avec git clone : **via ce [lien](https://github.com/rouge13/P4Lamzone/tree/develop)**
![Clone_P4_Mareu.JPG](Clone_P4_Mareu.JPG)
_________________________________________________________________________________________________________________

## Configuration pour accéder au projet
_________________________________________________________________________________________________________________

### 3. Ouvrez Android Studio. Cliquez sur "File" -> "New" -> "Check out project from Version Control" puis sur "Git"
![Check_out_project_from_version_control.jpg](Check_out_project_from_version_control.jpg)

### 4. Copier et coller l'Url du dépôt Git dans le champ "URL"
![Copier_coller_URL.JPG](Copier_coller_URL.JPG)

## Ou utilisez Git Bash et rentrez la ligne de commande suivante
![git clone -b develop https://github.com/rouge13/P4Lamzone.git](Ligne_de_commande_git_sur_git_bash.jpg)

###  NB : Si vous souhaitez enregistrer le projet sur un dossier de votre choix (autre que celui proposé par Android), modifiez le dossier d'installation du dépôt dans le champ "Directory"
Le projet est actuellement sur la branche : [develop](Branche_actuelle.jpg)
![Saisir_emplacement.JPG](Saisir_emplacement.JPG)

_________________________________________________________________________________________________________________

## Synchronisation Gradle et application
_________________________________________________________________________________________________________________

### 6. Si le message Sync apparait en haut à droite, synchronisez le projet avec Gradle en cliquant sur l'icône "éléphant"

### 7. Compilez le projet à l'aide de l'icône "marteau"

### 8. Si besoin, sélectionnez un appareil Android et lancez le projet en cliquant sur l'icône "flèche verte" en vérifiant bien que "app" soit bien sélectionnée à droite du marteau et fonctionne sous 

![Etape_6_7_8_9_lancement_application.png](Etape_6_7_8_9_lancement_application.png)


_________________________________________________________________________________________________________________

## Pourquoi l'utilisation du langage Java pour le développement de cette application
_________________________________________________________________________________________________________________

- *Les précédents projets on aussi était réalisé avec ce langage*
- *Maturité du langage et du support qui le relie qui permet une grande fiabilité du code pour les années futures*
- *Mise à jour régulière et évolution constante même si de plus en plus les applications se font en Kotlin*
- *Au vu des nombreuses année où le langage existe on constate qu'il y a une importante communauté de développeurs expérimentés*

_________________________________________________________________________________________________________________

## Ajouts réalisées : 
- *Création du projet et la mise en place du versioning sur GitHub*
- *Mise en place du listing des réunions comprenant : l’heure de la réunion, le lieu de la réunion, le sujet de la réunion, la liste des participants (adresses mail)*
- *Ajout et suppression d'une réunion*
- *Ajout du menu filtrage par date, lieu de réunion ou l'annulation du filtrage*
- *Création des différentes classes pour réaliser cette fonctionnalités*
- *Réalisation de [tests unitaires](https://github.com/rouge13/P4Lamzone/blob/develop/Test%20Results%20-%20MareuServiceTest.html)*
- *Réalisation des [tests instrumentés](https://github.com/rouge13/P4Lamzone/blob/develop/Test%20Results%20-%20ProjectActivitiesTest.html)*
- *Ajout du Fichier README.md pour l'installation du projet sur un autre poste*


