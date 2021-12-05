package dev.dementisimus.autumn.common.helper;

/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class NumberHelper @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:22:29
 */
public class NumberHelper {

    public static int toNumber(String string) {
        try {
            return Integer.parseInt(string);
        }catch(NumberFormatException exception) {
            return -1;
        }
    }
}
