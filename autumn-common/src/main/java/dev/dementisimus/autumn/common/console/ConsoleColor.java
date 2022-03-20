/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common.console;

import lombok.AllArgsConstructor;
import org.fusesource.jansi.Ansi;

@AllArgsConstructor
public enum ConsoleColor {

    DEFAULT('r', Ansi.ansi().reset().fg(Ansi.Color.DEFAULT).boldOff().toString()),
    BLACK('0', Ansi.ansi().reset().fg(Ansi.Color.BLACK).boldOff().toString()),
    DARK_BLUE('1', Ansi.ansi().reset().fg(Ansi.Color.BLUE).boldOff().toString()),
    GREEN('2', Ansi.ansi().reset().fg(Ansi.Color.GREEN).boldOff().toString()),
    CYAN('3', Ansi.ansi().reset().fg(Ansi.Color.CYAN).boldOff().toString()),
    DARK_RED('4', Ansi.ansi().reset().fg(Ansi.Color.RED).boldOff().toString()),
    PURPLE('5', Ansi.ansi().reset().fg(Ansi.Color.MAGENTA).boldOff().toString()),
    ORANGE('6', Ansi.ansi().reset().fg(Ansi.Color.RED).fg(Ansi.Color.YELLOW).boldOff().toString()),
    GRAY('7', Ansi.ansi().reset().fg(Ansi.Color.WHITE).boldOff().toString()),
    DARK_GRAY('8', Ansi.ansi().reset().fg(Ansi.Color.BLACK).bold().toString()),
    BLUE('9', Ansi.ansi().reset().fg(Ansi.Color.BLUE).bold().toString()),
    LIGHT_GREEN('a', Ansi.ansi().reset().fg(Ansi.Color.GREEN).bold().toString()),
    AQUA('b', Ansi.ansi().reset().fg(Ansi.Color.CYAN).bold().toString()),
    RED('c', Ansi.ansi().reset().fg(Ansi.Color.RED).bold().toString()),
    PINK('d', Ansi.ansi().reset().fg(Ansi.Color.MAGENTA).bold().toString()),
    YELLOW('e', Ansi.ansi().reset().fg(Ansi.Color.YELLOW).bold().toString()),
    WHITE('f', Ansi.ansi().reset().fg(Ansi.Color.WHITE).bold().toString());

    private final char index;
    private final String ansiCode;

    public static String toColoredString(char triggerChar, String text) {
        for(ConsoleColor consoleColor : values()) {
            text = text.replace(triggerChar + "" + consoleColor.index, consoleColor.ansiCode);
        }
        return text + DEFAULT.ansiCode;
    }
}
