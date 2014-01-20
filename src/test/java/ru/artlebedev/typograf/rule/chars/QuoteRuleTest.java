package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.AbstractTypografTest;
import ru.artlebedev.typograf.Typograf;
import ru.artlebedev.typograf.info.MainInfo;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 13.07.2009
 * Time: 10:07:30
 */
public class QuoteRuleTest extends AbstractTypografTest {
  public void test1Href() {
    final Typograf p = createProcessor("<p>«Всероссийский экологический субботник «Зелёная Россия»</p>" +
        "<p>Всероссийский экологический субботник «Зелёная Россия»</p>");

    if (p.process()) {
      final String result = String.valueOf(p.getSource());
      logger.info(result);
      assertEquals("<p>«Всероссийский экологический субботник „Зелёная Россия“</p>" +
          "<p>Всероссийский экологический субботник «Зелёная Россия»</p>" , result);
    }
  }

  public void test2Href() {
    final Typograf p = createProcessor("<p class=\"media_link\">\"<a href=\"/press/chief-journal/2013/18/\">" +
        "Рад приветствовать Вас со страниц интернет-сайта ООО \"Газпром трансгаз Нижний Новгород\"</a>\"</p>");

    if (p.process()) {
      final String result = String.valueOf(p.getSource());
      logger.info(result);
      assertEquals("<p class=\"media_link\">" +
          "«<a href=\"/press/chief-journal/2013/18/\">Рад приветствовать " +
          "Вас со страниц интернет-сайта ООО\u00a0„Газпром трансгаз Нижний Новгород“</a>»</p>" , result);
    }
  }

  public void test3level() {
    final Typograf p = createProcessor("<p>\"Комплекс стандартов \"Документы нормативные для проектирования, строительства и эксплуатации объектов ОАО \"Газпром\"\"\"</p>");
    if (p.process()) {
      final String result = String.valueOf(p.getSource());
      logger.info(result);
      assertEquals("<p>«Комплекс стандартов „Документы нормативные для проектирования, строительства и эксплуатации объектов ОАО\u00A0„Газпром““»</p>" , result);
    }
  }

