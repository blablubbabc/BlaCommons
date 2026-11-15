package de.blablubbabc.commons.util.bukkit;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.util.inventory.ItemUtils;
import de.blablubbabc.commons.util.inventory.UnmodifiableItemStack;

/**
 * Utilities related to merchants and trading.
 */
public final class MerchantUtils {

	public static MerchantRecipe createMerchantRecipe(
			UnmodifiableItemStack resultItem,
			UnmodifiableItemStack buyItem1,
			@Nullable UnmodifiableItemStack buyItem2
	) {
		assert !ItemUtils.isEmpty(resultItem) && !ItemUtils.isEmpty(buyItem1);
		// The items are already copied on various occasions (addIngredient, getIngredients, when
		// converting to a Minecraft recipe, etc.), so we do not need to copy them ourselves here.
		// The only exception to this is the result item: The MerchantRecipe does not copy it during
		// construction, nor during getResult. Copying the result item here ensures that we do not
		// accidentally encounter unmodifiable merchant recipe result items in contexts in which we
		// do not expect them.
		// No max-uses limit
		MerchantRecipe merchantRecipe = new MerchantRecipe(resultItem.copy(), Integer.MAX_VALUE);
		merchantRecipe.setExperienceReward(false); // No experience rewards
		merchantRecipe.addIngredient(ItemUtils.asItemStack(buyItem1));
		if (buyItem2 != null) {
			merchantRecipe.addIngredient(ItemUtils.asItemStack(buyItem2));
		}
		return merchantRecipe;
	}

	public static abstract class MerchantRecipeComparator {

		public abstract boolean equals(
				@Nullable MerchantRecipe recipe1,
				@Nullable MerchantRecipe recipe2
		);

		public boolean equals(
				@Nullable List<? extends @Nullable MerchantRecipe> recipes1,
				@Nullable List<? extends @Nullable MerchantRecipe> recipes2
		) {
			if (recipes1 == recipes2) return true;
			if (recipes1 == null || recipes2 == null) return false;
			if (recipes1.size() != recipes2.size()) return false;
			for (int i = 0; i < recipes1.size(); ++i) {
				MerchantRecipe recipe1 = recipes1.get(i);
				MerchantRecipe recipe2 = recipes2.get(i);
				if (!this.equals(recipe1, recipe2)) {
					return false;
				}
			}
			return true;
		}
	}

	public static final MerchantRecipeComparator MERCHANT_RECIPES_EQUAL_ITEMS = new MerchantRecipeComparator() {
		@Override
		public boolean equals(@Nullable MerchantRecipe recipe1, @Nullable MerchantRecipe recipe2) {
			if (recipe1 == recipe2) return true;
			if (recipe1 == null || recipe2 == null) return false;
			if (!recipe1.getResult().equals(recipe2.getResult())) return false;

			List<ItemStack> ingredients1 = recipe1.getIngredients();
			ingredients1.removeIf(ItemUtils::isEmpty);

			List<ItemStack> ingredients2 = recipe2.getIngredients();
			ingredients2.removeIf(ItemUtils::isEmpty);

			if (!ingredients1.equals(ingredients2)) return false;
			return true;
		}
	};

	// Does not compare the exact amounts of uses and max-uses, but the 'is blocked' state has to
	// match.
	public static final MerchantRecipeComparator MERCHANT_RECIPES_IGNORE_USES_EXCEPT_BLOCKED = new MerchantRecipeComparator() {
		@Override
		public boolean equals(@Nullable MerchantRecipe recipe1, @Nullable MerchantRecipe recipe2) {
			if (recipe1 == recipe2) return true;
			if (recipe1 == null || recipe2 == null) return false;

			boolean isBlocked1 = (recipe1.getUses() >= recipe1.getMaxUses());
			boolean isBlocked2 = (recipe2.getUses() >= recipe2.getMaxUses());
			if (isBlocked1 != isBlocked2) return false;
			if (recipe1.hasExperienceReward() != recipe2.hasExperienceReward()) return false;
			if (recipe1.getPriceMultiplier() != recipe2.getPriceMultiplier()) return false;
			if (recipe1.getVillagerExperience() != recipe2.getVillagerExperience()) return false;
			if (!recipe1.getResult().equals(recipe2.getResult())) return false;

			List<ItemStack> ingredients1 = recipe1.getIngredients();
			ingredients1.removeIf(ItemUtils::isEmpty);

			List<ItemStack> ingredients2 = recipe2.getIngredients();
			ingredients2.removeIf(ItemUtils::isEmpty);

			if (!ingredients1.equals(ingredients2)) return false;

			return true;
		}
	};

	private MerchantUtils() {
	}
}
