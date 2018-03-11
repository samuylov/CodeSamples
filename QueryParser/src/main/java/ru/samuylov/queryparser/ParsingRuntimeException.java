package ru.samuylov.queryparser;

import org.jetbrains.annotations.NotNull;

/**
 * Wrapping runtime exception for transfer error through antlr parser
 */
public final class ParsingRuntimeException extends RuntimeException {
    ParsingRuntimeException(@NotNull RequestParsingException cause) {
        super(cause);
    }

    @Override
    public synchronized @NotNull RequestParsingException getCause() {
        return (RequestParsingException) super.getCause();
    }
}
