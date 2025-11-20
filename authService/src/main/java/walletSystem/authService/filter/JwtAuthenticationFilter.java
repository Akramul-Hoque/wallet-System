package walletSystem.authService.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import walletSystem.authService.common.response.base.ErrorResponse;
import walletSystem.authService.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    // List of whitelisted paths that don't need authentication
    private static final List<String> WHITELISTED_PATHS = Arrays.asList(
            "/api/v1/user/swagger-ui.html",
            "/api/v1/user/swagger-ui/",
            "/api/v1/user/v3/api-docs",
            "/api/v1/user/v3/api-docs/",
            "/v3/api-docs",
            "/v3/api-docs/",
            "/swagger-ui/",
            "/swagger-ui.html",
            "/api/auth/login"
    );

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (isWhitelisted(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = extractToken(request);

        if (jwtToken == null || !jwtTokenUtil.validateToken(jwtToken)) {
            ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED, 401, "Invalid or missing token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getWriter(), error);
            return;

        }

        String username = jwtTokenUtil.extractUsername(jwtToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
    private boolean isWhitelisted(String path) {
        for (String whitelistedPath : WHITELISTED_PATHS) {
            if (path.startsWith(whitelistedPath)) {
                return true;
            }
        }
        return false;
    }

    // Extract JWT token from Authorization header
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extract the token part after "Bearer "
        }
        return null;
    }
}
