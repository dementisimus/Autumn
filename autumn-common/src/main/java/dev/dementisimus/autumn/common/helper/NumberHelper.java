/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.helper;

public class NumberHelper {

    public static int toNumber(String string) {
        try {
            return Integer.parseInt(string);
        }catch(NumberFormatException exception) {
            return -1;
        }
    }
}
