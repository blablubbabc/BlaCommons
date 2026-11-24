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
| `sample` | none | all | An example plugin. |

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

For an example, see the [build.gradle](modules/sample/build.gradle) of the sample plugin.

## Host Plugin Setup

Plugins that use the library set it up via the bootstrap class `de.blablubbabc.commons.bootstrap.BlaCommons` (module `bukkit`):

```java
public class MyPlugin extends JavaPlugin {

  @Override
  public void onLoad() {
    // At the start of onLoad:
    BlaCommons.onLoad(this, new MyCompatProvider(), TextModule.INSTANCE, UIModule.INSTANCE);

    // Optional, at any time, e.g. after loading the plugin's config:
    Debug.setDebugging(true);
  }

  @Override
  public void onEnable() {
    // At the start of onEnable:
    BlaCommons.onEnable();
  }

  @Override
  public void onDisable() {
    // At the end of onDisable:
    BlaCommons.onDisable();
  }
}
```

* `MyCompatProvider`: The plugin's own implementation of the `CompatProvider` interface (module `bukkit`), for functionality that might differ between server versions or server implementations.
* `Debug` (module `core`): The library's debug output is disabled by default. Plugins can enable it at any time via `Debug.setDebugging`, and enable individual debug options via `Debug.setDebugOptions` or `Debug.setDebugOption`, e.g. when loading their config or via a command. The modules define their debug options in their `*DebugOptions` classes (e.g. `TextDebugOptions`), and register them in `DebugOptions` when they are loaded.
* The modules: The `BlaCommonsModule` instances of all used library modules, in dependency order. Every module except `core` and `bukkit` provides one in its `bootstrap` sub-package (e.g. `de.blablubbabc.commons.text.bootstrap.TextModule`).

Library components that need a plugin instance (for example to register listeners or schedule tasks) use the plugin passed to `onLoad`.

For an example, see the [`SamplePlugin`](modules/sample/src/main/java/de/blablubbabc/sample/SamplePlugin.java).

## Building

Clone the Git repository and then run:
```
./build.sh
```

This installs the required JDK, builds all modules, runs the tests, and installs the artifacts into your local Maven repository. The jars are also copied into the `build` folder.

## Contributing

See [Contributing](CONTRIBUTING.md).
