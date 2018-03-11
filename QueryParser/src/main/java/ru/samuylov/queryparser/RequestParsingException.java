package ru.samuylov.queryparser;

import org.jetbrains.annotations.NotNull;

/**
 * The exception to report of some errors during search query parsing
 */
public final class RequestParsingException extends Exception {
    public RequestParsingException(@NotNull String message) {
        super(message);
    }

    public RequestParsingException(Throwable cause) {
        super(cause);
    }
}
