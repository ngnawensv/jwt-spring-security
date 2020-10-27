package cm.belrose.jwtspringsecurity.security;

public class SecurityConstants {
    public static final String SECRET="admin@admin.cm";
    public static final long EXPIRATION_TIME=864_000_000;//10 jours
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
}
