package com.mori5.itsecurity.security;

import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;
import com.mori5.itsecurity.errorhandling.exception.InvalidTokenException;
import com.mori5.itsecurity.errorhandling.exception.UserIsBannedException;
import com.mori5.itsecurity.repository.UserRepository;
import com.mori5.itsecurity.service.SecretService;
import com.mori5.itsecurity.service.impl.TokenServiceImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final byte[] secret;

    private final UserRepository userRepository;

    @Autowired
    public JwtTokenFilter(SecretService secretService, UserRepository userRepository) {
        this.secret = secretService.getHS512SecretBytes();
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = resolveToken(request);

        if (StringUtils.hasText(token)) {
            if (validateToken(token)) {
                Claims claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();

                Collection<? extends GrantedAuthority> authorities = Collections.singleton(
                        new SimpleGrantedAuthority("ROLE_" + claims.get(TokenServiceImpl.ROLE_CLAIM).toString())
                );

                String userId = claims.get(TokenServiceImpl.USER_ID_CLAIM).toString();

                AuthUserDetails userDetails = AuthUserDetails.builder()
                        .userId(userId)
                        .username(claims.getAudience())
                        .authorities(authorities)
                        .build();

                if (isUserBanned(userDetails)) {
                    throw new UserIsBannedException("User is banned!", ItSecurityErrors.USER_BANNED);
                }

                return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            } else {
                throw new InvalidTokenException("Access denied", ItSecurityErrors.ACCESS_DENIED);
            }
        }

        return null;
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }

        return null;
    }

    private boolean isUserBanned(AuthUserDetails userDetails) {
        Optional<User> optionalUser = userRepository.findByUsername(userDetails.getUsername());
        if (optionalUser.isEmpty()){
            return true;
        }

        return optionalUser.get().getIsBanned();
    }

    private boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace", e);
        }
        return false;
    }
}
