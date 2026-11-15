package de.blablubbabc.commons.ui;

/**
 * An empty {@link UIState}.
 */
public final class EmptyUIState implements UIState {

	/**
	 * Shared singleton instance.
	 */
	public static final UIState INSTANCE = new EmptyUIState();

	private EmptyUIState() {
	}
}
