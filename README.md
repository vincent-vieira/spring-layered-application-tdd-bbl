# Conception et réalisation d'une application Spring en couches - Il est temps de faire un ménage de printemps !

## A propos de l'intervenant

Vincent VIEIRA, Technical Officer chez Carbon IT, est avide de connaissances et a pu expérimenter de multiples langages
dans différents domaines (banque, transports, commerce).

Chez Carbon IT, il se dédie à l'amélioration continue des consultants, ainsi que la sienne. Toujours à la recherche de
comprendre le pourquoi du comment, il a désormais envie de vous partager l'ensemble de ses découvertes.

## Contexte

Nous co-réaliserons un ensemble de tests unitaires en isolation, et observerons comment vous pouvez concevoir
efficacement une API REST en couches. Lors de ce live-coding sur les dernières versions de Spring Boot/MVC, vous
découvrirez un ensemble de bonnes pratiques que ce soit dans l'utilisation du paradigme orienté objet, ou celle du
framework Spring.

## Ce qu'il faut retenir

Spring est un framework qui fêtera ses 20 ans en l'an prochain, en 2023. En 20 ans, l'écosystème Java ainsi que les
bonnes pratiques associées à la création d'applications d'entreprise ont beaucoup évolué.

Pour des questions de rétrocompatibilité, Spring laisse toujours le choix : code récent ou code "old-school", ce choix
devient notre responsabilité en tant que développeurs.

### Architecture en couches

Dans une application monolithique traditionnelle, il peut être tentant de commencer à coder pêle-mêle. Or, respecter une
décomposition en plusieurs couches est essentielle pour coller
aux [principes SOLID](https://essential-dev-skills.com/poo/principe-solid).

Avoir des couches avec des responsabilités bien définies et séparées les unes des autres, sans superpositions
permet de respecter un maximum (voire l'intégralité d'entre eux, dans l'état de l'art).

Dans notre exemple (et dans la majorité des applications d'entreprise), imaginer trois couches différentes est adéquat :
la couche présentation (notre contrôleur REST), la couche métier (notre service) et la couche d'accès aux données (notre
repository).

Aussi, il est nécessaire d'éviter autant que possible de faire transiter des modèles entre les différentes couches,
chaque modèle ayant ses spécificités par rapport au contexte d'utilisation. Cela est d'autant plus vrai en Java où les
annotations vont très vite empêcher de réutiliser correctement un modèle dans une couche différente de celle où il a été
conçu pour initialement (**imaginez mélanger un modèle et lui associer des annotations Jackson et Hibernate en même
temps...**)

Des styles d'architecture comme l'Architecture Hexagonale sont donc *centrées sur le domaine métier* et distinguent les
modèles de transport de données (les fameux [DTO](https://www.baeldung.com/java-dto-pattern)) de ceux représentant le
métier, qui ne doit avoir aucune référence à "*l'infrastructure*" : ce terme fait référence à l'intégralité du code
spécifique au contexte d'utilisation nous permettant d'implémenter notre métier au sein d'une application; tout ce qui
est propre aux frameworks que nous utilisons, en gros.

### Découplage entre Spring et le code métier

Spring permet de déclarer des instances gérées par le conteneur d'inversion de contrôle de deux façons différentes :

- Par stéréotype (`@Component`, `@Configuration`, `@Service`, `@Repository`, `@Controller`)
- Par `@Bean`, apposée sur des méthodes au sein d'une classe de configuration (annotée `@Configuration` ou non)

La dernière est la plus propice à garder [un minimum de contrôle](https://stackoverflow.com/a/51238724) sur l'inversion
de contrôle fournie par le framework : entre autres, elle nous permet d'expliciter quelle sera la configuration de notre
application (quels beans seront créés/utilisables), ainsi que de séparer leur définition de classe de celle de leurs
instances éventuelles au sein du framework.

### Injection et inversion de dépendances

Le principe d'inversion de dépendances présent au sein des principes
SOLID (*[Dependency Inversion Principle](https://codeburst.io/understanding-solid-principles-dependency-injection-d570c15560ab)*)
édicte que
> les modules de haut niveau ne doivent pas dépendre de modules de bas niveau; tous deux doivent dépendre
> d'abstractions.

Cela signifie qu'au sein d'une classe, nous devons demander les dépendances depuis l'extérieur et ne pas avoir
connaissance de comment les instancier pour les utiliser (d'où l'intérêt de les rendre abstraites, cela rend impossible
toute instanciation).

En choisissant au sein de nos points d'injection de dépendances (**par constructeur**) d'utiliser des paramètres
abstraits,
nous permettons à chacune des couches de se reposer sur une abstraction représentant son fonctionnement dans l'idée
générale. Cela nous permet de ne pas avoir à nous soucier de comment fonctionneront nos dépendances (et potentiellement
de facilement en changer leur fonctionnement, pour autant que l'on respecte
l'*[Open-Closed Principle](https://medium.com/codeburst/understanding-solid-principles-open-closed-principle-e2b588b6491f)*)

### Tests unitaires en isolation

- Doubles de test
- Nommage du test différent
