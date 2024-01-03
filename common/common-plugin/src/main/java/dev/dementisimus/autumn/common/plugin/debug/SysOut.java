/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.plugin.debug;

public class SysOut {

    public static void debug(Object object) {
        System.out.println(object);
    }

    public static void debug(String key, Object object) {
        System.out.println(key + ": " + object);
    }
}
