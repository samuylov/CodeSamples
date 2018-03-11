// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser.expressions.combined;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jetbrains.annotations.NotNull;
import ru.samuylov.queryparser.IQueryExpression;

import java.util.List;

/**
 * expression which looks like &lt;subexpression1&gt; | &lt;subexpression2&gt; | &lt;subexpression3&gt; ...
 */
public final class SynonymExpression extends CombinedQueryExpression {
    ////////////////////////////////////////////// Construction ////////////////////////////////////////////////////////
    public SynonymExpression(
            @NotNull List<IQueryExpression> branches, int expressionOffset) {
        super(branches, expressionOffset);
    }

    //////////////////////////////////////////////// Overrides /////////////////////////////////////////////////////////
    @Override
    public String toString() {
        return StringUtils.join(getBranches(), "|");
    }

    @Override
    public @NotNull QueryBuilder toQueryBuilder(@NotNull String fieldName) {
        if( hasSingleBranch() )
            return getSingleBranch().toQueryBuilder(fieldName);

        DisMaxQueryBuilder disMaxQuery = QueryBuilders.disMaxQuery();
        for( IQueryExpression subExpression : getBranches() )
            disMaxQuery = disMaxQuery.add( subExpression.toQueryBuilder(fieldName) );

        return disMaxQuery;
    }
}