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

Spring est un framework qui fêtera ses 20 ans l'an prochain, en 2023. En 20 ans, l'écosystème Java ainsi que les
bonnes pratiques associées à la création d'applications d'entreprise ont beaucoup évolué.

Pour des questions de rétrocompatibilité, Spring laisse toujours le choix : code récent ou code "old-school", ce choix
devient notre responsabilité en tant que développeurs.

### Architecture en couches

Dans une application monolithique traditionnelle, il peut être tentant de commencer à coder pêle-mêle. Or, respecter une
décomposition en plusieurs couches est essentielle pour coller
aux [principes SOLID](https://www.baeldung.com/solid-principles).

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

Les différentes couches de notre application se doivent d'être testées, chacune à leur échelle et en utilisant différents outils. Nous pouvons mettre en place intensivement des tests unitaires connus pour être les plus précis, fiables et rapides à réaliser au sein de [la classification en pyramide des types de tests](https://blog.octo.com/la-pyramide-des-tests-par-la-pratique-1-5/). La seule condition pour réaliser ce type de tests aisément est d'avoir un code peu endetté techniquement, c'est ici le cas de par la décomposition en couches.

Tester unitairement une classe en isolation nécessite d'avoir le contrôle sur ce qu'on peut appeler *les sorties indirectes* de celle-ci, c'est à dire les interactions avec les dépendances que notre classe possède, qu'elles soient concrètes ou abstraites (elles seront évidemment quasiment systématiquement abstraites, pour respecter le principe d'inversion de dépendances évoqué précédemment). 

Ecrire un test unitaire va donc commencer par créer et substituer les dépendances de notre [sujet sous test (*subject under test ou **SUT** en Anglais*)](http://xunitpatterns.com/SUT.html) par des [doubles de test](https://blog.pragmatists.com/test-doubles-fakes-mocks-and-stubs-1a7491dfa3da), généralement des **mocks**. 

Dans le cas de notre service, le tester en isolation revient à instancier la classe pour obtenir une instance de notre SUT, substituer le repository injecté par un mock et réaliser notre test en 3 étapes : [Arrange, Act, Assert (aussi appelé Given, When, Then)](https://www.qwan.eu/2021/09/02/tdd-given-when-then.html)

Lors de la phase *Arrange* nous allons faire du *stubbing*, qui consiste à configurer notre mock afin qu'il puisse simuler le comportement d'une ou plusieurs sorties indirectes (donc les *méthodes*) en temps normal :
- Retourner une valeur : 
```java
when(repository.findById(any())).thenReturn(Optional.of(sampleLandingPad));
```
- Jeter une exception : 
```java
when(restTemplate.getForObject(anyString(), any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
```

Lors de la phase *Act*, appeler la méthode de notre SUT que l'on teste, avec les paramètres qui définissent le cas de test.

Lors de la phase *Assert*, nous allons vérifier que la méthode appelée :
- Retourne bien la bonne valeur : 
```java
assertEquals(Optional.of(sampleLandingPad), result);
```
- Ou a jeté la bonne exception : 
```java
assertThrows(RSpaceXApiException.class, () -> landingpadsRepository.findAll());
```

> Pourquoi tester qu'une valeur récupérée en sortie de notre SUT est identique à notre stubbing ? Cela semble inutile.

Car le fait de passer la valeur une couche au-dessus, depuis la dépendance jusqu'à l'appelant sans la modifier est un détail d'implémentation à vérifier. Tout comme le fait de transformer une valeur est à tester :wink:.

Il est également et finalement nécessaire d'effectuer des *vérifications* sur nos mocks concernant les valeurs des paramètres passés aux méthodes des dépendances de notre SUT.

```java
verify(repository).findById("id");
verifyNoMoreInteractions(repository);
```

**Si l'on possède un test où plusieurs méthodes du même mock sont appelées, ou si plusieurs mocks interagissent de concert, il est également nécessaire de vérifier l'ordre d'invocation des méthodes (Mockito expose *[inOrder](https://frontbackend.com/java/how-to-verify-the-order-of-calls-using-mockito)* pour cela)**

> Quand utiliser des *[argument matchers](https://www.baeldung.com/mockito-argument-matchers)* avec Mockito ?


Je vous conseillerai de les utiliser lorsque vous faites du stubbing, pour se faciliter la vie. Soyez plus ou moins précis selon les différents stubbings appliqués au mock, **mais n'utilisez jamais les matchers pour les vérifications** : c'est le but même de l'approche de vérifier les valeurs passées.

```java
when(repository.findById(any())).thenReturn(Optional.of(sampleLandingPad)); ✅
when(repository.findById(eq("id"))).thenReturn(Optional.of(sampleLandingPad)); ✅
when(repository.findById("id")).thenReturn(Optional.of(sampleLandingPad)); ✅

verify(repository).findById("id"); ✅
verify(repository).findById(eq("id")); ✅
verify(repository).findById(any()); ❌
```

**Lorsque notre SUT est utilisable sans Spring, il est intéressant de se demander si configurer notre test pour utiliser Spring est judicieux**. Le test de notre service n'a besoin que du support de Mockito pour fonctionner :
```java
@ExtendWith(MockitoExtension.class)
class RepositoryBasedLandingpadsServiceShould {

    @Mock
    private LandingpadsRepository repository;

    @InjectMocks
    private RepositoryBasedLandingpadsService landingpadsService;
```

Dans le cas de notre contrôleur, le tester en l'appelant programmatiquement n'a que peu de valeur : il ne sera jamais utilisé tel quel, Spring l'encapsulera toujours dans son conteneur d'inversion de contrôle afin de faire correspondre les requêtes HTTP entrantes aux méthodes des différents contrôleurs qui ont été déclarées. 

De plus, une méthode de contrôleur possède de la configuration spécifique à Spring, que le framework utilise : **tester tout ceci d'un bloc et utiliser Spring est judicieux**. On sera techniquement en présence d'un test d'intégration puisque l'on fait fonctionner notre code avec celui de Spring, mais le code de Spring étant testé et les sorties indirectes du contrôleur mockées, nous pouvons considérer que nous restons dans l'aspect unitaire :blush:.

L'initialisation d'un test avec Spring étant plus lourde, nous allons configurer notre test unitaire afin que le contexte d'application ne contienne que [le strict nécessaire à l'exécution de celui-ci](https://developer.okta.com/blog/2021/07/12/spring-boot-test-slices#when-to-avoid-springboottest-annotation), pour un cas d'usage Web : grâce aux test slices de Spring Boot, apposer [`@WebMvcTest`](https://developer.okta.com/blog/2021/07/12/spring-boot-test-slices#test-controllers-with-webmvctest) sur la classe de test nous permet de configurer automatiquement, et obtenir dans nos tests une instance d'un `MockMvc`  qui nous servira à simuler des requetes HTTP et vérifier les réponses que fournira notre contrôleur.

```java
@WebMvcTest
class LandingpadsControllerShould {

    @Autowired
    private MockMvc mockMvc;
```

Je ne détaillerai pas ici l'utilisation des APIs Spring de test, mais voici quelques petits conseils d'usage :
- Réutilisez vos DTOs dans vos tests, que ce soit dans les données envoyées ou reçues. Vous pouvez avoir accès à l'ObjectMapper dans vos tests, et l'utiliser permet de tester la configuration JSON appliquée aux modèles
`.andExpect(content().json(objectMapper.writeValueAsString(List.of(sampleLandingPadResponse))))`
- Evitez d'avoir des tests trop peu sensibles au refactoring, vérifiez strictement le contenu JSON obtenu en réponse : `.andExpect(content().json("{'field': 'value'}"), true))`
Le booléen passé en second paramètre dans notre exemple sert à activer le mode strict, qui vérifiera qu'aucune autre propriété que celles contenues dans le JSON passé n'est présente, en plus de vérifier les existentes, et s'il y en a d'absentes

> Pourquoi la classe de test est suffixée de "should" et non "test" ?

Vous connaissez l'adage : ["There are only two hard things in Computer Science..."](https://martinfowler.com/bliki/TwoHardThings.html). Nommer ses tests clairement et de façon concise est primordial : utiliser la syntaxe en "should" permet de créer un préfixe commun à nos tests qui vont tous vérifier un prérequis, et le nom de la méthode de test doit clairement expliciter le comportement attendu ainsi que les conditions qui amèneront à ce comportement, s'il y en a.

```java
class RepositoryBasedLandingpadsServiceShould {

    // Pas de conditions, on ne l'indique pas
    void returnAllLandingPads() throws Exception {}

    // On a une condition sur le paramètre id qui doit pointer sur un pas de tir existant
    void returnAKnownLandingPadGivenItsId() {}
```
