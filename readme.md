# Yams

Logiciel permettant de jouer au Yam's (Yahtzee).

De 1 à 10 joueurs.

## Prérequis

- Java 8

## Compilation et exécution

```bash
mvn clean package
java -jar target/yams-1.0-SNAPSHOT.jar
```

## Tests

Le projet utilise JUnit 5.

```bash
mvn test
```

Un rapport de couverture de code (JaCoCo) est généré à chaque exécution des
tests, consultable dans `target/site/jacoco/index.html`.

## Lancement sur macOS

Un `.jar` téléchargé depuis une release GitHub porte l'attribut de
quarantaine que macOS appose à tout fichier venant d'Internet. Un
double-clic dans le Finder affiche alors "yams-1.0-SNAPSHOT.jar est
endommagé et ne peut pas être ouvert" (message Gatekeeper, pas un jar
corrompu). Trois solutions :

- Lancer depuis un terminal (ne déclenche pas Gatekeeper) :
  ```bash
  java -jar yams-1.0-SNAPSHOT.jar
  ```
- Ou lever la quarantaine puis double-cliquer normalement :
  ```bash
  xattr -d com.apple.quarantine yams-1.0-SNAPSHOT.jar
  ```
- Ou, sans terminal : clic droit sur `yams-1.0-SNAPSHOT.jar` → **Compresser**,
  puis double-cliquer sur le `.zip` généré pour le ré-extraire. Le fichier
  extrait n'a plus l'attribut de quarantaine et s'ouvre normalement au
  double-clic.