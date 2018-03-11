package ru.samuylov.queryparser;

import org.jetbrains.annotations.NotNull;

/**
 * top-level part of search query with operator
 */
public interface IQueryItem {

    /**
     * @return expression of this part of query
     */
    @NotNull IQueryExpression getItemExpression();

    /**
     * @return operator of this part of query
     */
    @NotNull QueryOperator getOperator();

    /**
     *
     * @return offset of operator from start of query
     */
    int getOperatorOffset();
}
