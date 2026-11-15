package de.blablubbabc.commons.ui.confirmations;

import java.util.Arrays;
import java.util.List;

import de.blablubbabc.commons.lang.MessagesBase;
import de.blablubbabc.commons.text.Text;

/**
 * The messages used by the confirmation UI.
 * <p>
 * Host plugins can assign these fields (e.g. when loading their own language files) to customize
 * the messages.
 */
public final class ConfirmationMessages extends MessagesBase {

	public static String confirmationUiConfirm = c("&2Confirm");
	public static String confirmationUiCancel = c("&4Cancel");
	public static List<String> confirmationUiCancelLore = c(Arrays.asList(
			"This will abort the",
			"current action."
	));
	public static Text confirmationUiAborted = Text.parse("&7Confirmation aborted.");

	private ConfirmationMessages() {
	}
}
