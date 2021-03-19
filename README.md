# SportTrack
*Jonathan Duplaix*

Application Android de suivi d'objectifs sportifs

## Disclaimer
La démarche sur ce projet suit un parti pri très clair:  
Atteindre le résultat !  

Il faut comprendre par là que malgré le caractère exploratoire de la réalisation  
(approriation des TDs Android, découvertes Java, Gitflow, ...), l'objectif est d'arriver à couvrir  
les principaux cas d'usage du produit en passant outre certaines bonnes pratiques,  
l'optimisation du code ou encore le peaufinage (pour ne pas parler de beautification) de l'interface.  

Ces critères qualitatifs rentrerons néanmoins en ligne de compte dès qu'un service nominal pourra être rendu.


## Conventions de nommage
- Java = CamelCase
    - lower pour les variables et les noms de méthodes
    - upper pour les classes
    - préfixer les instance de composants de layout par le type de composant (Ex. : EditText avec id _sportLabel_ = **et**SportLabel)
- lower CamelCase pour les id de composants de layout
- snake_case pour les fichiers .xml
- pas de rêgle arrêtée pour la documentation : rester cohérent vis à vis de l'existant
- langues : code=anglais (tant que faire se peut), commentaires=français 


## Dépendances
- androidx.room : persistance des données avec SQLite
- com.google.android.material : FloatingActionButton + Snackbar

## Testing
:::caution
TODO: tests unitaires non implémentés.
:::
### Environnement
### Expresso

## Changelog
| Version | Livraison | Commentaire |
|-----|-----|-----|
| 1.1.0 | _19/03/2021_ | Enregistrement d'un suivi de durée en temps réel sur un activité sportive |
| 1.0.1 | _17/03/2021_ | Modif. layout formulaire CRUD Sport (test gitflow Hotfix) |
| 1.0.0 | _17/03/2021_ | Fonctionnalités liées à la  gestion des sports |


## Analyse
### Backlog
![backlog](/docs/backlog.png)
