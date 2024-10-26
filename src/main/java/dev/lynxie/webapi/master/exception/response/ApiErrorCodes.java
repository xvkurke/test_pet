package dev.lynxie.webapi.master.exception.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiErrorCodes {
    AUTHORIZATION_ERROR(401),
    NOT_FOUND(404),
    ROUTE_NOT_FOUND(404),
    OTHER_ERROR(400),
    VALIDATION_ERROR(402),
    ACCESS_DENIED(403),
    METHOD_NOT_ALLOWED(405),
    BAD_REQUEST(400),
    REQUEST_TIME_OUT(404),
    SERVER_ERROR(500),
    CONFLICT_ERROR(409);

    private final Integer errorCode;
}
