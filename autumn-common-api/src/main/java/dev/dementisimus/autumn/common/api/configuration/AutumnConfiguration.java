/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.api.configuration;

import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
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
     * @param key key used in file
     * @param object Object representing the key
     *
     * @return configuration object
     *
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Object object);

    /**
     * Sets {@link Document}
     *
     * @param document {@link Document} with all keys and values
     *
     * @return configuration object
     *
     * @since 1.1.0
     */
    @NotNull AutumnConfiguration set(@NotNull Document document);

    /**
     * Sets key and its Number value
     *
     * @param key key used in file
     * @param number Number representing the key
     *
     * @return configuration object
     *
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Number number);

    /**
     * Sets key and its Boolean value
     *
     * @param key key used in file
     * @param bool Boolean representing the key
     *
     * @return configuration object
     *
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Boolean bool);

    /**
     * Sets key and its String value
     *
     * @param key key used in file
     * @param string String representing the key
     *
     * @return configuration object
     *
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(@NotNull String key, @NotNull String string);

    /**
     * Sets key and its Character value
     *
     * @param key key used in file
     * @param character Character representing the key
     *
     * @return configuration object
     *
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Character character);

    /**
     * Sets key and its Properties value
     *
     * @param key key used in file
     * @param properties Properties representing the key
     *
     * @return configuration object
     *
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(@NotNull String key, @NotNull Properties properties);

    /**
     * Sets key and its ByteArray value
     *
     * @param key key used in file
     * @param bytes ByteArray representing the key
     *
     * @return configuration object
     *
     * @since 1.0.0
     */
    @NotNull AutumnConfiguration set(@NotNull String key, byte[] bytes);

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
     *
     * @since 1.0.0
     */
    @NotNull Document read();

    /**
     * Reads the file
     *
     * @param callback Callback used to deliver the {@link Document} containing all keys and values
     *
     * @since 1.0.0
     */
    void read(@NotNull AutumnCallback<@NotNull Document> callback);
}
