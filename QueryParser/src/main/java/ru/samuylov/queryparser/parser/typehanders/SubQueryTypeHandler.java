/**
 *
 */
package ru.samuylov.queryparser.parser.typehanders;

/**
 * Интерфейс, которы реализуется для каждого типа подзапроса, и который используется при разборе
 * поискового запроса
 *
 * @author samuylov
 *
 */
public interface SubQueryTypeHandler {

  /**
   * преобразует первый элемент(с которого начинается подзапрос). например, убирает кавычки, скобки
   * и тд. не учитывает MUST/MUST NOT.
   *
   * @param first
   * @return
   */
  public String transformFirst(String first);

  /**
   * преобразует последний элемент (с которым заканчивается подзапрос). например, убирает кавычки,
   * скобки и тд.
   *
   * @param last
   * @return
   */
  public String transformLast(String last);

  /**
   * Проверяет, что элементом можно закрыть данный запрос. например, для запроса точного совпадения
   * требуется чтобы последний элемент содержал кавычки
   *
   * @param token
   * @return
   */
  public boolean isClosingToken(String token);

  /**
   * проверяет, можно ли добавить к уже разобранной части запроса, еще одну часть (в середину)
   *
   * @param token
   * @return
   */
  public boolean canAdd(String token);


  /**
   * Проверяет, что элементом можно начать данный запрос. например, для запроса точного совпадения
   * требуется чтобы первый элемент содержал кавычки. не учитывает префиксы MUST, MUST NOT
   *
   * @param token
   * @return
   */
  public boolean isStartingToken(String token);
}
