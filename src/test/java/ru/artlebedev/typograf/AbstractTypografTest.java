package ru.artlebedev.typograf;

import ru.artlebedev.typograf.rule.chars.DashRule;
import ru.artlebedev.typograf.rule.chars.ModeRule;
import ru.artlebedev.typograf.rule.chars.ParseWordRule;
import ru.artlebedev.typograf.rule.chars.QuoteRule;
import ru.artlebedev.typograf.rule.word.HyphenWordRule;
import ru.artlebedev.typograf.rule.word.MeasureRule;
import ru.artlebedev.typograf.rule.word.ShortWordRule;

import junit.framework.TestCase;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 13.07.2009
 * Time: 10:08:27
 */
public abstract class AbstractTypografTest extends TestCase {
  protected static Logger logger = Logger.getLogger("ru.artlebedev.typograf");

  protected Typograf createProcessor(String source) {
    Typograf p = new Typograf(source);
    p.addRule(new ParseWordRule());
    p.addRule(new ModeRule());
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());

    p.addRule(new ShortWordRule());
    p.addRule(new HyphenWordRule());
    p.addRule(new MeasureRule());
    return p;
  }

  protected void assertWith(String expected, char[] actual) {
      assertEquals(expected, String.valueOf(actual));
  }
}
