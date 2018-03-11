package ru.samuylov.queryparser;

import org.antlr.v4.runtime.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.samuylov.queryparser.parser.QueryParser;

/**
 * antlr errors listener, which generates exception for some well-known cases
 */
final class ThrowingErrorListener extends BaseErrorListener {
    private static final Logger logger = LoggerFactory.getLogger(ThrowingErrorListener.class);

    ////////////////////////////////////////////// Overrides ////////////////////////////////////////////////////////
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        logger.error("parsing exception at line: {}, position in line: {}, message: {}", line, charPositionInLine, msg);

        CommonToken token = (CommonToken) offendingSymbol;
        if (token.getType() == Token.EOF)
            throw new ParsingRuntimeException(new RequestParsingException("Unexpected end of query"));
        if (token.getType() == QueryParser.UNKNOWN)
            throw new RuntimeException("Query contains unknown symbol '" + token.getText() + " at position " + token.getStartIndex());

        throw new RuntimeException("Invalid operator '" + token.getText() + "' at position " + token.getStartIndex());
    }
}

