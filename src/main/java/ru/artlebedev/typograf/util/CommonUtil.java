package ru.artlebedev.typograf.util;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 27.05.2009
 * Time: 16:37:28
 */
public class CommonUtil {

  // TODO тоже заменить строкой
  private static final char[] INITIALS = new char[]
      { 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', };
  private static String LAT_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static String DIGITS = "1234567890";

  public static boolean isDigitChar(char c)
  {
    return DIGITS.contains(String.valueOf(c));
  }

  public static boolean isUpper(char c)
  {
    for (char initial : INITIALS) {
      if (initial == c) { return true; }
    }
    return false;
  }


  public static boolean isInLatLetter(char c) {
    return LAT_LETTERS.contains(String.valueOf(c)); // UNDONE возможно замедление производительности
  }

}
