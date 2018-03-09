/**
 *
 */
package ru.samuylov.queryparser;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.samuylov.queryparser.parser.ParseException;
import ru.samuylov.queryparser.parser.SearchQueryParser;
import ru.samuylov.queryparser.parser.SubQuery;

/**
 * класс для тестовго запуска парсера
 *
 * @author samuylov
 *
 */
public class ParserApplication {
  private static final Logger log = LoggerFactory.getLogger(ParserApplication.class);

  public static void main(String[] args) throws IOException, ParseException {
    boolean exit = false;
    SearchQueryParser parser = new SearchQueryParser();

    while (!exit) {
      System.console().writer().println("Введите строку для разбора поискового запроса:");

      processLine(parser);

      System.console().writer().println();
      System.console().writer().println("Продолжить (Y - да, все остальное - закончить)?");
      String answer = System.console().readLine();
      exit = !"Y".equalsIgnoreCase(answer);
    }

  }

  /**
   * запрашивает и обрабатывает строку поискового запроса
   *
   * @param parser
   */
  private static void processLine(SearchQueryParser parser) {
    String sourceQuery = System.console().readLine();
    try {
      Collection<SubQuery> result = parser.parse(sourceQuery);
      if (result.isEmpty()) {
        System.console().writer().println("Пустой результат разбора");
      } else {
        for (SubQuery subQuery : result) {
          System.console().writer().println(subQuery);
        }
      }
    } catch (ParseException e) {
      System.console().writer().println("Ошибка разбора запроса: " + e.getMessage());
      log.error(e.getMessage(), e);
    }
  }

}
