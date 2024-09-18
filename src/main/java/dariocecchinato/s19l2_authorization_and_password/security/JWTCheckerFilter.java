package dariocecchinato.s19l2_authorization_and_password.security;

import dariocecchinato.s19l2_authorization_and_password.entities.Dipendente;
import dariocecchinato.s19l2_authorization_and_password.exceptions.UnauthorizedException;
import dariocecchinato.s19l2_authorization_and_password.services.DipendentiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private DipendentiService dipendentiService;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader("Authorization");

        if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserisci il Token nell' Authentication");

        String accessToken = authenticationHeader.substring(7);

        jwtTools.verifyToken(accessToken);

        String id = jwtTools.extractIdFromToken(accessToken);
        Dipendente currentDipendente = this.dipendentiService.findDipendenteById(UUID.fromString(id));

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentDipendente,null,currentDipendente.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/authorizations/**", request.getServletPath());
    }
}