package de.blablubbabc.commons.ui;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.util.java.Validate;

// TODO Handle enabled state and permission of UI types generally? Currently, most UIs handle
// permission checks as part of their canAccess logic. And the enabled state is not used at all.
// However, UIs may want to use specific feedback messages or more sophisticated logic when checking
// if a player can open a certain UI. Remove the unused enabled state and permission from UI types?
// Or document them as unused? Or provide default behavior, but let each UI handle it more
// specifically if they want?
public abstract class AbstractUIType implements UIType {

	private final String identifier;

	protected AbstractUIType(String identifier) {
		Validate.notEmpty(identifier, "identifier is null or empty");
		this.identifier = identifier;
	}

	@Override
	public final String getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append(" [identifier=");
		builder.append(identifier);
		builder.append("]");
		return builder.toString();
	}

	// hashCode and equals: Instances are compared by identity.

	@Override
	public final int hashCode() {
		return super.hashCode();
	}

	@Override
	public final boolean equals(@Nullable Object obj) {
		return super.equals(obj);
	}
}
