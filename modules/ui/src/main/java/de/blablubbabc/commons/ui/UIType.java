package de.blablubbabc.commons.ui;

/**
 * A type of user interface that can be requested for a player.
 */
public interface UIType {

	/**
	 * Gets the unique identifier of this UI type.
	 * 
	 * @return the identifier, not <code>null</code> or empty
	 */
	public String getIdentifier();
}
