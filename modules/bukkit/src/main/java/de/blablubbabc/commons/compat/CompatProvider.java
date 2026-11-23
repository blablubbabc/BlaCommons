package de.blablubbabc.commons.compat;

import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import de.blablubbabc.commons.annotations.ReadOnly;
import de.blablubbabc.commons.bootstrap.BlaCommons;
import de.blablubbabc.commons.util.inventory.UnmodifiableItemStack;
import de.blablubbabc.commons.util.inventory.UnmodifiableItemStackImpl;
import de.blablubbabc.commons.util.java.Unsafe;

/**
 * Server version and implementation specific functionality (e.g. Spigot vs Paper), as well as other
 * optional hooks that the host plugin can override to adapt certain aspects.
 * <p>
 * Implemented by the host plugin and passed to {@link BlaCommons#onLoad BlaCommons.onLoad}.
 */
public interface CompatProvider {

	/**
	 * Gets the {@link Registry} for the given type.
	 *
	 * @param <T>
	 *            the registry type
	 * @param type
	 *            the type, not <code>null</code>
	 * @return the registry, not <code>null</code>
	 * @throws IllegalStateException
	 *             if there is no registry for the given type
	 */
	public <T extends Keyed> Registry<@NonNull T> getRegistry(Class<T> type);

	/**
	 * Creates an {@link UnmodifiableItemStack} for the given {@link ItemStack}.
	 * <p>
	 * This is used by {@link UnmodifiableItemStack#of(ItemStack)}. Host plugins can return
	 * instances of a subclass of {@link UnmodifiableItemStackImpl} here, for example to
	 * additionally implement their own unmodifiable item stack interface that they can expose in
	 * their plugin API without having to expose this full library.
	 * 
	 * @param itemStack
	 *            the item stack, not <code>null</code>
	 * @return the unmodifiable item stack, not <code>null</code>
	 */
	public default UnmodifiableItemStack createUnmodifiableItemStack(
			@ReadOnly ItemStack itemStack
	) {
		return Unsafe.assertNonNull(UnmodifiableItemStackImpl.of(itemStack));
	}
}
