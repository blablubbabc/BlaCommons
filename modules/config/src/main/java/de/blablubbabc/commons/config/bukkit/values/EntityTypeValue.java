package de.blablubbabc.commons.config.bukkit.values;

import org.bukkit.entity.EntityType;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;

public class EntityTypeValue extends MinecraftEnumValue<EntityType> {

	public static final EntityTypeValue INSTANCE = new EntityTypeValue();

	public EntityTypeValue() {
		super(EntityType.class);
	}

	@Override
	protected ValueLoadException newInvalidEnumValueException(
			String valueName,
			ValueParseException parseException
	) {
		return new InvalidEntityTypeException(parseException.getMessage(), parseException);
	}
}
