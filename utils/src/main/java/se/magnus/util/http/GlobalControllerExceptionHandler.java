package se.magnus.util.http;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.magnus.util.exceptions.InvalidInputException;
import se.magnus.util.exceptions.NotFoundException;

@RestControllerAdvice
class GlobalControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpErrorInfo> handleNotFoundExceptions(final ServerHttpRequest request, final Exception ex) {
        return ResponseEntity.status(NOT_FOUND)
                .body(createHttpErrorInfo(NOT_FOUND, request, ex));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<HttpErrorInfo> handleInvalidInputException(final ServerHttpRequest request, final Exception ex) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY)
                .body(createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex));
    }

    private HttpErrorInfo createHttpErrorInfo(final HttpStatus httpStatus, final ServerHttpRequest request,
                                              final Exception ex) {
        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();
        LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }
}
