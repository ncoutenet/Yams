---
name: release
description: Run the full Maven release workflow (prepare + perform) for GestioFav. Use when the user wants to cut a release.
disable-model-invocation: true
---

The user wants to cut a Maven release. Follow these steps in order:

1. **Pre-flight checks** — confirm all of these before touching the release plugin:
   - Run `mvn clean test` and ensure tests pass.
   - Run `git status` — working tree must be clean (no uncommitted changes).
   - Confirm the current branch is `master` (or the user has confirmed releasing from another branch intentionally).
   - Check `git log --oneline origin/master..HEAD` — all commits should already be pushed.

2. **Run release:prepare** — this creates the release tag and bumps the POM version to the next snapshot:
   ```
   mvn release:prepare
   ```
   Maven will prompt for the release version, tag name, and next development version. Accept the defaults or adjust as needed. The tag format used in this repo is just the version number (e.g., `1.0.6`).

3. **Run release:perform** — this checks out the tag and builds/deploys the release artifact:
   ```
   mvn release:perform
   ```

4. **Verify** — after both commands complete:
   - Check that the new tag exists: `git tag -l`
   - Check that `pom.xml` now shows the next `-SNAPSHOT` version.
   - Push tags if not already pushed: `git push --tags`

5. **If something goes wrong** — use `mvn release:rollback` to undo `release:prepare`, then fix the issue and retry.