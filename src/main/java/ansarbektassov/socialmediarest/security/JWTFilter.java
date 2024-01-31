package ansarbektassov.socialmediarest.security;

import ansarbektassov.socialmediarest.services.PersonDetailsService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final PersonDetailsService personDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            if (!jwtToken.isBlank()) {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwtToken);

                    UserDetails userDetails = personDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails.getUsername(),
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null)
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch(JWTVerificationException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }

        filterChain.doFilter(request, response);


    }
}

