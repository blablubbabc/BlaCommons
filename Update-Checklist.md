# Update Checklist

## Minecraft/Bukkit Version Update

* If we require a new JDK version to build:
	* Define a version alias for the JDK version in 'scripts/installJDK.sh'.
	* Update the JDK installation entry in 'build.sh'.
	* Update the JDK version inside the GitHub workflow '.github/workflows/build.yml'.

* If we target a new Java version:
	* Update the 'sourceCompatibility' inside the root 'build.gradle' and 'modules/sample/build.gradle' files.
	* Update the 'ECJ_PREFERRED_JAVA_VERSION' inside the root 'build.gradle' file.

* New mobs:
	* Check which equipment slots are supported and adjust EquipmentUtils.

* New blocks or items:
	* Check the ItemUtils if there are any material lists or mappings that need to be updated.
		* Containers, chests, shulker boxes, signs, rails.
		* Wool material by dye color, carpet material by dye color.

* New enchantments:
	* Check the aliases inside EnchantmentUtils.

* New potion types or items with potion data:
	* Check the aliases and the parsing inside PotionUtils.

* New MerchantRecipe properties:
	* Update the comparator in MerchantUtils.
	* Check the MerchantRecipe constructions in MerchantUtils.

* New registries or changes to existing registries:
	* Check if the CompatProvider of the 'sample' module needs to special-case additional registries that are not supported by Bukkit#getRegistry(Class).

* Changes to the Bukkit API:
	* Check if the external annotations in 'modules/external-annotations' need to be updated for changed method signatures.

* Check if the assumptions in 'Assumptions.md' still hold.

* If there are major differences, consider bumping the lowest supported Bukkit version.
	* Update the minimal Bukkit/Spigot/Paper dependency versions inside the 'gradle/libs.versions.toml' file.
	* Update the 'api-version' inside the 'plugin.yml' file of the 'sample' module.
	* Update the lowest supported MC version inside the changelog entry of the next version.
	* Update the code base (optional):
		* Check if there are new Bukkit features that can replace portions of the existing compat specific code.
		* Check for TODO notes that mention Bukkit or Minecraft version dependencies.

## On every update

* Build and test the new version.
* Make sure the changelog is complete. Fill in the release date.
* Update the version in the root 'gradle.properties' file (remove the '-SNAPSHOT' tag).
* Commit, build and publish: './gradlew cleanPublish'.
* Add a new git tag for that version and push it.
* Update the version in the root 'gradle.properties' file for working on the next version. Re-add the '-SNAPSHOT' tag.
* If not yet done, add a new entry inside the changelog for the next version.
* Commit.
* Optional: Update the BlaCommons version in the plugins that use it (e.g. Shopkeepers).

