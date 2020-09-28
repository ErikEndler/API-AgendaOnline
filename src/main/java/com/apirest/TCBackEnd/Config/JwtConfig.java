package com.apirest.TCBackEnd.Config;

public class JwtConfig {
    public static final String SECRET_KEY = "solitario";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final long TOKEN_EXPIRATION_TIME = 8640000;

}
