package de.blablubbabc.commons.config.bukkit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.blablubbabc.commons.config.bukkit.values.ColoredStringListValue;
import de.blablubbabc.commons.config.bukkit.values.ColoredStringValue;

/**
 * Shortcut for using {@link ColoredStringValue} or {@link ColoredStringListValue}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Colored {
}
