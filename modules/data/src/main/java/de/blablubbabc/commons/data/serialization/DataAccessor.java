package de.blablubbabc.commons.data.serialization;

import de.blablubbabc.commons.data.container.DataContainer;

/**
 * Combines {@link DataSaver} and {@link DataLoader} to provide operations to save and load a
 * particular kind of value to and from a given {@link DataContainer}.
 *
 * @param <T>
 *            the type of the value that is being saved or loaded
 */
public interface DataAccessor<T> extends DataSaver<T>, DataLoader<T> {
}
