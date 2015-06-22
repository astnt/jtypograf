## English sample ##

### Before ###

Employees' willingness and ability to "work effectively, "improve" their skills" and - extend knowledge are a guarantee of success.

### After ###

Employees' willingness and ability to “work effectively, ‘improve’ their skills” and – extend knowledge are a guarantee of success.

## Russian sample ##

### Before ###

Тест - типографики.
Проверка "этих может "быть" кавычек" - текст.

стр. 456-466 по тел. (456) 646-55-45


### After ###

Тест — типографики.
Проверка «этих может „быть” кавычек» — текст.

стр. 456–466 по тел. (456) 646-55-45

## Install ##

Checkout and install project:
```
svn checkout http://jtypograf.googlecode.com/svn/trunk/ jtypograf-read-only
cd jtypograf-read-only
mvn install
```

Then you need to add maven dependency:
```
    <dependency>
      <groupId>ru.artlebedev.jtypograf</groupId>
      <artifactId>jtypograf</artifactId>
      <version>1.0.3</version>
    </dependency>
```

## Performance ##
```
INFO - 14:52:10,544 - typograf time: 0.0099 seconds:  page /production/processing/, length 25652, p.style = RU, lang ru
INFO - 14:52:13,670 - typograf time: 0.0085 seconds:  page /production/processing/, length 25652, p.style = RU, lang ru
INFO - 14:52:29,240 - typograf time: 0.0036 seconds:  page /management/, length 10772, p.style = RU, lang ru
INFO - 14:52:31,922 - typograf time: 0.0044 seconds:  page /management/directors/, length 12625, p.style = RU, lang ru
INFO - 14:52:41,232 - typograf time: 0.0051 seconds:  page /subsidiaries/news/, length 14567, p.style = RU, lang ru
INFO - 14:52:53,304 - typograf time: 0.0059 seconds:  page /about/, length 9396, p.style = RU, lang ru
INFO - 14:52:55,920 - typograf time: 0.0036 seconds:  page /about/history/, length 9433, p.style = RU, lang ru
INFO - 14:52:57,855 - typograf time: 0.0003 seconds:  page /about/history/chronicle/, length 401, p.style = RU, lang ru
INFO - 14:52:58,551 - typograf time: 0.0193 seconds:  page /about/history/chronicle/2008/, length 63754, p.style = RU, lang ru
INFO - 14:53:10,777 - typograf time: 0.0081 seconds:  page /production/processing/, length 25025, p.style = RU, lang ru
INFO - 14:53:12,229 - typograf time: 0.0098 seconds:  page /production/processing/, length 25652, p.style = RU, lang ru
INFO - 14:53:13,487 - typograf time: 0.0081 seconds:  page /production/processing/, length 25025, p.style = RU, lang ru
INFO - 14:53:15,318 - typograf time: 0.0084 seconds:  page /production/processing/, length 25652, p.style = RU, lang ru
INFO - 14:53:17,156 - typograf time: 0.0084 seconds:  page /production/processing/, length 25652, p.style = RU, lang ru
INFO - 14:53:17,809 - typograf time: 0.0221 seconds:  page /about/history/chronicle/2008/, length 63754, p.style = RU, lang ru
INFO - 14:53:22,849 - typograf time: 0.0036 seconds:  page /about/history/, length 9433, p.style = RU, lang ru
INFO - 14:53:26,704 - typograf time: 0.0003 seconds:  page /about/history/chronicle/, length 401, p.style = RU, lang ru
```

### Sites using library ###
  * http://www.gazprom.ru/
  * http://www.ridus.ru/