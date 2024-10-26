package dev.lynxie.webapi.config;

public abstract class ControllerRoutes {
    
    // Main related Controllers
    public static final String HEALTH_CHECK = "/";
    
    // Users related Controllers
    public static final String USER_GET = "/v1/users/{id}";
    
    public static final String USERS_GET = "/v1/users";
    public static final String USERS_GET_ROLES = "/v1/users/roles";
    
    public static final String USER_CREATE = "/v1/users";
    public static final String USER_DELETE = "/v1/users/{id}";
    public static final String USER_UPDATE = "/v1/users/{id}";
    
    public static final String AUTH_REGISTRATION = "/v1/auth/registration";
    public static final String AUTH_LOGIN = "/v1/auth/login";
    
    // Expense related Controllers
    public static final String EXPENSES_GET_ALL = "/v1/expenses";
    public static final String EXPENSES_CREATE = "/v1/expenses";
    public static final String EXPENSES_GET = "/v1/expenses/{id}";
    public static final String EXPENSES_UPDATE = "/v1/expenses/{id}";
    public static final String EXPENSES_DELETE = "/v1/expenses/{id}";
    
    // Category related Controllers
    public static final String CATEGORIES_GET_ALL = "/v1/categories";
    public static final String CATEGORIES_CREATE = "/v1/categories";
    public static final String CATEGORIES_GET = "/v1/categories/{id}";
    public static final String CATEGORIES_UPDATE = "/v1/categories/{id}";
    public static final String CATEGORIES_DELETE = "/v1/categories/{id}";

    // Report related Controllers
    public static final String REPORTS_MAIN =  "/v1/reports";
    public static final String REPORTS_GET_SUMMARY = "/v1/reports/summary";
    public static final String REPORTS_GET_MONTHLY = "/v1/reports/monthly";
    public static final String REPORTS_GET_CATEGORY = "/v1/reports/category";
    
    // Currency related Controllers
    public static final String CURRENCY_LIST =  "/v1/currencies";
    public static final String EXCHANGE_RATES =  "/v1/exchange-rates";
}
