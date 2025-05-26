package eci.cvds.mod2.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import eci.cvds.mod2.modules.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {


    private final String SECRET_KEY = "supersecretpassword1234567891011121314";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String path = request.getRequestURI();

        List<String> openPaths = Arrays.asList(
                "/swagger-ui/index.html",
                "/swagger-ui",
                "/swagger-ui/",
                "/swagger-ui/**",
                "/v3/api-docs",
                "/swagger-resources/**",
                "/webjars/**"
        );

        if (openPaths.stream().anyMatch(path::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        if (header == null || !header.startsWith("Bearer ")) {
            throw new BadCredentialsException("Session error, no token");
        }

        String token = header.substring(7);

        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
            String id = jwt.getClaim("id").asString();
            String userName = jwt.getClaim("userName").asString();
            String email = jwt.getClaim("email").asString();
            String name = jwt.getClaim("name").asString();
            String role = jwt.getClaim("role").asString();
            String specialty = jwt.getClaim("specialty").asString();

            request.setAttribute("id", id);
            request.setAttribute("userName", userName);
            request.setAttribute("email", email);
            request.setAttribute("name", name);
            request.setAttribute("role", role);
            request.setAttribute("specialty", specialty);

            CustomUserDetails userDetails = new CustomUserDetails(id, userName, email, name, role, specialty);
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);


            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, List.of(authority)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (JWTVerificationException e) {
            throw new BadCredentialsException("Tu sesión ya expiró");
        }

        chain.doFilter(request, response);
    }
}
