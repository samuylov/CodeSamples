// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser.expressions.atomic;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jetbrains.annotations.NotNull;

/**
 * term(word) which must be searched with some variation of spelling. it can be useful for fixing errors in words writing
 * in search query these expressing looks like ~term
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-fuzzy-query.html">elastic docs</a>
 *
 */
public final class FuzzyTermExpression extends AtomicQueryExpression {
    ////////////////////////////////////////////// Construction ////////////////////////////////////////////////////////
    public FuzzyTermExpression(String text, int expressionOffset) {
        super(text, expressionOffset);
    }

    //////////////////////////////////////////////// Overrides /////////////////////////////////////////////////////////
    @Override
    public String toString() {
        return '~' + getText();
    }

    @Override
    public @NotNull QueryBuilder toQueryBuilder(@NotNull String fieldName) {
        return QueryBuilders.fuzzyQuery(fieldName, getText());
    }
}