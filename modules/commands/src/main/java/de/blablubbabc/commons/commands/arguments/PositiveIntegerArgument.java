package de.blablubbabc.commons.commands.arguments;

public class PositiveIntegerArgument extends BoundedIntegerArgument {

	public PositiveIntegerArgument(String name) {
		super(name, 1, Integer.MAX_VALUE); // Minimum value = 1
	}
}
