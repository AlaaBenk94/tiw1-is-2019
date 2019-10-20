Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP 2 : Conteneurs d'objets et inversion de contrôle
## Auteurs
BENKARRAD Alaa Eddine

AISSAOUI AbdelWalid

## Features
### 1. IoC
Pico container nous a permet d'inverser le controle de l'application et d'implémenter differents types d'injection des dépendences. 
### 2. Isolation & Uniformisation
Notre framework possède un controleur principale `Controleur.class` qui transfert la requete aux controleurs délégués `xxxResource`.
### 3. Annuaire
Nous avons mis en place un annuaire qui stocke tous les composants de notre application. Cet annuaire peut etre intérogé par un chemin vers la ressource (par exemple : `\serveur\application\AbonneResource.class`)
### 4. Fichier de configuration
Dans cette application, le fichier de configuration permet de :
- choisir les composant de l'application et leurs types
- choisir le mécanisme d'injection de ces composants (`sdi=true` => injection par dépendence)
- choisir le type de cache de ces composants (`cache=false` => permet plusieurs instances de composants)
Voici un exemple de fichier de configuration 
```
{
  "application-config": {
    "name": "my-trottinettes",
    "business-components": [
      {"class-name": "tiw1.emprunt.controleur.TrottinetteResource"},
      {"class-name": "tiw1.emprunt.controleur.AbonneResource",
        "params": [{
        "name" : "sdi",
        "value": true
      }]},
      {"class-name": "tiw1.emprunt.controleur.EmpruntResource"},
      {"class-name": "tiw1.emprunt.controleur.Controleur"}
    ],
    "service-components": [
      {"class-name": "tiw1.emprunt.pool.TrottinettePool"},
      {"class-name": "tiw1.emprunt.contexte.AnnuaireImpl"},
      {"class-name": "tiw1.emprunt.interceptor.InterceptorChainImpl"},
      {"class-name": "tiw1.emprunt.interceptor.LoggerInterceptor"},
      {"class-name": "tiw1.emprunt.interceptor.JSONConverterInterceptor"},
      {"class-name": "java.util.ArrayList"},
      {"class-name": "java.lang.String"},
      {"class-name": "java.util.HashMap",
        "params": [{
          "name" : "cache",
          "value": false
        }]}
    ],
    "persistence-components": [
      {
        "class-name": "tiw1.emprunt.persistence.AbonneDAO",
        "params": [{
          "name": "file",
          "value": "abonnes.json"
        }]
      }, {
        "class-name": "tiw1.emprunt.persistence.EmpruntDAO",
        "params": [{
          "name": "base",
          "value": "H2"
        }, {
          "name": "sdi",
          "value": true
        }]
      }
    ]
  }
}
```

### 5. Annotations 
Dans cette partie, nous avons utiliser l'annotation `Todo` pour génerer des fichiers html.

### 6. Pooling 
Le pool implémenté permet de controler l'accés à la liste des trottinettes. En effet, pour simuler un temps traitement sur une instance des trottinette, nous avons ajouter un `sleep()` dans laquelle l'instance sera verouillée et devérouillée à la fin de ce temps.

### 7. Intercepteurs
Nous avons mis en place une chaine d'intercepteurs dans laquelle nous avons ajouté deux implémentation d'intercepteurs :
- `LoggerInterceptor` qui affiche les requetes passé à l'application
- `JSONInterceptor` qui change le type des sortie en `JSON`

## 8. Pipline & Wiremock
Nous avons remarqué que jetty ne fonctionne plus sur le pipline. donc nous avons mis en place un mock `Wiremock` de l'api REST afin de passer les tests. De plus nous avons modifier ce fichier pour qu'il prend en compte le lancement de mock avant le lancement de projet `Emprunt`