/**
 *
 */
package ru.samuylov.queryparser.parser.typehanders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author samuylov
 *
 */
public class SemanticHandlerTest {

  private SemanticHandler subj = new SemanticHandler();

  @Test
  public void testCanAdd() {
    assertTrue(subj.canAdd("dfdfgdg"));
    assertTrue(subj.canAdd("fffg444"));
    assertFalse(subj.canAdd("fsf\"fd446"));
    assertFalse(subj.canAdd("!@%#^$%)$%^"));
    assertFalse(subj.canAdd(">?FDZ"));
  }

  @Test
  public void testIsStartingToken() {
    assertTrue(subj.isStartingToken("{dfdfgdg}"));
    assertTrue(subj.isStartingToken("{dfdfgdg"));
    assertFalse(subj.isStartingToken("{fsf\"fd446"));
    assertFalse(subj.isStartingToken(">?FDZ"));
  }


  @Test
  public void testIsClosingToken() {
    assertTrue(subj.isClosingToken("{dfdfgdg}"));
    assertTrue(subj.isClosingToken("dfdfgdg}"));
    assertFalse(subj.isClosingToken("{fsf\"fd446}"));
    assertFalse(subj.isClosingToken(">?FDZ"));
  }

  @Test
  public void testTansformLast() {
    assertEquals("{dfdfgdg", subj.transformLast("{dfdfgdg}"));
    assertEquals("dfdfgdg", subj.transformLast("dfdfgdg}"));
    assertEquals("{fsf\"fd446", subj.transformLast("{fsf\"fd446}"));
  }

  @Test
  public void testTransformFirst() {
    assertEquals("dfdfgdg}", subj.transformFirst("{dfdfgdg}"));
    assertEquals("dfdfgdg", subj.transformFirst("{dfdfgdg"));
    assertEquals("fsf\"fd446", subj.transformFirst("{fsf\"fd446"));
  }
}
