package ru.samuylov.queryparser;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jetbrains.annotations.NotNull;
import ru.samuylov.queryparser.parser.QueryLexer;
import ru.samuylov.queryparser.parser.QueryParser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Search query thar can be constructed from human-readable text and syntax.
 *
 */
public class SearchQuery implements IQuery {
    ////////////////////////////////////////////// Construction ////////////////////////////////////////////////////////
    public SearchQuery(@NotNull String searchQueryString) throws RequestParsingException {
        try {
            QueryLexer lexer = new QueryLexer(new ANTLRInputStream(new StringReader(searchQueryString)));
            ANTLRErrorListener errorListener = new ThrowingErrorListener();
            lexer.removeErrorListeners();
            lexer.addErrorListener(errorListener);

            QueryParser parser = new QueryParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);
            queryItems.addAll(parser.query().items);
        } catch (ParsingRuntimeException e) {
            throw e.getCause();
        } catch (IOException e) {
            throw new RequestParsingException(e);
        }
    }

    private final List<IQueryItem> queryItems = new ArrayList<>();

    ////////////////////////////////////////////// Overrides ////////////////////////////////////////////////////////

    @Override
    public @NotNull Collection<IQueryItem> getQueryItems() {
        return Collections.unmodifiableList(queryItems);
    }

    @Override
    public String toString() {
        return StringUtils.join(queryItems," ");
    }

    @Override
    public @NotNull QueryBuilder toQueryBuilder(@NotNull String fieldName) {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        getQueryItems().stream().filter(item -> item.getOperator()==QueryOperator.Must)
                .forEach(item -> boolQuery.must(item.getItemExpression().toQueryBuilder(fieldName)));

        getQueryItems().stream().filter(item -> item.getOperator()==QueryOperator.None)
                .forEach(item -> boolQuery.should(item.getItemExpression().toQueryBuilder(fieldName)));

        getQueryItems().stream().filter(item -> item.getOperator()==QueryOperator.MustNot)
                .forEach(item -> boolQuery.mustNot(item.getItemExpression().toQueryBuilder(fieldName)));

        return boolQuery;
    }
}
