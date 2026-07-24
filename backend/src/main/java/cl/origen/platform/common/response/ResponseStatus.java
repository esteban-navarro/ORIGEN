package cl.origen.platform.common.response;

/**
 * Represents the execution status of an API request.
 */
public enum ResponseStatus {

    /**
     * Request completed successfully.
     */
    OK,

    /**
     * Request could not be completed.
     */
    ERROR,

    /**
     * Request completed successfully but generated warnings.
     */
    WARNING

}
