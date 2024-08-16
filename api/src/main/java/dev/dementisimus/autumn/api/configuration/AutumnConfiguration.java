/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.api.configuration;

import dev.dementisimus.autumn.api.callback.SingleCallback;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

/**
 * Used for JSON file storage
 *
 * @since 1.0.0
 */
public interface AutumnConfiguration {

    /**
     * Sets key and its Object value
     *
     * @param key    key used in file
     * @param object Object representing the key
     * @return configuration object
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(String key, Object object);

    /**
     * Sets {@link Document}
     *
     * @param document {@link Document} with all keys and values
     * @return configuration object
     * @since 1.1.0
     */
    @NotNull AutumnConfiguration set(Document document);

    /**
     * Sets key and its Number value
     *
     * @param key    key used in file
     * @param number Number representing the key
     * @return configuration object
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(String key, Number number);

    /**
     * Sets key and its Boolean value
     *
     * @param key  key used in file
     * @param bool Boolean representing the key
     * @return configuration object
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(String key, Boolean bool);

    /**
     * Sets key and its String value
     *
     * @param key    key used in file
     * @param string String representing the key
     * @return configuration object
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(String key, String string);

    /**
     * Sets key and its Character value
     *
     * @param key       key used in file
     * @param character Character representing the key
     * @return configuration object
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(String key, Character character);

    /**
     * Sets key and its Properties value
     *
     * @param key        key used in file
     * @param properties Properties representing the key
     * @return configuration object
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(String key, Properties properties);

    /**
     * Sets key and its ByteArray value
     *
     * @param key   key used in file
     * @param bytes ByteArray representing the key
     * @return configuration object
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(String key, byte[] bytes);

    /**
     * Saves all previously set keys and values to the storage file
     *
     * @since 1.0.0
     */
    void write();

    /**
     * Reads the file
     *
     * @return {@link Document} containing all keys and values
     * @since 1.0.0
     */
    @NotNull Document read();

    /**
     * Reads the file
     *
     * @param callback Callback used to deliver the {@link Document} containing all keys and values
     * @since 1.0.0
     */
    void read(SingleCallback<@NotNull Document> callback);
}
