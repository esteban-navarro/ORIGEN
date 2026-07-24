package cl.origen.platform.common.response;

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
     * @param data response data
     * @param message response message
     * @return successful response
     */
    public static <T> Response<T> ok(T data, String message) {

        return Response.<T>builder()
                .status(ResponseStatus.OK)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Creates a warning response.
     *
     * @param <T> response payload type
     * @param data response data
     * @param message response message
     * @return warning response
     */
    public static <T> Response<T> warning(T data, String message) {

        return Response.<T>builder()
                .status(ResponseStatus.WARNING)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Creates an error response.
     *
     * @param <T> response payload type
     * @param message response message
     * @return error response
     */
    public static <T> Response<T> error(String message) {

        return Response.<T>builder()
                .status(ResponseStatus.ERROR)
                .message(message)
                .build();
    }

}