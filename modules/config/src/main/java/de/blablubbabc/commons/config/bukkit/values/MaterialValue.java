package de.blablubbabc.commons.config.bukkit.values;

import org.bukkit.Material;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;
import de.blablubbabc.commons.util.inventory.ItemUtils;
import de.blablubbabc.commons.util.java.Validate;

public class MaterialValue extends MinecraftEnumValue<Material> {

	public static final MaterialValue INSTANCE = new MaterialValue();

	public MaterialValue() {
		super(Material.class);
	}

	@Override
	protected ValueLoadException newInvalidEnumValueException(
			String valueName,
			ValueParseException parseException
	) {
		return new InvalidMaterialException(parseException.getMessage(), parseException);
	}

	@Override
	public Material parse(String input) throws ValueParseException {
		Validate.notNull(input, "input is null");
		Material material = ItemUtils.parseMaterial(input);
		if (material == null) {
			throw new ValueParseException("Unknown Material: " + input);
		}
		// Filter legacy materials:
		if (material.isLegacy()) {
			throw new ValueParseException("Unsupported legacy Material: " + input);
		}
		return material;
	}
}
