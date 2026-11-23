package de.blablubbabc.sample;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.potion.PotionType;

import de.blablubbabc.commons.compat.CompatProvider;
import de.blablubbabc.commons.util.java.Unsafe;
import de.blablubbabc.commons.util.java.Validate;

public class SampleCompatProvider implements CompatProvider {

	// Spigot specific: On Paper, use RegistryAccess.registryAccess().getRegistry(registryKey)
	// instead, with a mapping from the registry type to the corresponding RegistryKey.
	@Override
	public <T extends Keyed> Registry<T> getRegistry(Class<T> type) {
		// Some API-only registries are not supported by Bukkit.getRegistry(Class):
		if (type == EntityType.class) {
			return Unsafe.castNonNull(Registry.ENTITY_TYPE);
		} else if (type == Particle.class) {
			return Unsafe.castNonNull(Registry.PARTICLE_TYPE);
		} else if (type == PotionType.class) {
			return Unsafe.castNonNull(Registry.POTION);
		} else if (type == MemoryKey.class) {
			return Unsafe.castNonNull(Registry.MEMORY_MODULE_TYPE);
		} else {
			return Validate.State.notNull(
					Bukkit.getRegistry(type),
					() -> "Unsupported registry type: " + type.getName()
			);
		}
	}
}
