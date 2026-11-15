package de.blablubbabc.commons.config.bukkit.values;

import java.lang.reflect.Type;
import java.util.List;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.value.TypePattern;
import de.blablubbabc.commons.config.value.TypePatterns;
import de.blablubbabc.commons.config.value.ValueType;
import de.blablubbabc.commons.config.value.ValueTypeProvider;
import de.blablubbabc.commons.config.value.ValueTypeProviders;
import de.blablubbabc.commons.config.value.types.ListValue;

public class ColoredStringListValue extends ListValue<String> {

	public static final ColoredStringListValue INSTANCE = new ColoredStringListValue();
	public static final TypePattern TYPE_PATTERN = TypePatterns.parameterized(List.class, String.class);
	public static final ValueTypeProvider PROVIDER = ValueTypeProviders.forTypePattern(TYPE_PATTERN, type -> INSTANCE);

	public static final class Provider implements ValueTypeProvider {
		@Override
		public @Nullable ValueType<?> get(Type type) {
			return PROVIDER.get(type);
		}
	}

	public ColoredStringListValue() {
		super(ColoredStringValue.INSTANCE);
	}
}
