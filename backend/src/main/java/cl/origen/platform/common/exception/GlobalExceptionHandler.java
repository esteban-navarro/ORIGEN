package cl.origen.platform.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.origen.platform.common.constants.ApiMessages;
import cl.origen.platform.common.response.Response;
import cl.origen.platform.common.response.ResponseFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for REST controllers.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles authentication failures caused by invalid credentials.
     *
     * @param ex authentication exception
     * @return unauthorized response
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<Void>> handleBadCredentials(
            final BadCredentialsException ex) {

        log.warn("Authentication failed: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ResponseFactory.error(
                                ApiMessages.INVALID_CREDENTIALS));

    }

    /**
     * Handles authorization failures.
     *
     * @param ex authorization exception
     * @return forbidden response
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response<Void>> handleAccessDenied(
            final AccessDeniedException ex) {

        log.warn("Access denied: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        ResponseFactory.error(
                                ApiMessages.ACCESS_DENIED));

    }

    /**
     * Handles bean validation errors.
     *
     * @param ex validation exception
     * @return bad request response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Void>> handleValidation(
            final MethodArgumentNotValidException ex) {

        log.warn("Validation error: {}", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(
                        ResponseFactory.error(
                                ApiMessages.VALIDATION_ERROR));

    }

    /**
     * Handles requests for resources that do not exist.
     *
     * @param ex resource not found exception
     * @return not found response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response<Void>> handleResourceNotFound(
            final ResourceNotFoundException ex) {

        log.warn("Resource not found: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseFactory.error(
                                ex.getMessage()));

    }

    /**
     * Handles resource conflicts.
     *
     * @param ex conflict exception
     * @return conflict response
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Response<Void>> handleConflict(
            final ConflictException ex) {

        log.warn("Resource conflict: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ResponseFactory.error(
                                ex.getMessage()));

    }

    /**
     * Handles unexpected exceptions.
     *
     * @param ex unexpected exception
     * @return internal server error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleException(
            final Exception ex) {

        log.error("Unexpected exception", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseFactory.error(
                                ApiMessages.INTERNAL_SERVER_ERROR));

    }

}
