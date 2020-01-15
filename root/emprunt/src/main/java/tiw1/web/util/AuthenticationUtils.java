package tiw1.web.util;

import org.springframework.security.core.Authentication;

public abstract class AuthenticationUtils {

    public static final String ROLE_ADMIN = "ROLE_admin";

    /**
     * checks if the provided user has admin role.
     *
     * @param authentication
     * @return
     */
    public static Boolean isAdmin(Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(o -> o.getAuthority().equalsIgnoreCase(ROLE_ADMIN));
    }
}
