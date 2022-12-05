package com.nataly.hospital.security;

import com.nataly.hospital.exception.JwtAuthenticationException;
import com.nataly.hospital.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    @Value("${jwt.header}")
    private String authorisationHeader;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AccountService accountService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        try{
            if(token!=null&& jwtProvider.validateToken(token)){
                String userLogin = jwtProvider.getLoginFromToken(token);
                UserDetails userDetails = accountService.loadUserByUsername(userLogin);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (JwtAuthenticationException e){
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader(authorisationHeader);
        if(header!=null&&header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
