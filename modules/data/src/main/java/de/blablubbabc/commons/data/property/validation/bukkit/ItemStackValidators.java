package de.blablubbabc.commons.data.property.validation.bukkit;

import org.bukkit.inventory.ItemStack;

import de.blablubbabc.commons.data.property.validation.PropertyValidator;
import de.blablubbabc.commons.util.inventory.ItemUtils;
import de.blablubbabc.commons.util.inventory.UnmodifiableItemStack;
import de.blablubbabc.commons.util.java.Validate;

/**
 * Default {@link PropertyValidator}s for {@link ItemStack} values.
 */
public final class ItemStackValidators {

	/**
	 * Default {@link PropertyValidator}s for {@link UnmodifiableItemStack} values.
	 */
	public static final class Unmodifiable {

		/**
		 * A {@link PropertyValidator} that ensures that the validated {@link UnmodifiableItemStack}
		 * is not {@link ItemUtils#isEmpty(UnmodifiableItemStack) empty}.
		 */
		public static final PropertyValidator<UnmodifiableItemStack> NON_EMPTY = (value) -> {
			Validate.isTrue(!ItemUtils.isEmpty(value), "Item stack is empty!");
		};

		private Unmodifiable() {
		}
	}

	/**
	 * A {@link PropertyValidator} that ensures that the validated {@link ItemStack} is not
	 * {@link ItemUtils#isEmpty(ItemStack) empty}.
	 */
	public static final PropertyValidator<ItemStack> NON_EMPTY = (value) -> {
		Validate.isTrue(!ItemUtils.isEmpty(value), "Item stack is empty!");
	};

	private ItemStackValidators() {
	}
}
