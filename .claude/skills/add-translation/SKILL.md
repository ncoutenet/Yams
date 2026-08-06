---
name: add-translation
description: Add a new UI string key to both language files (English and French). Use when adding any new label, button text, title, or error message to the UI.
---

The user wants to add a new translatable UI string. Follow these steps:

1. **Choose a key name** — follow the existing convention in `src/main/resources/languages/language_en_US.properties` (dot-separated, e.g. `mainMenu.file.open`, `forms.titles.addFavoris`, `messages.confirm.quit`). If the user hasn't specified a key name, suggest one based on the UI context.

2. **Add to English file** — open `src/main/resources/languages/language_en_US.properties` and add the key with an English value. Place it in the logical section that matches the UI component (group related keys together).

3. **Add to French file** — open `src/main/resources/languages/language_fr_FR.properties` and add the same key with a French translation. If the user has not provided a French translation, ask them for it — do not guess or leave it as a copy of the English value.

4. **Wire it up in the Swing component** — retrieve the value via `gestiofav.services.I18n`, using the same pattern already used in the file where you're working. Example:
   ```java
   I18n.get("your.new.key")
   ```

5. **Verify** — confirm both property files now contain the new key before finishing.

Note: Both files must be saved as UTF-8. The `UTF8Controller` class handles this at load time, but ensure no editor re-saves them as ISO-8859-1.

Note: if the string is displayed anywhere other than the menu bar (which already refreshes via `MenuPrincipal.applyMenuLabels()`), check whether it needs to be included in a language-switch refresh path — otherwise it will keep showing in whatever language was active when the component was built. The bookmarks table/tabs are refreshed via `MenuPrincipal.updateLanguage` → `MenuController.refreshLanguage` → `PageController.refreshLanguage` → `AffichagePage.buildPanel`.