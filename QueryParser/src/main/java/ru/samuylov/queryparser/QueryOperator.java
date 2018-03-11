// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser;

/**
 * Search operator for part of query
 */
public enum QueryOperator {
    Must("+"),
    MustNot("-"),
    None("");

    /////////////////////////////////////////////// Construction ////////////////////////////////////////////////////////
    QueryOperator(String character) {
        this.character = character;
    }

    private final String character;

    /////////////////////////////////////////////// Attributes ////////////////////////////////////////////////////////
    public String getCharacter() {
        return character;
    }
}
