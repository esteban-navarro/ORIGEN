package cl.origen.platform.common.response;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Factory class for creating standardized API responses.
 */
public final class ResponseFactory {

    /**
     * Private constructor to prevent instantiation.
     */
    private ResponseFactory() {
    }

    /**
     * Creates a successful response.
     *
     * @param <T> response payload type
     * @param message response message
     * @param data response data
     * @return successful response
     */
    public static <T> Response<T> ok(
            final String message,
            final T data) {

        return build(
                ResponseStatus.OK,
                message,
                data);

    }

    /**
     * Creates a warning response.
     *
     * @param <T> response payload type
     * @param message response message
     * @param data response data
     * @return warning response
     */
    public static <T> Response<T> warning(
            final String message,
            final T data) {

        return build(
                ResponseStatus.WARNING,
                message,
                data);

    }

    /**
     * Creates an error response.
     *
     * @param <T> response payload type
     * @param message response message
     * @return error response
     */
    public static <T> Response<T> error(
            final String message) {

        return build(
                ResponseStatus.ERROR,
                message,
                null);

    }

    /**
     * Creates a standardized response.
     *
     * @param <T> response payload type
     * @param status response status
     * @param message response message
     * @param data response payload
     * @return standardized response
     */
    private static <T> Response<T> build(
            final ResponseStatus status,
            final String message,
            final T data) {

        return Response.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .timestamp(currentTimestamp())
                .build();

    }

    /**
     * Returns the current UTC timestamp.
     *
     * @return current timestamp
     */
    private static OffsetDateTime currentTimestamp() {

        return OffsetDateTime.now(ZoneOffset.UTC);

    }

}
