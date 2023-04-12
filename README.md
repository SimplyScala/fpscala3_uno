## La progammation fonctionnelle, avec les types, à travers le jeu Uno

### Présentation

Ce projet a pour but d'illustrer plusieurs concepts/pattern de la programmation fonctionnelle statiquement typée, avec Scala 3, en 
utilisant pour cela une implémentation du célèbre jeu Uno.

### Concepts illustrés

- l’immutabilité générale / la transparence référentielle
- les types algébriques de données (ADT)
- les types opaques / les types riches
- les classe de type (typeclass)
- les systèmes d'effets (IO)
- la programmation concurrente / parallèle
- les Stream

### Les règles du Uno

https://www.regledujeu.fr/uno/

### Progression

la progression du développement du jeu; en fonction des concepts illutrés; est faite via les branches git préfixées avec le label `step`

La branche `rawDev` représente le jeu coder "complètement" (avec tous les concepts d'un coup)

## Démo
                         
### Pré-requis
                    
#### PostgreSQL

> avoir à disposition une BDD PostgreSQL version 14      

#### variables d'environnements

> renseigner ces variables d'environnement avec les valeurs de votre base postgres et exporter les dans le scope de votre appli

```
export POSTGRESQL_ADDON_DB="dbname"
export POSTGRESQL_ADDON_HOST="localhost"
export POSTGRESQL_ADDON_PASSWORD=""
export POSTGRESQL_ADDON_PORT="db_port"
export POSTGRESQL_ADDON_USER="dbuser"
export POSTGRESQL_ADDON_VERSION="14"
```

#### branche git

> la branche `rawDev` contient le code le plus "complet"

### Lancer le serveur

> sbt
 
> sbt:fpScala3_Uno> ~reStart
 
> 1

> http://localhost:2000/api/v1
 
### lancer un client

> curl GET http://localhost:2000/api/vEvent/unogame/98064f99-fc82-4634-86e3-28d048be3754

### jouer un commande Uno

cf. `src/main/resources/Unogame.http`

## Ressources

https://github.com/softwaremill/tapir/blob/master/examples/src/main/scala/sttp/tapir/examples/streaming/StreamingZioHttpServer.scala

https://zio.dev
https://zio.dev/reference/concurrency/hub


ZIO.attempt(Random.shuflle(seq))