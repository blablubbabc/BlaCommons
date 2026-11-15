package de.blablubbabc.commons.util;

import static org.junit.Assert.*;

import org.bukkit.ChatColor;
import org.junit.Test;

import de.blablubbabc.commons.text.Text;
import de.blablubbabc.commons.util.bukkit.TextUtils;

public class TextTest {

	@Test
	public void testIsBukkitHexCode() {
		assertTrue("Bukkit hex code with & is not considered valid",
				TextUtils.isBukkitHexCode("&x&a&a&b&b&c&c"));
		assertTrue("Bukkit hex code with § is not considered valid",
				TextUtils.isBukkitHexCode("§x§a§a§b§b§c§c"));

		assertFalse("Too long Bukkit hex code is considered valid",
				TextUtils.isBukkitHexCode("&x&a&a&b&b&c&c&d"));
		assertFalse("Too short Bukkit hex code is considered valid",
				TextUtils.isBukkitHexCode("&x&a&a&b&b&c"));
		assertFalse("Incomplete Bukkit hex code is considered valid",
				TextUtils.isBukkitHexCode("&x&a&a&b&b&c&"));
		assertFalse("Bukkit hex code with invalid format character is considered valid",
				TextUtils.isBukkitHexCode("&#&a&a&b&b&c&c"));
		assertFalse("Bukkit hex code with invalid hex character is considered valid",
				TextUtils.isBukkitHexCode("&x&a&a&b&b&c&g"));
		assertFalse("Non-Bukkit hex code is considered valid Bukkit hex code",
				TextUtils.isBukkitHexCode("#aabbcc"));
		assertFalse("Non-Bukkit hex code with ampersand is considered valid Bukkit hex code",
				TextUtils.isBukkitHexCode("&#aabbcc"));
	}

	@Test
	public void testIsHexCode() {
		assertTrue("Hex code is not considered valid",
				TextUtils.isHexCode("#aabbcc"));

		assertFalse("Bukkit hex code is considered valid hex code",
				TextUtils.isHexCode("§x§a§a§b§b§c§c"));
		assertFalse("Too long hex code is considered valid",
				TextUtils.isHexCode("#aabbccd"));
		assertFalse("Too short hex code is considered valid",
				TextUtils.isHexCode("#aabbc"));
		assertFalse("Hex code without hex char is considered valid",
				TextUtils.isHexCode("aabbcc"));
		assertFalse("Hex code without hex char but correct length is considered valid",
				TextUtils.isHexCode("xaabbcc"));
		assertFalse("Hex code with invalid hex character is considered valid",
				TextUtils.isHexCode("#aabbcg"));
	}

	@Test
	public void testFromBukkitHexCode() {
		String expected = "#aabbcc";
		assertEquals("fromBukkitHexCode with & failed",
				expected, TextUtils.fromBukkitHexCode("&x&a&a&b&b&c&c"));
		assertEquals("fromBukkitHexCode with § failed",
				expected, TextUtils.fromBukkitHexCode("§x§a§a§b§b§c§c"));
	}

	@Test
	public void testToBukkitHexCode() {
		assertEquals("toBukkitHexCode with & failed",
				"&x&a&a&b&b&c&c", TextUtils.toBukkitHexCode("#aabbcc", TextUtils.COLOR_CHAR_ALTERNATIVE));
		assertEquals("toBukkitHexCode with § failed",
				"§x§a§a§b§b§c§c", TextUtils.toBukkitHexCode("#aabbcc", ChatColor.COLOR_CHAR));
	}

	// Tests the Text parsing and backwards conversion with some sample messages.
	@Test
	public void testMessageConversions() {
		String[] messages = {
				"&aPlain colored text",
				"&7Text with &e{placeholder}&7 and more",
				"&cMultiple\nlines &6of text",
				"No formatting at all"
		};
		for (String formatText : messages) {
			String colorizedText = TextUtils.colorize(formatText);
			Text text = Text.parse(formatText);
			assertEquals(
					"Format text does not match parsing input for: " + formatText,
					formatText, text.toFormat()
			);

			if (text.isPlainText()) {
				assertEquals(
						"Plain text does not match parsing input for: " + formatText,
						colorizedText, text.toPlainText()
				);
			}
		}
	}
}
