Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP 1 : Mise en route Java

N'oubliez pas de faire une branche git `tp1` dans laquelle vous ferez toutes les modifications de ce TP.
Si vous travaillez à deux, créez bien ce projet dans un groupe gitlab et faites chacun un fork.

## Objectifs

- Se réapprorier Java
- Se souvenir de la structure d'une application Web Java avec une API REST
- Écrire des tests unitaires
- Revoir l'intégration continue avec gitlab

## Application fournie

L'application fournie se trouve dans le répertoire [maintenance-web](../root/maintenance-web). Il s'agit d'une application Web REST sans interface utilisateur, qui s'appuie sur la technologie Spring.
Elle permet une gestion simplifiée de la maintenance des trottinettes. 

Attention: ce projet Maven est en fait un sous projet du projet ``root``, une partie de la configuration ce trouve donc dans ce dernier.

### Architecture

Le code fourni est décomposé en un ensemble packages selon l'utilisation des classes.
Le package ``models`` contient une classe ``Trottinette`` et une classe ``Intervention`` qui sont des entités correspondant à des données en base.
Le package ``metier`` contient une classe ``Maintenance`` contenant le code métier correspondant à la maintenance des trottinettes (ajout, interventions, etc. ).
Le package ``rest`` contient un contrôleur Spring ``TrottinetteController`` permettant d'exposer sous forme d'API REST le métier codé dans ``Maintenance``.
Le package ``spring`` contient les classes permettant d'initialiser et de configurer les composants Spring dans le conteneur de servlet (dans ce TP on utilisera [Jetty](https://www.eclipse.org/jetty/) embarqué comme [plugin Maven](https://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html).) 

> Parcourir le projet et comprendre le code des classes.
>
> Démarrer ensuite le serveur Web via le plugin Jetty pour Maven, vérifier son bon fonctionnement dans les logs, puis arrêter le serveur (via ctrl-C). 

### Tests

#### Avec JUnit

Le code fourni contient une classe de test ``MaintenanceTest`` avec tests pour vérifier le fonctionnement du composant ``Maintenance``.
Cette classe configure le contexte Spring de l'appplication et récupère le composant ``Maintenance`` afin de le rendre disponible aux méthodes de test.
Pour rappel, ce type de test utilise le framework [JUnit 4](https://junit.org/junit4/) et est exécutable via le [plugin Maven surefire](https://maven.apache.org/surefire/maven-surefire-plugin/) déclenché dans la phase de test de maven (par exemple via la commande ``mvn test``).

> Lancer les tests junit de l'application dans votre IDE et depuis la ligne de commande.
>
> Ajouter des tests pour vérifier le bon fonctionnement de la méthode ``ajouterIntervention``. 

#### Avec JMeter

[JMeter](http://jmeter.apache.org/) est une application de test automatisé pour les application Web.
Elle permet de réaliser des tests de charge, mais on peut égelement l'utiliser pour effectuer des tests fonctionnels.
Une séquence de test est décrite dans le fichier [jmeter-tests.jmx](../root/maintenance-web/src/test/jmeter/jmeter-tests.jmx).

> Comprendre le cas de test décrit dans le fichier [jmeter-tests.jmx](../root/maintenance-web/src/test/jmeter/jmeter-tests.jmx) en ouvrant ce fichier dans l'interface graphique de jmeter. 
> Vérifier si le test se déroule correctement ou non.
> Le cas échéant, faire le nécessaire pour que le cas de test se déroule sans erreur. 
> Lancer ensuite jmeter en mode batch et vérifier le bon déroulement des tests.
>
> Retourner dans l'éditeur de JMeter et créer un nouveau cas de test pour l'ajout d'une intervention.

## Intégration continue

Le projet est préconfiguré pour utiliser l'[intégration continue de la forge gitlab](https://about.gitlab.com/product/continuous-integration/).

Celle-ci est configurée dans le fichier [.gitlab-ci.yml](../.gitlab-ci.yml) à la racine du dépôt.

> Ouvrir le fichier dans l'IDE et expliquer ce que fait chaque ligne de ce fichier.
> Expliquer pourquoi le script de la tâche ``test-maintenance-api`` est plus complexe que la tâche ``package``.
> Expliquer à quel moment les tests JUnit sont exécuter.

## Ajout d'un modèle pour les gérer les batteries

On souhaite à présent modéliser les batteries, leur installation dans les trottinettes, leur mise en charge.
On souhaite donc pouvoir effectuer les actions suivantes via l'API REST de l'application: 

- Ajouter une nouvelle batterie
- Indiquer qu'une batterie a été installée sur une trottinette
- Indiquer qu'une betterie a été retirée d'une trottinette
- Indiquer qu'une batterie a été mise en charge
- Indiquer qu'une batterie a été chargée
- Pouvoir consulter l'historique de charge d'une batterie
- Pouvoir connaître dans les données d'une trottinette la batterie qui y est actuellement installée (si elle en a une).

Pour cela on va procéder fonctionnalité par fonctionnalité en suivant les étapes ci-dessous:

* Déterminer les données nécessaires à conserver en base (i.e. en enrichissant les classes du modèle dans le package ``models``).
* Ajouter la fonctionnalité via une nouvelle méthode métier dans la classe ``Maintenance``
* Tester la nouvelle fonctionnalité de la classe ``Maintenance`` via un ou plusieurs tests JUnit.
* Ajouter la route correspondant à la fonctionnalité dans le contrôleur REST
* Créer un cas de test fonctionnel automatisé via JMeter

Même si on procède par fonctionnalité, on essaiera de limiter le refactoring en particulier sur le modèle en anticipant en partie les fonctionnalités suivantes. 
