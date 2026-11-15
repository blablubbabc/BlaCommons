package de.blablubbabc.commons.text.util;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.annotations.ReadOnly;
import de.blablubbabc.commons.text.HoverEventText;
import de.blablubbabc.commons.text.Text;
import de.blablubbabc.commons.text.TextBuilder;
import de.blablubbabc.commons.text.spigot.SpigotText;
import de.blablubbabc.commons.util.bukkit.TextUtils;
import de.blablubbabc.commons.util.inventory.ItemUtils;
import de.blablubbabc.commons.util.inventory.UnmodifiableItemStack;
import de.blablubbabc.commons.util.java.Validate;
import de.blablubbabc.commons.util.text.MessageArguments;

/**
 * Utilities for creating and sending {@link Text}s.
 * <p>
 * For utilities that only operate on plain Strings, see {@link TextUtils}.
 */
public final class TextHelpers {

	public static Text getPlayerText(Player player) {
		Validate.notNull(player, "player is null");
		return getPlayerText(player.getName(), player.getUniqueId());
	}

	public static Text getPlayerText(@Nullable String playerName, @Nullable UUID playerUUID) {
		// Either of them might be null.
		// Prefer name, else use uuid.
		if (playerName != null) {
			if (playerUUID != null) {
				String playerUUIDString = playerUUID.toString();
				return Text.hoverEvent(Text.of(playerUUIDString))
						.childInsertion(playerUUIDString)
						.childText(playerName)
						.buildRoot();
			} else {
				return Text.of(playerName);
			}
		} else if (playerUUID != null) {
			return Text.of(playerUUID.toString());
		} else {
			return Text.of(TextUtils.getPlayerNameOrUnknown(null));
		}
	}

	public static Text getItemText(@Nullable UnmodifiableItemStack itemStack) {
		return getItemText(ItemUtils.asItemStackOrNull(itemStack));
	}

	public static Text getItemText(@ReadOnly @Nullable ItemStack itemStack) {
		if (itemStack == null) return Text.text("");
		return getItemHover(itemStack)
				.child(getItemNameForDisplay(itemStack))
				.getRoot();
	}

	public static TextBuilder getItemHover(@ReadOnly ItemStack itemStack) {
		Validate.notNull(itemStack, "itemStack is null");
		var unmodifiableItem = UnmodifiableItemStack.ofNonNull(itemStack);
		return Text.hoverEvent(new HoverEventText.ItemContent(unmodifiableItem));
	}

	/**
	 * Formats the name of the given {@link Material} to a more user-friendly representation.
	 * <p>
	 * This first attempts to use a client-translatable Text, and if not available, falls back to
	 * using {@link ItemUtils#formatMaterialName(Material)}. If the given material is
	 * <code>null</code>, this returns an empty Text.
	 * 
	 * @param material
	 *            the material, can be <code>null</code>
	 * @return the formatted material name, not <code>null</code>
	 */
	public static Text getMaterialNameForDisplay(@Nullable Material material) {
		if (material == null) return Text.EMPTY;
		Text formattedName = Text.of(ItemUtils.formatMaterialName(material));
		// Note: This might not necessarily match the name that is usually displayed for an
		// ItemStack, but rather the translated item type name (for example for items such as
		// different types of potions, skulls, etc.).
		String translationKey = material.getTranslationKey();
		// We use the formatted name as fallback.
		return Text.translatable(translationKey).child(formattedName).getRoot();
	}

	/**
	 * Formats the {@link Material} name of the given {@link ItemStack} to a more user-friendly
	 * representation.
	 * <p>
	 * If the given item stack is <code>null</code>, this returns an empty Text.
	 * 
	 * @param itemStack
	 *            the item stack, can be <code>null</code>
	 * @return the formatted material name, not <code>null</code>
	 * @see #getMaterialNameForDisplay(Material)
	 */
	public static Text getMaterialNameForDisplay(@Nullable ItemStack itemStack) {
		return getMaterialNameForDisplay(itemStack != null ? itemStack.getType() : null);
	}

	/**
	 * Gets a display name for the given {@link ItemStack}.
	 * <p>
	 * This uses either the item's display name, if available, or falls back to the
	 * {@link #getMaterialNameForDisplay(ItemStack)}. This returns an empty Text if the item stack
	 * is <code>null</code>.
	 * 
	 * @param itemStack
	 *            the item stack, can be <code>null</code>
	 * @return the display name
	 */
	public static Text getItemNameForDisplay(@ReadOnly @Nullable ItemStack itemStack) {
		var displayName = ItemUtils.getDisplayNameOrEmpty(itemStack);
		if (!displayName.isEmpty()) {
			return Text.of(displayName);
		}

		return getMaterialNameForDisplay(itemStack);
	}

	// SENDING

	public static void sendMessage(CommandSender recipient, Text message) {
		SpigotText.sendMessage(recipient, message);
	}

	public static void sendMessage(
			CommandSender recipient,
			Text message,
			MessageArguments arguments
	) {
		Validate.notNull(recipient, "recipient is null");
		Validate.notNull(message, "message is null");
		// Assign arguments and then send:
		message.setPlaceholderArguments(arguments);
		sendMessage(recipient, message);
	}

	public static void sendMessage(
			CommandSender recipient,
			Text message,
			Map<? extends String, @NonNull ?> arguments
	) {
		Validate.notNull(recipient, "recipient is null");
		Validate.notNull(message, "message is null");
		// Assign arguments and then send:
		message.setPlaceholderArguments(arguments);
		sendMessage(recipient, message);
	}

	public static void sendMessage(
			CommandSender recipient,
			Text message,
			@NonNull Object... argumentPairs
	) {
		Validate.notNull(recipient, "recipient is null");
		Validate.notNull(message, "message is null");
		// Assign arguments and then send:
		message.setPlaceholderArguments(argumentPairs);
		sendMessage(recipient, message);
	}

	private TextHelpers() {
	}
}
