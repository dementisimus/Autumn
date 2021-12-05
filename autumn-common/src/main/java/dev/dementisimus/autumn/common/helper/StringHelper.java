package dev.dementisimus.autumn.common.helper;

/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class StringHelper @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:19
 */
public class StringHelper {

    public static boolean equalsTo(String string, String... strings) {
        for(String equalsToString : strings) {
            if(string.equalsIgnoreCase(equalsToString)) return true;
        }
        return false;
    }
}
