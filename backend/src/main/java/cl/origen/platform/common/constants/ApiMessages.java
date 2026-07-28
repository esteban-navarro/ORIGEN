package cl.origen.platform.common.constants;

/**
 * Centralized API messages used across the application.
 *
 * Keeping messages in a single place avoids duplicated literals,
 * improves maintainability and simplifies future localization.
 */
public final class ApiMessages {

    private ApiMessages() {
    }

    public static final String APPLICATION_RUNNING = "Application is running.";

    public static final String LOGIN_SUCCESS = "Login successful.";

    public static final String INVALID_CREDENTIALS = "Invalid username or password.";

    public static final String ACCESS_DENIED = "Access denied.";

    public static final String VALIDATION_ERROR = "Validation error.";

    public static final String INTERNAL_SERVER_ERROR = "An unexpected error occurred.";

}
