# BlaCommons

A collection of reusable helper libraries for Bukkit / [Spigot](https://www.spigotmc.org/wiki/spigot/) plugins. Paper is supported as well, but other server variants are untested and not explicitly supported.

Feel free to use these libraries in your own projects. However, the primary focus of these libraries is their use in my other Bukkit plugin projects, most notably [Shopkeepers](https://github.com/Shopkeepers/Shopkeepers). Changes, additions and Bukkit version bumps are therefore aligned with the requirements of these other projects. Backwards compatibility is not a primary focus: Code may be added, removed or moved around as I see fit.

This project is **not a Bukkit plugin**. Instead, the libraries are meant to be **shaded** (and relocated!) into the plugins that use them. The code is split into several modules, so that plugins only include what they actually need. To further reduce the size of the shaded library code, you can for example use the `minimize` feature of the [Gradle Shadow plugin](https://gradleup.com/shadow/configuration/minimizing/).

## Modules

All artifacts use the group id `de.blablubbabc` and share the root package `de.blablubbabc.commons`. Modules depend on each other as shown below, and the dependencies are transitive (`api` scope).

| Module | Artifact id | Depends on | Content |
| --- | --- | --- | --- |
| `core` | `blacommons-core` | external-annotations | General Java helpers (`util.java`). Does not depend on Bukkit. |
| `bukkit` | `blacommons-bukkit` | core | General Bukkit/Spigot helpers (`util.bukkit`) and the `BlaCommons` bootstrap. |
| `text` | `blacommons-text` | core, bukkit | A `Text` model that converts to Spigot components. |
| `data` | `blacommons-data` | core, bukkit | Data and serialization abstractions. |
| `config` | `blacommons-config` | core, bukkit, text, data | Annotation based configuration framework. |
| `commands` | `blacommons-commands` | core, bukkit, text | Command framework. |
| `ui` | `blacommons-ui` | core, bukkit, text | Inventory based UI framework. |
| `input` | `blacommons-input` | core, bukkit | Chat and interaction player input handling. |
| `external-annotations` | `blacommons-external-annotations` | none | External Eclipse null annotations for null analysis during development. |
| `test` | none | all | Tests. |

## Versioning Scheme

The project follows [Semantic Versioning](https://semver.org/): `Major.Minor.Patch[-pre.id][+metadata]`
- `Major`: Backwards incompatible changes.
- `Minor`: Backwards compatible feature additions.
- `Patch`: Backwards compatible bug fixes.

## Usage

Via Gradle:

```
// Maven repository:
repositories {
  maven { url = 'https://raw.githubusercontent.com/blablubbabc/BlaCommonsRepository/main/releases/' }
}

// Add the library modules you want to use in your plugin:
dependencies {
  implementation 'de.blablubbabc:blacommons-commands:<version>'
}

// Shading, e.g. with the Gradle Shadow plugin:
// Always relocate the library packages into your plugin's namespace to avoid conflicts with other plugins that shade other versions of this library.
shadowJar {
  relocate 'de.blablubbabc.commons', 'your.plugin.libs.common'
}
```

## Host Plugin Setup

Plugins that use the library call the bootstrap class `de.blablubbabc.commons.bootstrap.BlaCommons` (module `bukkit`):

* `BlaCommons.onLoad(plugin, modules...)` at the start of `onLoad`.
* `BlaCommons.onEnable()` at the start of `onEnable`.
* `BlaCommons.onDisable()` at the end of `onDisable`.

Library components that need a plugin instance (for example to register listeners or schedule tasks) use the plugin passed to `onLoad`.

The `modules` are the `BlaCommonsModule` instances of all used library modules, in dependency order. Every module except `core` and `bukkit` provides a module class (e.g. `TextModule`, `ConfigModule`, `UIModule`, etc.) in their respective `bootstrap` sub-package (e.g. `de.blablubbabc.commons.text.bootstrap`).

Example: `BlaCommons.onLoad(this, TextModule.INSTANCE, UIModule.INSTANCE);`

The `bukkit` module contains a `Compat` hook for functionality that might differ between server versions or server implementations. Host plugins must provide their own `CompatProvider` implementation via `Compat.setProvider(...)` early during startup, before `BlaCommons.onLoad` is called.

## Building

Clone the Git repository and then run:
```
./build.sh
```

This installs the required JDK, builds all modules, runs the tests, and installs the artifacts into your local Maven repository. The jars are also copied into the `build` folder.

## Contributing

See [Contributing](CONTRIBUTING.md).
