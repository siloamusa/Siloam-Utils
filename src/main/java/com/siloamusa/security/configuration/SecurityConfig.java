package com.siloamusa.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import com.siloamusa.security.Filters.ApiKeyfilter;
import com.siloamusa.security.services.ApiKeyUserDetailsService;

import lombok.extern.slf4j.Slf4j;

 //Disabled Security until apigateway send api-key
 //@Configuration
 //@EnableWebSecurity
 //@EnableGlobalMethodSecurity(prePostEnabled = true)
 //@Order(1)
 @Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${api.key.header.name}")
    private String principalRequestHeader;

    @Value("${api.key.header.value}")
    private String principalRequestValue;

    private final ApiKeyUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ApiKeyfilter filter = new ApiKeyfilter(principalRequestHeader);
        filter.setAuthenticationManager(new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String principal = (String) authentication.getPrincipal();
                UserDetails userDetails = userDetailsService.loadUserByUsername(principal);
                if (!principalRequestValue.equals(principal)){
                    log.error("The API key was not found or not the expected value.");
                    throw new BadCredentialsException("The API key was not found or not the expected value.");
                }
                authentication.setAuthenticated(true);
                //return authentication;
                return new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities());
            }
        });
        httpSecurity.
            antMatcher("/api/**").
            csrf().disable().
            sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
            and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
    }


    public SecurityConfig(ApiKeyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


}