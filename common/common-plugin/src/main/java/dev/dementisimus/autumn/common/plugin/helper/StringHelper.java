/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.plugin.helper;

public class StringHelper {

    public static boolean equalsTo(String string, String... strings) {
        for (String equalsToString : strings) {
            if (string.equalsIgnoreCase(equalsToString)) return true;
        }
        return false;
    }
}
