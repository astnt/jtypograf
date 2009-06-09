package ru.artlebedev.typograf.util;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 27.05.2009
 * Time: 16:37:28
 */
public class CommonUtil {
  private static String LAT_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static boolean isInLatLetter(char c) {
    return LAT_LETTERS.indexOf(c) >= 0;
  }
}
