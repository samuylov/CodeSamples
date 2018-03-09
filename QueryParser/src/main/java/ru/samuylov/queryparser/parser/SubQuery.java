/**
 *
 */
package ru.samuylov.queryparser.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author samuylov
 *
 */
public class SubQuery {
  /**
   * префик запроса( MUST/ MUST NOT), если есть
   */
  private SubQueryPrefix prefix;
  /**
   * тип подзапроса
   */
  private SubQueryType type;

  /**
   * элементы запроса
   */
  private List<String> tokens = new ArrayList<>();

  public SubQuery(SubQueryPrefix prefix, SubQueryType type) {
    this.prefix = prefix;
    this.type = type;
  }

  /**
   * проверяет, можно ли добавить в подзапросу еще одно слово. смотрит, что слово подходит по типу к
   * подзапросу.
   *
   * @param token
   * @return
   */
  public boolean canAdd(String token) {
    return type.canAdd(token);
  }

  /**
   * добавляет элемент к подзапросу
   * @param token
   */
  public void add(String token) {
    if(canAdd(token)){
      tokens.add(token);
    }else{
      throw new RuntimeException("Невозможно добавить элемент " + token + " к подзапросу " + this
          + ", он не подходит по типу");
    }
  }


  /**
   * Проверяет, что новым элементом можно закрыть данный запрос
   *
   * @param token
   * @return
   */
  public boolean isClosingToken(String token) {
    return type.isClosingToken(token);
  }

  /**
   * выполняет инициализацию подзапроса элементом. при этом в списке token уже может быть результат.
   * работает, только если добавляемый элемент явяется начинающим
   *
   * @return true если требуется закрытие подзапроса
   */
  public boolean processFirstElement(String token) {
    if (type.isStartingToken(token)) {
      token = type.transformFirst(token);
      if (type.isClosingToken(token)) {
        tokens.add(type.transformLast(token));
        return false;
      } else {
        tokens.add(token);
        return true;
      }
    } else {
      throw new RuntimeException(
          "Невозможно начать подзапрос элементом " + token + " запрос: " + this);
    }
  }


  /**
   * выполняет закрытие подзапроса элементом.
   *
   * @param token
   */
  public void close(String token) {
    if (isClosingToken(token)) {
      tokens.add(type.transformLast(token));
    } else {
      throw new RuntimeException(
          "Невозможно закрыть подзапрос элементом " + token + " запрос: " + this);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String prefixStr = prefix == null ? "" : ", " + prefix.name();

    return "Подзапрос [тип: " + type.getTypeName() + prefixStr + ", запрос: "
        + StringUtils.join(tokens.iterator(), " ") + " ]";
  }

  public SubQueryPrefix getPrefix() {
    return prefix;
  }

  public SubQueryType getType() {
    return type;
  }

  public List<String> getTokens() {
    return tokens;
  }

}
