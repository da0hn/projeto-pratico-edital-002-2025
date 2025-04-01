package br.com.ghonda.application.rest;

import br.com.ghonda.application.rest.payload.ApiFailureResponse;
import br.com.ghonda.core.exceptions.InvalidTokenException;
import br.com.ghonda.core.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiFailureResponse> handleConstraintViolationException(final ConstraintViolationException exception) {
        log.error("m=handleConstraintViolationException(message={})", exception.getMessage(), exception);

        final var status = HttpStatus.BAD_REQUEST;

        final var failures = exception.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .toList();

        final var response = ApiFailureResponse.builder()
            .message(exception.getMessage())
            .failures(failures)
            .code(status.value())
            .status(status.toString())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiFailureResponse> handleResourceNotFoundException(final ResourceNotFoundException exception) {
        log.error("m=handleResourceNotFoundException(message={})", exception.getMessage(), exception);

        final var status = HttpStatus.NOT_FOUND;

        final var response = ApiFailureResponse.builder()
            .message(exception.getMessage())
            .code(status.value())
            .status(status.toString())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler({ Exception.class, RuntimeException.class })
    public ResponseEntity<ApiFailureResponse> handleGenericException(final Exception exception) {
        log.error("m=handleGenericException(message={})", exception.getMessage(), exception);

        final var status = HttpStatus.INTERNAL_SERVER_ERROR;

        final var response = ApiFailureResponse.builder()
            .message(exception.getMessage())
            .code(status.value())
            .status(status.toString())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiFailureResponse> handleIllegalArgumentException(final IllegalArgumentException exception) {
        log.error("m=handleIllegalArgumentException(message={})", exception.getMessage(), exception);

        final var status = HttpStatus.BAD_REQUEST;

        final var response = ApiFailureResponse.builder()
            .message(exception.getMessage())
            .code(status.value())
            .status(status.toString())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiFailureResponse> handleIllegalStateException(final IllegalStateException exception) {
        log.error("m=handleIllegalStateException(message={})", exception.getMessage(), exception);

        final var status = HttpStatus.PRECONDITION_FAILED;

        final var response = ApiFailureResponse.builder()
            .message(exception.getMessage())
            .code(status.value())
            .status(status.toString())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiFailureResponse> handleNullPointerException(final NullPointerException exception) {
        log.error("m=handleNullPointerException(message={})", exception.getMessage(), exception);

        final var status = HttpStatus.INTERNAL_SERVER_ERROR;

        final var response = ApiFailureResponse.builder()
            .message(exception.getMessage())
            .code(status.value())
            .status(status.toString())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiFailureResponse> handleInvalidTokenException(final InvalidTokenException exception) {
        log.error("m=handleInvalidTokenException(message={})", exception.getMessage(), exception);

        final var status = HttpStatus.UNAUTHORIZED;

        final var response = ApiFailureResponse.builder()
            .message(exception.getMessage())
            .code(status.value())
            .status(status.toString())
            .timestamp(LocalDateTime.now())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiFailureResponse> handleBadCredentialsException(final BadCredentialsException exception) {
        log.error("m=handleBadCredentialsException(message={})", exception.getMessage(), exception);

        final var status = HttpStatus.UNAUTHORIZED;

        final var response = ApiFailureResponse.builder()
            .message("Credenciais inválidas")
            .code(status.value())
            .status(status.toString())
            .timestamp(LocalDateTime.now())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiFailureResponse> handleUsernameNotFoundException(final UsernameNotFoundException exception) {
        log.error("m=handleUsernameNotFoundException(message={})", exception.getMessage(), exception);

        final var status = HttpStatus.NOT_FOUND;

        final var response = ApiFailureResponse.builder()
            .message(exception.getMessage())
            .code(status.value())
            .status(status.toString())
            .timestamp(LocalDateTime.now())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    private String extractFieldName(final String errorCode, final String defaultName) {
        if (errorCode == null || errorCode.isEmpty()) {
            return defaultName;
        }
        final var parts = errorCode.split("\\.");
        return parts.length > 1 ? parts[parts.length - 1] : defaultName;
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        final HttpRequestMethodNotSupportedException ex,
        final HttpHeaders headers,
        final HttpStatusCode status,
        final WebRequest request
    ) {
        log.error(
            "m=handleHttpRequestMethodNotSupported(message={}, headers={}, status={}, request={})",
            ex.getMessage(),
            headers,
            status,
            request,
            ex
        );

        final var supportedMethods = ex.getSupportedHttpMethods().stream()
            .map(HttpMethod::name)
            .collect(Collectors.joining(", "));

        final var requestURI = ((ServletRequestAttributes) request).getRequest().getRequestURI();
        final var message = "O método %s não é suportado para o recurso %s. Os métodos suportados são: %s"
            .formatted(ex.getMethod(), requestURI, supportedMethods);

        final var response = ApiFailureResponse.builder()
            .message(message)
            .code(status.value())
            .status(status.toString())
            .build();

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        final MethodArgumentNotValidException ex,
        final HttpHeaders headers,
        final HttpStatusCode status,
        final WebRequest request
    ) {
        log.error(
            "m=handleMethodArgumentNotValid(message={}, headers={}, status={}, request={})",
            ex.getMessage(),
            headers,
            status,
            request,
            ex
        );

        final var fieldErrors = ex.getBindingResult().getFieldErrors();
        final var message = fieldErrors.stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();
        final var response = ApiFailureResponse.builder()
            .message("Erro de validação nos dados enviados")
            .failures(message)
            .code(status.value())
            .status(status.toString())
            .timestamp(LocalDateTime.now())
            .build();
        return ResponseEntity.status(status).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
        final HandlerMethodValidationException ex,
        final HttpHeaders headers,
        final HttpStatusCode status,
        final WebRequest request
    ) {
        log.error(
            "m=handleHandlerMethodValidationException(message={}, headers={}, status={}, request={})",
            ex.getMessage(),
            headers,
            status,
            request,
            ex
        );

        final var errors = ex.getParameterValidationResults().stream()
            .flatMap(result -> {
                final String parameterName = result.getMethodParameter().getParameterName();
                return result.getResolvableErrors().stream()
                    .map(error -> {
                        final String errorCode = error.getCodes() != null && error.getCodes().length > 0 ?
                            error.getCodes()[0] : "";

                        final String fieldName = this.extractFieldName(errorCode, parameterName);

                        return "Campo '%s': %s".formatted(fieldName, error.getDefaultMessage());
                    });
            })
            .collect(Collectors.toList());

        final var response = ApiFailureResponse.builder()
            .message("Erro de validação nos dados enviados")
            .failures(errors)
            .code(status.value())
            .status(status.toString())
            .timestamp(LocalDateTime.now())
            .build();

        return ResponseEntity.status(status).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
        final NoHandlerFoundException ex,
        final HttpHeaders headers,
        final HttpStatusCode status,
        final WebRequest request
    ) {
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
        final NoResourceFoundException ex,
        final HttpHeaders headers,
        final HttpStatusCode status,
        final WebRequest request
    ) {
        log.error(
            "m=handleNoResourceFoundException(message={}, headers={}, status={}, request={})",
            ex.getMessage(),
            headers,
            status,
            request,
            ex
        );

        final var resourceURI = ((ServletRequestAttributes) request).getRequest().getRequestURI();

        final var message = "O recurso %s %s não foi encontrado".formatted(ex.getHttpMethod(), resourceURI);

        final var response = ApiFailureResponse.builder()
            .message(message)
            .code(status.value())
            .status(status.toString())
            .build();

        return ResponseEntity.status(status)
            .body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(
        final MaxUploadSizeExceededException ex,
        final HttpHeaders headers,
        final HttpStatusCode status,
        final WebRequest request
    ) {
        return super.handleMaxUploadSizeExceededException(ex, headers, status, request);
    }

}
