package ru.artlebedev.typograf;

import ru.artlebedev.typograf.rule.chars.CharRule;
import ru.artlebedev.typograf.rule.Rule;
import ru.artlebedev.typograf.rule.word.WordRule;
import ru.artlebedev.typograf.model.Word;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 18.04.2009
 * Time: 14:09:53
 */
public class Processor {
  public char[] source;
  protected final transient Log log = LogFactory.getLog(getClass());

  public boolean isInText = false;
  public boolean isInTitle = false;
  public boolean isInTag = false;
  public boolean isInScript = false;
  public boolean isInStyle = false;
  public boolean isInNoTypograf = false;

  public char prevChar;
  public boolean hasPrevChar = false;
  public char c;
  public char nextChar;
  public boolean hasNextChar = false;

  public int charIndex = 0;

  private List<CharRule> charRules = new ArrayList<CharRule>();
  private List<WordRule> wordRules = new ArrayList<WordRule>();
  private Word currentWord;
  private Word prevWord;

  public void setCurrentWord(Word value) {
    //if (value.Length > 0) Console.WriteLine(String.Format("[{0}][mode {1}]", value.Value, ModeType));
    if (isInTag) {
      if (value.equals(scriptStart)) { isInScript = true; }
      if (value.equals(scriptEnd)) { isInScript = false; }

      if (value.equals(styleStart)) { isInStyle = true; }
      if (value.equals(styleEnd)) { isInStyle = false; }

      if (value.equals(titleStart)) { isInTitle = true; }
      if (value.equals(titleEnd)) { isInTitle = false; }

      if (value.equals(noTypografStart)) { isInNoTypograf = true; }
      if (value.equals(noTypografEnd)) { isInNoTypograf = false; }
    }
    if (wordRules.isEmpty()) {
      //Console.WriteLine("OnWordChange is empty");
    } else {
      if (!isInTag // Аттрибуты?
          && !isInScript
          && !isInStyle
          && !isInNoTypograf
          ) {
        if (!currentWord.equals(nbsp)) {
          prevWord = currentWord;
        }

        // TODO
        for (WordRule rule : wordRules) {
          rule.process();
          updateChar();
        }
      }
    }
    currentWord = value;
//    log.debug("currentWord'" + currentWord.value.toString() + "'=isInStyle-" + isInStyle + ";isInScript-" + isInScript);
    log.debug("currentWord'" + currentWord.value.toString() + "'\t\tisInScript-" + isInScript + "(" + value.equals(scriptEnd) + ")" + "tag:" + isInTag);
  }

  public char[] getSource() {
    return source;
  }

  public Processor(String source) {
    this.source = source.toCharArray();
  }

  public void addRule(CharRule charRule) {
    charRules.add(charRule);
    ((Rule) charRule).p = this;
  }

  public void addRule(WordRule wordRule) {
    wordRules.add(wordRule);
  }

  public boolean process() {
    for (int i = 0; i < source.length; i++) {
      charIndex = i;
      if (hasPrevChar = i - 1 > 0) {
        prevChar = source[i - 1];
      }
      c = source[i];
      if (hasNextChar = i + 1 < source.length - 1) {
        nextChar = source[i + 1];
      }
      for (CharRule charRule : charRules) {
        charRule.process();
      }
    }
    return true;
  }

  public void updateChar() {
    c = source[charIndex];
    // TODO вероятно нужно prev и next chars обновить
  }

  static final Word scriptStart = new Word("script");
  static final Word scriptEnd = new Word("/script");

  static final Word styleStart = new Word("style");
  static final Word styleEnd = new Word("/style");

  static final Word titleStart = new Word("title");
  static final Word titleEnd = new Word("/title");

  static final Word noTypografStart = new Word("notypograf");
  static final Word noTypografEnd = new Word("/notypograf");

  static final Word nbsp = new Word("nbsp");
}
