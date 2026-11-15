package de.blablubbabc.commons.config.value;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.bukkit.values.EntityTypeValue;
import de.blablubbabc.commons.config.bukkit.values.MaterialValue;
import de.blablubbabc.commons.config.bukkit.values.SoundEffectValue;
import de.blablubbabc.commons.config.bukkit.values.TextValue;
import de.blablubbabc.commons.config.value.types.BooleanValue;
import de.blablubbabc.commons.config.value.types.DoubleValue;
import de.blablubbabc.commons.config.value.types.EnumValue;
import de.blablubbabc.commons.config.value.types.FloatValue;
import de.blablubbabc.commons.config.value.types.IntegerValue;
import de.blablubbabc.commons.config.value.types.ListValue;
import de.blablubbabc.commons.config.value.types.LongValue;
import de.blablubbabc.commons.config.value.types.StringValue;
import de.blablubbabc.commons.config.value.types.TrileanValue;
import de.blablubbabc.commons.text.Text;
import de.blablubbabc.commons.util.bukkit.SoundEffect;
import de.blablubbabc.commons.util.java.Trilean;
import de.blablubbabc.commons.util.java.Unsafe;

/**
 * Registry of default value types of settings.
 */
public class DefaultValueTypes {

	private static final ValueTypeRegistry REGISTRY = new ValueTypeRegistry();

	static {
		REGISTRY.register(String.class, StringValue.INSTANCE);
		REGISTRY.register(Boolean.class, BooleanValue.INSTANCE);
		REGISTRY.register(boolean.class, BooleanValue.INSTANCE);
		REGISTRY.register(Integer.class, IntegerValue.INSTANCE);
		REGISTRY.register(int.class, IntegerValue.INSTANCE);
		REGISTRY.register(Double.class, DoubleValue.INSTANCE);
		REGISTRY.register(double.class, DoubleValue.INSTANCE);
		REGISTRY.register(Float.class, FloatValue.INSTANCE);
		REGISTRY.register(float.class, FloatValue.INSTANCE);
		REGISTRY.register(Long.class, LongValue.INSTANCE);
		REGISTRY.register(long.class, LongValue.INSTANCE);

		REGISTRY.register(Trilean.class, TrileanValue.INSTANCE);

		REGISTRY.register(Text.class, TextValue.INSTANCE);
		REGISTRY.register(Material.class, MaterialValue.INSTANCE);
		REGISTRY.register(SoundEffect.class, SoundEffectValue.INSTANCE);
		REGISTRY.register(EntityType.class, EntityTypeValue.INSTANCE);

		// The following more general value type providers are only used for types that didn't match
		// any of the above or other registered value types:

		REGISTRY.register(ValueTypeProviders.forTypePattern(
				TypePatterns.forBaseType(Enum.class),
				new Function<Type, ValueType<?>>() {
					@Override
					public ValueType<?> apply(@Nullable Type type) {
						assert type instanceof Class<?> && Enum.class.isAssignableFrom((Class<?>) type);
						Class<? extends Enum<?>> enumClass = Unsafe.castNonNull(type);
						return this.newEnumValueType(enumClass);
					}

					private <E extends Enum<E>> EnumValue<E> newEnumValueType(
							Class<? extends Enum<?>> enumClass
					) {
						return new EnumValue<>(Unsafe.cast(enumClass));
					}
				}
		));
		REGISTRY.register(ValueTypeProviders.forTypePattern(
				TypePatterns.forClass(List.class),
				(type) -> {
					assert type instanceof ParameterizedType;
					Type elementType = ((ParameterizedType) type).getActualTypeArguments()[0];
					ValueType<?> elementValueType = DefaultValueTypes.get(elementType);
					if (elementValueType == null) {
						throw new IllegalArgumentException("Unsupported element type: "
								+ elementType.getTypeName());
					}
					return new ListValue<>(elementValueType);
				}
		));
	}

	/**
	 * Registers a default {@link ValueType} for the given type.
	 * <p>
	 * This replaces any previously registered value type for the same type.
	 * 
	 * @param type
	 *            the type, not <code>null</code>
	 * @param valueType
	 *            the value type, not <code>null</code>
	 */
	public static void register(Type type, ValueType<?> valueType) {
		REGISTRY.register(type, valueType);
	}

	public static <T> @Nullable ValueType<T> get(Type type) {
		return REGISTRY.getValueType(type);
	}

	private DefaultValueTypes() {
	}
}
