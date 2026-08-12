# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Yams is a Java Swing desktop application for playing Yam's (Yahtzee), for 1 to 10 players, started in 2012. No database — scores and preferences are persisted to flat `.dat` files on disk (see `yams.folder.DataFolder`).

The developer is french, so speak with them in French.

## Build

This project follows the **standard Maven directory layout**:
- Sources: `src/main/java/`
- Resources: `src/main/resources/`
- Tests: `src/test/java/` (JUnit 5, see the Testing section below).

```bash
mvn clean package   # compile + plain JAR in target/
```

The project has **no external runtime dependencies** (JUnit 5 and JaCoCo in `pom.xml` are test-scope only), so `mvn package` produces a plain (non-shaded) JAR: `target/yams-1.0-SNAPSHOT.jar`, executable directly with `java -jar target/yams-1.0-SNAPSHOT.jar` (`Main-Class: yams.Yams` is set via `maven-jar-plugin`).

The project was converted from a NetBeans/Ant build to Maven in 2026 (see commit "Mavenisation du projet"); the old `nbproject/`, `build.xml`, and `manifest.mf` were removed and are fully replaced by `pom.xml`.

## Java version constraint

**Always target Java 1.8 source compatibility.** The `pom.xml` sets `maven.compiler.source`/`target` to `1.8`. The code uses `java.applet.Applet.newAudioClip` for sound playback, which is deprecated but still present on Java 8.

## Resource loading

Images and sounds live under `src/main/resources/images/` and `src/main/resources/sons/`, loaded via absolute classpath paths, e.g. `getClass().getResource("/images/dés/normal/1.png")` or `"/sons/dé_roulant.wav"`. There is **no `resources/` prefix inside `src/main/resources`** — the folder name itself is `images`/`sons` directly at the resources root. Keep any new asset under one of these two folders and reference it the same way; do not reintroduce a `resources/resources/...` nesting.

The top-level `dés/` folder (GIFs) at the repository root is legacy and unused by any `.java` file — do not confuse it with `src/main/resources/images/dés/`, which is the one actually packaged into the JAR.

## Code style

All identifiers are in French throughout the codebase (`YamModele`, `YamControl`, `JeuVue`, `Joueur`, `ConfirmQuitVue`, `FinPartieVue`, etc.) — there is no English/French mix here, follow French naming when extending the code. There is no i18n/resource-bundle mechanism; UI strings are hardcoded in French directly in the view classes.

## Persistence

`yams.folder.DataFolder` resolves an OS-specific storage directory at runtime (`%AppData%/yams/` on Windows, `~/Library/Application Support/yams/` on macOS, `~/.yams/` elsewhere) and stores scores/preferences there as serialized `.dat` files. This is unrelated to the Maven `src/main/resources` classpath resources above — don't conflate the two when debugging "file not found" issues.

## Testing

JUnit 5 tests exist under `src/test/java/`, mirroring the `src/main/java/` package layout (e.g. `yams.pojos.Joueur` → `yams.pojos.JoueurTest`). The established pattern is a `@BeforeEach setUp()` building the fixture, then one `@Test` per behavior (see `JoueurTest`, `ScoreTest`, `ColorTabTest`).

**Any new or modified non-Swing class — a POJO, enum, model, or other class with no `javax.swing`/AWT dependency and no real I/O — must have a corresponding test class following this pattern.** Swing UI behavior (views, controllers driving components) cannot be verified by `mvn package` alone — after a change touching the UI, build with `mvn clean package` and ask the user to run `java -jar target/yams-1.0-SNAPSHOT.jar` and check manually; do not launch the application yourself and claim the feature works from the build alone.

## IDE

IntelliJ run configurations are committed under `.idea/runConfigurations/`: `Yams` (Application, debuggable, main class `yams.Yams`) and `yams [clean,package]` (Maven `clean package`).
