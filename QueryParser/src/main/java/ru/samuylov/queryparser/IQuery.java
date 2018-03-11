// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser;

import org.elasticsearch.index.query.QueryBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * search query for elastic
 */
public interface IQuery {
    /**
     * transforms human-readable query to Elastic query syntax
     *
     * @param fieldName name of field to perform search
     * @return Elastic search queries
     */
    @NotNull QueryBuilder toQueryBuilder(@NotNull String fieldName);

    /**
     * @return elements of parsed query
     */
    @NotNull Collection<IQueryItem> getQueryItems();
}