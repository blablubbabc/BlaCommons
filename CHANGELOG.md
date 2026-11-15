# Changelog
Date format: (YYYY-MM-DD)  

## v1.0.0 (TBA)
### Lowest supported MC version: 1.21.5

* Initial version: Move common utilities out of the Shopkeepers project into this library, split into the modules:
  * `core`
  * `bukkit`
  * `text`
  * `data`
  * `config`
  * `commands`
  * `ui`
  * `input`
  * `external-annotations`
* Host plugins call `BlaCommons.onLoad`, `onEnable` and `onDisable` of the `bukkit` module.
