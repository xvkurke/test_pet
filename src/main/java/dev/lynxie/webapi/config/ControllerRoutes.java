package dev.lynxie.webapi.config;

// List of all available controller routes
public abstract class ControllerRoutes {

    // Main related Controllers
    public static final String HEALTH_CHECK = "/health";

    // Users related Controllers
    public static final String USER_GET = "/v1/users/{id}";

    public static final String USERS_GET = "/v1/users";
    public static final String USERS_GET_ROLES = "/v1/users/roles";

    public static final String USER_CREATE = "/v1/users";
    public static final String USER_DELETE = "/v1/users/{id}";
    public static final String USER_UPDATE = "/v1/users/{id}";

    public static final String AUTH_REGISTRATION = "/v1/auth/registration";
    public static final String AUTH_LOGIN = "/v1/auth/login";
    public static final String AUTH_LOGOUT = "/v1/auth/logout";
    public static final String AUTH_ME = "/v1/auth/me";
    
    // Documents related Controllers
    public static final String DOCUMENT_CREATE = "/v1/documents";
    public static final String DOCUMENT_UPDATE = "/v1/documents/{id}";
    public static final String DOCUMENT_DELETE = "/v1/documents/{id}";
    public static final String DOCUMENT_GET = "/v1/documents/{id}";
    public static final String DOCUMENTS_ARCHIVE = "/v1/documents/{id}/archive";
    public static final String DOCUMENTS_RESTORE = "/v1/documents/{id}/restore";
    public static final String DOCUMENT_BY_KEY = "/v1/documents/key/{key}";
    public static final String DOCUMENT_BY_PARENT = "/v1/documents/user/{userId}/parent/{parentDocumentId}";
    public static final String DOCUMENTS_FOR_USER = "/v1/documents/user/{id}";
    public static final String DOCUMENT_GET_TRASH = "/v1/documents/user/{id}/trash";
}
