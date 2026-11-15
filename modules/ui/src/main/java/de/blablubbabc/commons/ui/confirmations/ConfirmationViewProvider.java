package de.blablubbabc.commons.ui.confirmations;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.ui.SimpleViewContext;
import de.blablubbabc.commons.ui.UIState;
import de.blablubbabc.commons.ui.View;
import de.blablubbabc.commons.ui.ViewContext;
import de.blablubbabc.commons.ui.ViewProvider;

public class ConfirmationViewProvider extends ViewProvider {

	private static final ViewContext VIEW_CONTEXT = new SimpleViewContext("confirmation");

	ConfirmationViewProvider() {
		super(ConfirmationUIType.INSTANCE, VIEW_CONTEXT);
	}

	@Override
	public boolean canAccess(Player player, boolean silent) {
		// Players cannot directly request this UI themselves. It is always opened for them in some
		// context.
		return true;
	}

	@Override
	protected @Nullable View createView(Player player, UIState uiState) {
		return new ConfirmationView(this, player, uiState);
	}
}
