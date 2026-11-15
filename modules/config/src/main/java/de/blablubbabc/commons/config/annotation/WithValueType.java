package de.blablubbabc.commons.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.blablubbabc.commons.config.value.ValueType;

/**
 * Specifies the {@link ValueType} to use for a specific config field.
 * <p>
 * The specified value type has to provide a no-args constructor.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithValueType {

	Class<? extends ValueType<?>> value();
}
