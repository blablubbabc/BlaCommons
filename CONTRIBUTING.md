# Contributing to BlaCommons

### Pull Requests

Keep them small and easy to review.

For larger changes, or if you are not clear whether a particular addition fits this project, please first create a ticket to discuss it.

### Module Boundaries

Code is split into modules (see the README). Please do not introduce dependencies from lower-level modules (e.g. `core`) to higher-level ones, and keep `core` free of any Bukkit dependency.

### Volatile Code

Avoid code that relies on CraftBukkit, NMS, or reflection of these packages.

Any code that is specific to Bukkit versions or server variants (Spigot vs Paper) must stay behind the `CompatProvider` abstraction in the `bukkit` module. Keep such code minimal, because this interface must be implemented by every consuming project.

### Coding Style

Try to stick to the overall coding style currently used.
