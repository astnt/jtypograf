#

Code sample:

```
    Typograf typograf = new Typograf(inputHtml);
    typograf.addRule(new ParseWordRule());
    typograf.addRule(new ModeRule());
    typograf.addRule(new DashRule());
    typograf.addRule(new QuoteRule());

    typograf.addRule(new ShortWordRule());
    typograf.addRule(new HyphenWordRule());
    typograf.addRule(new MeasureRule());

    typograf.style = MainInfo.Lang.RU;

    if (typograf.process()) {
      String outputHtml = String.valueOf(typograf.getSource()));
    }
```