package de.blablubbabc.commons.data.serialization.bukkit;

import org.bukkit.NamespacedKey;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.data.serialization.DataSerializer;
import de.blablubbabc.commons.data.serialization.InvalidDataException;
import de.blablubbabc.commons.data.serialization.java.StringSerializers;
import de.blablubbabc.commons.util.bukkit.NamespacedKeyUtils;
import de.blablubbabc.commons.util.java.Validate;

/**
 * Default {@link DataSerializer}s for {@link NamespacedKey} values.
 */
public final class NamespacedKeySerializers {

	private static abstract class NamespacedKeySerializer implements DataSerializer<NamespacedKey> {

		@Override
		public @Nullable Object serialize(NamespacedKey value) {
			Validate.notNull(value, "value is null");
			return value.toString();
		}
	}

	/**
	 * A {@link DataSerializer} for {@link NamespacedKey} values.
	 */
	public static final DataSerializer<NamespacedKey> DEFAULT = new NamespacedKeySerializer() {
		@Override
		public NamespacedKey deserialize(Object data) throws InvalidDataException {
			String keyString = StringSerializers.STRICT.deserialize(data);
			// NamespacedKeyUtils.parse instead of NamespacedKey.fromString to also accept old
			// uppercase enum names and interpret them as namespaced keys.
			@Nullable NamespacedKey key = NamespacedKeyUtils.parse(keyString);
			if (key == null) {
				throw new InvalidDataException("Invalid namespaced key: '" + keyString + "'");
			}
			return key;
		}
	};

	private NamespacedKeySerializers() {
	}
}
