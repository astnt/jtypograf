package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.AbstractTypografTest;
import ru.artlebedev.typograf.Typograf;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 26.11.2010
 * Time: 11:11:44
 */
public class DashRuleTest extends AbstractTypografTest {
  public void testDashWithTabBeforeIt() throws IOException {
    Typograf p = new Typograf("<p></p>\t- Я думаю, что.");
    p.addRule(new ModeRule());
    p.addRule(new DashRule());
    if (p.process()) {
      logger.info(new String(p.getSource()));
      assertWith("<p></p>\t— Я думаю, что.", p.getSource());
    } else {
      fail();
    }
  }
}