  public void testQuotes3() {
    final Typograf p = createProcessor("<p>\"\"Газпром\" предлагает\" полностью</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>«„Газпром“ предлагает» полностью</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotesHrefOuter() {
    final Typograf p = createProcessor("<p>Проект \"<a href=\"#postId=13275\">Сахалин-2</a>\" начался.</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>Проект «<a href=\"#postId=13275\">Сахалин-2</a>» начался.</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotesHrefOuterWithNoBreakSpaceReal() { // c2 a0 - NO-BREAK SPACE - after </a>"
    Typograf p = createProcessor("<p>Проект \"<a href=\"/social/children/\">Газпром - детям</a>\" начался</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>Проект «<a href=\"/social/children/\">Газпром\u00A0— детям</a>» начался</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotesHrefOuterWithNoBreakSpace() {
    final Typograf p = createProcessor("Совместно с ОАО \"<a href=\"\">СИБУР Холдинг</a>\" компания");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("Совместно с ОАО\u00A0«<a href=\"\">СИБУР Холдинг</a>» компания", String.valueOf(p.getSource()));
    }
  }
  
  public void testQuotes4() {
    final Typograf p = createProcessor("<p>Маштоца (Республика Армения); орденом \"Достык\" (\"Дружбы\") II степени</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>Маштоца (Республика Армения); орденом «Достык» («Дружбы») II степени</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotes5() {
    final Typograf p = createProcessor("<p>\"\"Газпром\" свою стратегию. В слова \"вылетает в трубу\"! Таким образом\".</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>«„Газпром“ свою стратегию. В слова „вылетает в трубу“! Таким образом».</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotes6() {
    final Typograf p = createProcessor("<p>Cвою стратегию. В слова \"<a href=\"/test/\">вылетает</a>\";<b>в трубу</b>!.</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>Cвою стратегию. В слова «<a href=\"/test/\">вылетает</a>»;<b>в трубу</b>!.</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotesEndWithDot() {
    final Typograf p = createProcessor("<p>ООО \"Кубаньгазпром\" активным коллективов \"<a href=\"/path/\">Факел</a>\".</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>ООО\u00A0«Кубаньгазпром» активным коллективов «<a href=\"/path/\">Факел</a>».</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotes() throws IOException {
    Typograf p = new Typograf("test is \"test\" test");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      assertWith("test is «test» test", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotesSecondLevel() throws IOException {
    Typograf p = new Typograf("test is \"test in \"somewhere\"\" test");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      assertWith("test is «test in „somewhere“» test", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotesWithThreeDots() throws IOException {
    Typograf p = new Typograf("three dots test is \"...test in \"somewhere\" text\" test");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      assertWith("three dots test is «...test in „somewhere“ text» test", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotesWithThreeDots2() throws IOException {
    Typograf p = new Typograf("Министр ее 55-летия, писал: \"...горячо звания \"Почетный работник газовой промышленности\" - достойной в отрасли.\"");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      assertWith("Министр ее 55-летия, писал: «...горячо звания „Почетный работник газовой промышленности“ — достойной в отрасли.»", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotesForSingleLine() throws IOException {
    Typograf p = new Typograf("Протокол заседания Котировочной комиссии ОАО 'Газпром'");
    p.addRules();
    if (p.process()) {
      assertWith("Протокол заседания Котировочной комиссии ОАО\u00A0«Газпром»", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotesWithThreeDot() throws IOException {
    Typograf p = new Typograf("\"Сегодня в проекте \"<a>Южный поток</a>\"\".");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      logger.info(new String(p.getSource()));
      assertWith("«Сегодня в проекте „<a>Южный поток</a>“».", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotesWithTabSymbol() throws IOException {
    Typograf p = new Typograf("\n<p>" +
"\n	Участники встречи сошлись во мнении, что укрепление долгосрочного партнерства между \"Газпромом\" и GDF SUEZ будет способствовать повышению безопасности газоснабжения потребителей европейских государств.</p>" +
"<p> \n" +
        "<p> \n" +
"	\"Успехи в реализации новых инфраструктурных проектов, таких как \"Северный\" и \"Южный поток\"</p>");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      logger.info(new String(p.getSource()));
      assertWith("\n" +
          "<p>\n" +
          "\u0009Участники встречи сошлись во мнении, что укрепление долгосрочного партнерства между «Газпромом» и GDF SUEZ будет способствовать повышению безопасности газоснабжения потребителей европейских государств.</p><p> \n" +
          "<p> \n" +
          "\u0009«Успехи в реализации новых инфраструктурных проектов, таких как „Северный“ и „Южный поток“</p>", p.getSource());
    } else {
      fail();
    }
  }

  public void test2QuotesAndComma() {
    Typograf p = new Typograf("Компания \"Газпром нефть\" контролирует ОАО \"Московский НПЗ\" " +
        "(установленная мощность 12,15&nbsp;млн т&nbsp;в&nbsp;год) " +
        "и&nbsp;50% ОАО \"НГК \"Славнефть\"\", владеющей двумя нефтеперерабатывающими заводами");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      String result = new String(p.getSource());
      assertTrue(result.contains("«НГК „Славнефть“»"));
    } else {
      fail();
    }
  }

  public void test2QuotesAndComma2() {
//    String file = null;
//    try {
//      file = FileUtils.readFileToString(new File("/mnt/sda1/tmp/page.txt"), "utf8");
//    } catch (IOException e) {
//      logger.log(Level.SEVERE, "io", e);
//    }
    Typograf p = new Typograf("ОАО \"Салаватнефтеоргсинтез\"&nbsp;- тест \"следующей\" кавычки");
//    Typograf p = new Typograf(file);
    p.addRules();
    if (p.process()) {
      String result = new String(p.getSource());
      logger.info(result);
      assertWith("ОАО\u00a0«Салаватнефтеоргсинтез»&nbsp;— тест «следующей» кавычки",
          p.getSource());
    } else {
      fail();
    }
  }

  public void test2QuotesAndComma3() {
    Typograf p = new Typograf("текст «НГК „Славнефть“» текст");
    p.addRules();
    if (p.process()) {
      String result = new String(p.getSource());
      logger.info(result);
      assertWith("текст «НГК\u00a0„Славнефть“» текст",
          p.getSource());
    } else {
      fail();
    }
  }

  public void testImgAlt() {
    Typograf p = new Typograf("<img src=\"/preview/f/posts/34/840639/w350_gazprominfo-anon-01.jpg\" width=\"350\" height=\"448\" " +
        "alt=\"'Газпром' открыл новый сайт - 'Информаторий'\"/>");
    p.addRules();
    if (p.process()) {
      String result = new String(p.getSource());
      logger.info(result);
      assertWith("<img src=\"/preview/f/posts/34/840639/w350_gazprominfo-anon-01.jpg\" width=\"350\" height=\"448\" alt=\"«Газпром» открыл новый сайт — «Информаторий»\"/>",
          p.getSource());
    } else {
      fail();
    }
  }

  public void testImgAlt2() {
    Typograf p = new Typograf("test 'Газпром' открыл новый сайт - 'Информаторий' ");
    p.addRules();
    if (p.process()) {
      String result = new String(p.getSource());
      logger.info(result);
      assertWith("test «Газпром» открыл новый сайт — «Информаторий» ",
          p.getSource());
    } else {
      fail();
    }
  }

  public void testDeQuotes() {
    Typograf p = new Typograf("Doktrin der \"einheitlichen sozialistischen Staatsmacht\" galt");
    p.addRules();
    p.style = MainInfo.Lang.DE;
    if (p.process()) {
      String result = new String(p.getSource());
      logger.info(result);
      assertWith("Doktrin der „einheitlichen sozialistischen Staatsmacht“ galt", p.getSource());
    } else {
      fail();
    }
  }

  public void testApostropheInWord() {
    Typograf p = new Typograf("test \"Хартия'97\" test");
    p.addRules();
    if (p.process()) {
      String result = new String(p.getSource());
      logger.info(result);
      assertWith("test «Хартия'97» test",
          p.getSource());
    } else {
      fail();
    }
  }

  public void testApostropheInWord2() {
    Typograf p = new Typograf("test 'Хартия'</p><p>test 'test' test");
    p.addRules();
    if (p.process()) {
      String result = new String(p.getSource());
      logger.info(result);
      assertWith("test «Хартия»</p><p>test «test» test",
          p.getSource());
    } else {
      fail();
    }
  }

  public void testTitleBug() {
    Typograf p = new Typograf("<p>test \"test\" test</p><img src=\"/f/posts/13/673660/strategy_ru.jpg\" alt=\"\" width=\"700\" height=\"333\" title=\"\"/></div> \n" +
        "<p> \n" +
        "\tTest \"test\" test.</p>");
    p.addRules();
    if (p.process()) {
      String result = new String(p.getSource());
      logger.info(result);
      assertWith("<p>test «test» test</p><img src=\"/f/posts/13/673660/strategy_ru.jpg\" alt=\"\" width=\"700\" height=\"333\" title=\"\"/></div> \n" +
          "<p> \n" +
          "\tTest «test» test.</p>",
          p.getSource());
    } else {
      fail();
    }
  }

  public void testXmlns() {
    Typograf p = new Typograf("test \"test\" <noindex xmlns=\"\">\"Сахалин-2\"</noindex> test \"test\" test");
    p.addRules();
    if (p.process()) {
      String result = new String(p.getSource());
      logger.info(result);
      assertWith("test «test» <noindex xmlns=\"\">«Сахалин-2»</noindex> test «test» test",
          p.getSource());
    } else {
      fail();
    }
  }
}
