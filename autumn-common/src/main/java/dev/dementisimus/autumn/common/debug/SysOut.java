package dev.dementisimus.autumn.common.debug;

/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class SysOut @ AutumnCommon
 *
 * @author dementisimus
 * @since 24.11.2021:20:48
 */
public class SysOut {

    public static void debug(Object object) {
        System.out.println(object);
    }

    public static void debug(String key, Object object) {
        System.out.println(key + ": " + object);
    }
}
