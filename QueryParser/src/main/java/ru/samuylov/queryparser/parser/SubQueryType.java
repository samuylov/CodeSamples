/**
 *
 */
package ru.samuylov.queryparser.parser;

import ru.samuylov.queryparser.parser.typehanders.FullTextExactHandler;
import ru.samuylov.queryparser.parser.typehanders.FullTextHandler;
import ru.samuylov.queryparser.parser.typehanders.FulltextWildcardHandler;
import ru.samuylov.queryparser.parser.typehanders.SemanticClassHandler;
import ru.samuylov.queryparser.parser.typehanders.SemanticHandler;
import ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler;

/**
 * тип подзапроса
 *
 * @author samuylov
 *
 */
/**
 * @author samuylov
 *
 */
public enum SubQueryType {
  // порядок важен, в этом порядке будем пробовать определить первый элемент
  SEMANTIC("Семантический запрос", new SemanticHandler()), FULL_TEXT_EXACT(
      "Полнотекстовый запрос - точное совпадение", new FullTextExactHandler()), SEMANTIC_CLASS(
          "Семантические классы", new SemanticClassHandler()), FULLTEXT_WILDCARD(
              "Wildcard в полнотекстовом запросе", new FulltextWildcardHandler()), FULL_TEXT(
                  "Полнотекстовый запрос", new FullTextHandler());

  /**
   * обработчик, который отвечает за обработку(разбор) слов, относящихся к этому типу подзапроса
   */
  private SubQueryTypeHandler typeHandler;
  /**
   * отображаемое имя
   */
  private String name;



  private SubQueryType(String name, SubQueryTypeHandler typeHandler) {
    this.typeHandler = typeHandler;
    this.name = name;
  }


  /**
   * проверяет, можно ли добавить к уже разобранной части запроса, еще одну часть
   *
   * @param token
   * @return
   */
  public boolean canAdd(String token) {
    return typeHandler.canAdd(token);
  }

  /**
   * Проверяет, что элементом можно закрыть данный запрос. например, для запроса точного совпадения
   * требуется чтобы последний элемент содержал кавычки.
   *
   * @param token
   * @return
   */
  public boolean isClosingToken(String token) {
    return typeHandler.isClosingToken(token);
  }


  /**
   * Проверяет, что элементом можно начать данный запрос. например, для запроса точного совпадения
   * требуется чтобы первый элемент содержал кавычки. не учитывает префиксы MUST, MUST NOT
   *
   * @param token
   * @return
   */
  public boolean isStartingToken(String token) {
    return typeHandler.isStartingToken(token);
  }

  /**
   * преобразует первый элемент(с которого начинается подзапрос). например, убирает кавычки, скобки
   * и тд. не учитывает MUST/MUST NOT.
   *
   * @param first
   * @return
   */
  public String transformFirst(String first) {
    return typeHandler.transformFirst(first);
  }

  /**
   * преобразует последний элемент (с которым заканчивается подзапрос). например, убирает кавычки,
   * скобки и тд.
   *
   * @param last
   * @return
   */
  public String transformLast(String last) {
    return typeHandler.transformLast(last);
  }

  /**
   * возвращает имя типа
   *
   * @return
   */
  public String getTypeName() {
    return name;
  }

  /**
   * определяет, какому типу соответствует элемент. следует отличать от метода canAdd, где уже
   * известо, какой тип подзапроса был. В этом методе теструются доступные типы, и определяется к
   * какому типу подзапросу принадлежит элемент. текущая релазиация - по порядку из списка бетеся
   * первый подошедший.
   *
   * @param token элемент, для которого нужно определить тип подзапроса
   * @return тип, к которому относится элемент.
   * @throws ParseException
   */
  public static SubQueryType parseTokenType(String token) throws ParseException {

    for (SubQueryType type : values()) {
      if (type.isStartingToken(token)) {
        return type;
      }
    }

    throw new ParseException("Для элемента " + token + " не найден подходящий тип запроса");

  }



}
