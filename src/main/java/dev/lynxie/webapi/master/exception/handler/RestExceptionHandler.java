package dev.lynxie.webapi.master.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.lynxie.webapi.user.exception.AuthorizationException;
import dev.lynxie.webapi.master.exception.CustomException;
import dev.lynxie.webapi.master.exception.filter.RequestResponseLogFilter;
import dev.lynxie.webapi.master.exception.response.ApiError;
import dev.lynxie.webapi.master.exception.response.ApiErrorCodes;
import dev.lynxie.webapi.master.exception.response.ExceptionResponse;
import dev.lynxie.webapi.user.exception.RegistrationException;
import dev.lynxie.webapi.utils.RequestUtils;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final ExceptionResponse exceptionResponse;
    private final RequestUtils requestUtils;

    @Order(1)
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ApiError> handleException(CustomException ex, HttpServletRequest request, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nURL: {}\nBody: {}\nHandle custom exception: details {}, ex -> []", 
                requestId, request.getRequestURI(), requestUtils.extractBody(request), ex.getMessage(), ex);
        return exceptionResponse.buildResponseEntity(ex.getCode(), ex.getMessage(), ex);
    }

    @Order(2)
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiError> handleException(AccessDeniedException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nAccessDeniedException: {}", requestId, ExceptionUtils.getStackTrace(ex));
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.ACCESS_DENIED.getErrorCode(), "Forbidden", ex);
    }

    @Order(3)
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nValidationException: {}", requestId, ExceptionUtils.getStackTrace(ex));
        String message = ex.getCause() == null ? ex.getMessage() : ex.getCause().getMessage();
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.VALIDATION_ERROR.getErrorCode(), message, ex);
    }

    @Order(3)
    @ExceptionHandler({InvalidFormatException.class})
    public ResponseEntity<ApiError> handleInvalidFormatException(InvalidFormatException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nInvalidFormatException ->", requestId, ex);
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.VALIDATION_ERROR.getErrorCode(), formatFieldExceptionMessage(ex), ex);
    }

    @Order(4)
    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<ApiError> handleNumberFormatException(NumberFormatException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nNumberFormatException -> ", requestId, ex);
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.VALIDATION_ERROR.getErrorCode(), ex.getMessage(), ex);
    }

    @Order(5)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nMethodArgumentNotValidException -> ", requestId, ex);
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.VALIDATION_ERROR.getErrorCode(), formatFieldExceptionMessage(ex), ex);
    }

    @Order(6)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nMethodArgumentTypeMismatchException -> ", requestId, ex);
        String message = String.format("Invalid field type for %s", ex.getParameter().getParameterName());

        return exceptionResponse.buildResponseEntity(ApiErrorCodes.VALIDATION_ERROR.getErrorCode(), message, ex);
    }

    @Order(8)
    @ExceptionHandler({NoHandlerFoundException.class})
    public Object handleStaticNotFoundException(final NoHandlerFoundException ex, HttpServletRequest req, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nNoHandlerFoundException {}", requestId, req.getRequestURI());
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.ROUTE_NOT_FOUND.getErrorCode(), "Page not found", ex);
    }

    @Order(9)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nConstraintViolationException -> ", requestId, ex);
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.BAD_REQUEST.getErrorCode(), "Bad request", ex);
    }

    @Order(10)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nMissingServletRequestParameterException -> ", requestId, ex);
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.VALIDATION_ERROR.getErrorCode(), "Validation error", ex);
    }

    @Order(11)
    @ExceptionHandler({BindException.class})
    public ResponseEntity<ApiError> handleBindException(BindException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nBindException -> ", requestId, ex);
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.VALIDATION_ERROR.getErrorCode(), formatFieldExceptionMessage(ex), ex);
    }

    @Order(12)
    @ExceptionHandler({MissingServletRequestPartException.class})
    public ResponseEntity<ApiError> handleMissingServletRequestPartException(MissingServletRequestPartException ex, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nMissingServletRequestPartException -> ", requestId, ex);
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.VALIDATION_ERROR.getErrorCode(), "Validation error", ex);
    }

    @Order(13)
    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<ApiError> handleAuthorizationException(AuthorizationException ex, HttpServletRequest request, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nURL: {}\nBody: {}, Handle AuthorizationException, details {}, ex -> []", 
                requestId, request.getRequestURI(), requestUtils.extractBody(request), ex.getMessage(), ex);
        return exceptionResponse.buildResponseEntity(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex);
    }

    @Order(13)
    @ExceptionHandler({RegistrationException.class})
    public ResponseEntity<ApiError> handleAuthRegistrationException(RegistrationException ex, HttpServletRequest request, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nURL: {}\nBody: {}, Handle RegistrationException, details {}, ex -> []", 
                requestId, request.getRequestURI(), requestUtils.extractBody(request), ex.getMessage(), ex);
        return exceptionResponse.buildResponseEntity(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex);
    }

    @Order(14)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nURL: {}\nBody: {}, HttpRequestMethodNotSupportedException", requestId, request.getRequestURI(), requestUtils.extractBody(request), ex);
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.METHOD_NOT_ALLOWED.getErrorCode(), "Method not allowed", ex);
    }

    @Order(15)
    @ExceptionHandler({HandlerMethodValidationException.class})
    public ResponseEntity<ApiError> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpServletRequest request, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nURL: {}\n Body: {}, HandlerMethodValidationException -> []", requestId, request.getRequestURI(), requestUtils.extractBody(request), ex);
        Object[] detailMessageArguments = ex.getDetailMessageArguments();
        String errorMessage = detailMessageArguments == null ? ex.getMessage() : detailMessageArguments[0].toString();
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.BAD_REQUEST.getErrorCode(), errorMessage, ex);
    }

    @Order(16)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        final String requestId = retrieveRequestHeader(response);
        log.error("RequestId: {}\nURL: {}\n Body: {}, otherException exception -> []",
                requestId, request.getRequestURI(), requestUtils.extractBody(request), ex);
        return exceptionResponse.buildResponseEntity(ApiErrorCodes.OTHER_ERROR.getErrorCode(), "Other error", ex);
    }

    private String formatFieldExceptionMessage(Exception ex) {
        BindingResult error = (BindingResult) ex;

        return error.getAllErrors()
                .stream()
                .map(objectError -> {
                    String defaultMessage = objectError.getDefaultMessage();
                    if (defaultMessage != null && defaultMessage.contains("Failed to convert property value of type")) {
                        return "Invalid field: " + ((FieldError) objectError).getField();
                    } else {
                        return defaultMessage;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }

    private String retrieveRequestHeader(HttpServletResponse response) {
        if (response == null) {
            return null;
        }
        return response.getHeader(RequestResponseLogFilter.ID_HEADER);
    }
}
