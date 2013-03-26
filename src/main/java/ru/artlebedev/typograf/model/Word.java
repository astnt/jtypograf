package ru.artlebedev.typograf.model;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 28.05.2009
 * Time: 12:29:33
 */
public class Word {
  public final String value;
  public int hyphenCount;
  public boolean hasDigit;

  public Word() {
    value = "";
  }

  public Word(StringBuilder word) {
    this(word.toString());
  }

  public Word(String val) {
    value = val;
    for (int i = 0; i < val.length(); i++) {
      char c = val.charAt(i);
      if (c == '-') {
        hyphenCount += 1;
      }
      if (Character.isDigit(c)) {
        hasDigit = true;
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof Word)) { return false; }
    Word word = (Word) o;
    return value.equals(word.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  public char getLastChar() {
    int lastIndex = value.length() - 1;
    if (lastIndex < 0) {
      lastIndex = 0;
    }
    return value.charAt(lastIndex);
  }

  public char charAt(int position) {
    return value.charAt(position);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
