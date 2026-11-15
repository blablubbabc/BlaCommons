package de.blablubbabc.commons.ui.confirmations;

import de.blablubbabc.commons.ui.AbstractUIType;

public final class ConfirmationUIType extends AbstractUIType {

	public static final ConfirmationUIType INSTANCE = new ConfirmationUIType();

	private ConfirmationUIType() {
		super("confirmation");
	}
}
