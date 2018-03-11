// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser;

import org.elasticsearch.index.query.QueryBuilder;
import org.jetbrains.annotations.NotNull;
import ru.samuylov.queryparser.QueryOperator;

/**
 * some search expression, for example simple word or wildcard expression
 */
public interface IQueryExpression {
    /**
     * @return offset from start of search query
     */
    int getExpressionStartOffset();

    /**
     * transforms expression to Elastic query syntax
     *
     * @param fieldName name of field to perform search
     * @return Elastic search query
     */
    @NotNull QueryBuilder toQueryBuilder(@NotNull String fieldName);
}