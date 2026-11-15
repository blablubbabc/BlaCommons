package de.blablubbabc.commons.config.bukkit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.blablubbabc.commons.config.value.types.StringListValue;
import de.blablubbabc.commons.config.value.types.StringValue;

/**
 * Shortcut for using {@link StringValue} or {@link StringListValue}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Uncolored {
}
