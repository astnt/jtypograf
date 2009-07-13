package ru.artlebedev.typograf.util;

import ru.artlebedev.typograf.info.CharsInfo;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 27.05.2009
 * Time: 16:37:28
 */
public class Util {
  private static String LAT_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static boolean isInLatLetter(char c) {
    return LAT_LETTERS.indexOf(c) >= 0;
  }

  public static boolean hasChar(char[] source, int i) {
    return source.length > i;
  }

  public static boolean wordEnd(char c) {
    return c == '<' || c == CharsInfo.space || c == CharsInfo.noBreakSpace || c == ')';
  }
}
