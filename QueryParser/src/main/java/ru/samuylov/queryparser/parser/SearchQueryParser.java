/**
 *
 */
package ru.samuylov.queryparser.parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * основной класс для запуска разбора поискового запроса
 *
 * @author samuylov
 *
 */
public class SearchQueryParser {

  /**
   * разбот поискового запроса на подзапросы в соответствии с их типом
   *
   * @param source строка для разбора
   * @return коллекция подзапросов в соответствии с их типом
   * @throws ParseException в случае ошибки формата входной строки
   */
  public Collection<SubQuery> parse(String source) throws ParseException {
    Map<ResultMapKey, SubQuery> result = new HashMap<>();

    String[] tokens = StringUtils.split(source);
    SubQuery openedCloseableSubQuery = null;

    for (String token : tokens) {
      if (openedCloseableSubQuery != null) {
        // у нас уже был накопленный результат,
        // и он предполагает, что его можно закрыть. поэтому нам нужно или закрыть его,
        //или чтобы последующий элемент был того же типа

        // если можем закрыть, то отлично. закрываем.
        if (openedCloseableSubQuery.isClosingToken(token)) {
          openedCloseableSubQuery.close(token);

          openedCloseableSubQuery = null;
          continue;
        } else if (openedCloseableSubQuery.canAdd(token)) {
          // не можем закрыть, то он подходит по типу, добавляем к текущему подзапросу элемент
          openedCloseableSubQuery.add(token);
          continue;
        }else{
          throw new ParseException("При обработке элемента " + token
              + " не получилось отнеси его к начавшемуся ранее запросу, "
              + "предполагающему закрывающий элемент. запрос: " + openedCloseableSubQuery);
        }
      } else {
        // если первый подзапрос, или предыдущий закончился, смотрим что же нам пришло
        // нового,начинаем новый запрос
        openedCloseableSubQuery = processSingleElement(result, token);
      }
    }

    // провеяем, может быть то, что осталось нужно закрывать, и завершаем разбор
    if (openedCloseableSubQuery != null) {
      throw new ParseException("Элемент запроса остался не закрытым: " + openedCloseableSubQuery);
    }

    return result.values();
  }

  /**
   * обрабатывает одиночный элемент. на основании элемента определяет, к какому типу его отнести,
   * если в накопленном результате есть подзапрос этого типа, то добавляет элемент к нему, если нет,
   * создает новый. если эемент - это первый элемент составного запроса, то этот составной элемент
   * возвращается.
   *
   * @param result коллекция с уже разобранными подзапросами
   * @param token элемент, который нужно добавить к какому-либо подзапросу
   * @return если token - начало составного элемента, которы требуется закрывать, то возращается
   *         этот элемент
   * @throws ParseException ошибка разбора сообщения
   */
  private SubQuery processSingleElement(Map<ResultMapKey, SubQuery> result, String token)
      throws ParseException {
    // смотрим, есть ли префикс MUST/MUST NOT
    SubQueryPrefix prefix = SubQueryPrefix.parseTokenPrefix(token);
    if (prefix != null) {
      // если есть, убираем этот префикс
      token = prefix.transformFirst(token);
    }

    // определяем, к какому типу запрсоа утносится слово
    SubQueryType type = SubQueryType.parseTokenType(token);

    ResultMapKey key = new ResultMapKey(type, prefix);

    SubQuery subQuery = result.get(key);
    if (subQuery == null) {
      subQuery = new SubQuery(prefix, type);
      result.put(key, subQuery);
    }

    if (subQuery.processFirstElement(token)) {
      return subQuery;
    } else {
      return null;
    }
  }


  /**
   * т.к. в списке ответов запросы должны быть неповторяющимися, то сделаем ключ для мапы по тем
   * полям, которые нужны
   *
   * @author samuylov
   *
   */
  private static class ResultMapKey {
    /**
     * тип подзапроса
     */
    private SubQueryType type;

    /**
     * префикс MUST/ MUST NOT / отсутствует
     */
    private SubQueryPrefix prefix;


    /**
     * @param type
     * @param prefix
     */
    public ResultMapKey(SubQueryType type, SubQueryPrefix prefix) {
      super();
      this.type = type;
      this.prefix = prefix;
    }


    // Generated methods

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
      result = prime * result + ((type == null) ? 0 : type.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      ResultMapKey other = (ResultMapKey) obj;
      if (prefix != other.prefix) {
        return false;
      }
      if (type != other.type) {
        return false;
      }
      return true;
    }

  }
}
