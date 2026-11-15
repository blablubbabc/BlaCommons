package de.blablubbabc.commons.data.serialization.bukkit;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.compat.Compat;
import de.blablubbabc.commons.data.container.DataContainer;
import de.blablubbabc.commons.data.property.BasicProperty;
import de.blablubbabc.commons.data.property.Property;
import de.blablubbabc.commons.data.serialization.DataSerializer;
import de.blablubbabc.commons.data.serialization.InvalidDataException;
import de.blablubbabc.commons.data.serialization.java.DataContainerSerializers;
import de.blablubbabc.commons.data.serialization.java.EnumSerializers;
import de.blablubbabc.commons.data.serialization.java.NumberSerializers;
import de.blablubbabc.commons.data.serialization.java.StringSerializers;
import de.blablubbabc.commons.util.bukkit.NamespacedKeyUtils;
import de.blablubbabc.commons.util.bukkit.RegistryUtils;
import de.blablubbabc.commons.util.bukkit.SoundEffect;
import de.blablubbabc.commons.util.java.Validate;

/**
 * Default {@link DataSerializer}s for {@link SoundEffect} values.
 */
public final class SoundEffectSerializers {

	private static final Property<String> SOUND_NAME = new BasicProperty<String>()
			.dataKeyAccessor("sound", StringSerializers.SCALAR)
			.build();
	private static final Property<@Nullable SoundCategory> CATEGORY = new BasicProperty<@Nullable SoundCategory>()
			.dataKeyAccessor("category", EnumSerializers.lenient(SoundCategory.class))
			.nullable() // Optional
			.defaultValue(SoundEffect.DEFAULT_CATEGORY)
			.build();
	private static final Property<@Nullable Float> PITCH = new BasicProperty<@Nullable Float>()
			.dataKeyAccessor("pitch", NumberSerializers.FLOAT)
			.nullable() // Optional
			.defaultValue(SoundEffect.DEFAULT_PITCH)
			.build();
	private static final Property<@Nullable Float> VOLUME = new BasicProperty<@Nullable Float>()
			.dataKeyAccessor("volume", NumberSerializers.FLOAT)
			.nullable() // Optional
			.defaultValue(SoundEffect.DEFAULT_VOLUME)
			.build();

	/**
	 * A {@link DataSerializer} for {@link SoundEffect} values.
	 * <p>
	 * When deserializing a {@link SoundEffect}, we first try to map a given sound name to a
	 * server-known {@link Sound}. This means that if the sound effect was using a sound name
	 * instead of a {@link Sound} before being serialized, the deserialized sound effect may not be
	 * equal to the previously serialized sound effect.
	 */
	public static final DataSerializer<SoundEffect> DEFAULT = new DataSerializer<SoundEffect>() {
		@Override
		public @Nullable Object serialize(SoundEffect value) {
			Validate.notNull(value, "value is null");
			Sound sound = value.getSound();
			String soundName = value.getSoundName();
			SoundCategory category = value.getCategory();
			Float pitch = value.getPitch();
			Float volume = value.getVolume();

			// Sound and sound name are both serialized as String:
			String serializedSound = (sound != null) ? RegistryUtils.getKeyOrThrow(sound).toString()
					: soundName;

			// Use a compact representation if only the sound / sound name is specified:
			if (category == null && pitch == null && volume == null) {
				return serializedSound;
			}

			DataContainer soundEffectData = DataContainer.create();
			soundEffectData.set(SOUND_NAME, serializedSound);
			soundEffectData.set(CATEGORY, category);
			soundEffectData.set(PITCH, pitch);
			soundEffectData.set(VOLUME, volume);
			return soundEffectData.serialize();
		}

		@Override
		public SoundEffect deserialize(Object data) throws InvalidDataException {
			Validate.notNull(data, "data is null");
			String soundName = null;
			SoundCategory category = null;
			Float pitch = null;
			Float volume = null;

			if (data instanceof String) {
				// Compact representation:
				soundName = (String) data; // Can be empty
			} else {
				DataContainer soundEffectData = DataContainerSerializers.DEFAULT.deserialize(data);
				soundName = soundEffectData.get(SOUND_NAME); // Can be empty
				category = soundEffectData.get(CATEGORY);
				pitch = soundEffectData.get(PITCH);
				volume = soundEffectData.get(VOLUME);
			}

			// Check if the sound name matches a known Sound:
			var soundRegistry = Compat.getProvider().getRegistry(Sound.class);
			NamespacedKey soundKey = NamespacedKeyUtils.parse(soundName);
			Sound sound = soundKey != null ? soundRegistry.get(soundKey) : null;

			// We prefer the Sound over the sound name:
			SoundEffect soundEffect = (sound != null) ? new SoundEffect(sound)
					: new SoundEffect(soundName);
			return soundEffect.withCategory(category).withPitch(pitch).withVolume(volume);
		}
	};

	private SoundEffectSerializers() {
	}
}
