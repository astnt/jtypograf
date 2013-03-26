package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.model.Word;
import ru.artlebedev.typograf.rule.Rule;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 28.05.2009
 * Time: 12:19:03
 */
public class ModeRule extends Rule implements CharRule {
  private static Logger logger = Logger.getLogger("ru.artlebedev.typograf");
  private static final Word W_TITLE = new Word("title=");
  private static final Word W_ALT = new Word("alt=");
  private static final Word W_DATA = new Word("data-data=");
  private boolean attributeWasEmpty;

  public void process() {
    if (
           (p.c == '<' && !p.isInScript && !p.isInAttributeIgnore)
        || (p.c == '<' && p.isInScript && p.nextChar == '/' && !p.isInAttributeIgnore) // ФИКС: <script> if(a<script){ fuck; }</script> test - test
       )
    {
      if (p.isInText) {
        p.isInText = false;
      }
      p.isInTag = true;
    } else if (p.c == '>') {
      p.isInTag = false;
      p.isInText = true;
    } else if (p.isInTag && p.c == '"' && p.hasNextChar && p.nextChar == '"') { // skip empty attributes: title=""
      p.charIndex += 2;
      p.isInAttribute = false;
      p.updateChar();
      attributeWasEmpty = true;
      process();
    } else if (p.isInAttribute && p.c == '"') { // TODO
      p.isInTag = true;
      p.isInText = false;
      p.isInAttribute = false;
      p.updateChar();
    } else if (p.isInAttributeIgnore && p.c == '"') { // TODO
      p.isInTag = true;
      p.isInText = false;
      p.isInAttributeIgnore = false;
      p.updateChar();
    } else if (p.isInTag && p.word != null && (p.word.equals(W_TITLE) || p.word.equals(W_ALT) )) {
      if (attributeWasEmpty) {
        attributeWasEmpty = false;
        return;
      }
      p.isInTag = false;
      p.isInText = true;
      p.isInAttribute = true;
      p.charIndex += 1;
      if (p.source[p.charIndex] == '"') { p.charIndex += 1; }
      p.updateChar();
    } else if (p.isInTag && p.word != null && p.word.value.startsWith("data-") && p.word.value.endsWith("=")) {
      p.isInTag = false;
      p.isInText = true;
      p.isInAttributeIgnore = true;
      p.charIndex += 1;
    }
  }
}
