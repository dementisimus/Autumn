package dev.dementisimus.autumn.common.api.configuration;

import com.github.derrop.documents.Document;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;

import java.util.Properties;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class AutumnConfiguration @ AutumnCommon
 *
 * @author dementisimus
 * @since 24.11.2021:18:58
 */
public interface AutumnConfiguration {

    AutumnConfiguration enableYaml();

    AutumnConfiguration set(String key, Object object);

    AutumnConfiguration set(String key, Number number);

    AutumnConfiguration set(String key, Boolean bool);

    AutumnConfiguration set(String key, String string);

    AutumnConfiguration set(String key, Character character);

    AutumnConfiguration set(String key, Properties properties);

    AutumnConfiguration set(String key, byte[] bytes);

    void write();

    Document read();

    void read(AutumnCallback<Document> callback);
}
