package de.blablubbabc.commons.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.blablubbabc.commons.config.value.DefaultValueTypes;
import de.blablubbabc.commons.config.value.ValueType;

/**
 * Specifies the default {@link ValueType} to use for config fields of a specific type.
 * <p>
 * This overrides the defaults specified by {@link DefaultValueTypes}.
 * <p>
 * The specified value type has to provide a no-args constructor.
 * <p>
 * Default value types specified by this annotation are inherited to subclasses and can be
 * overridden there by other annotations for the same field type. If multiple default value types
 * are specified for the same field type, the first one is used.
 */
@Target(ElementType.TYPE)
@Repeatable(WithDefaultValueTypes.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithDefaultValueType {

	Class<?> fieldType();

	Class<? extends ValueType<?>> valueType();
}
