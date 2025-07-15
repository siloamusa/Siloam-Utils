package com.siloamusa.security.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siloamusa.security.dto.AuthUserDto;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class UserService {

    public UserDetails loadUserByApiKey(String apiKey) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        AuthUserDto UserAuth = new AuthUserDto();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); 
		try {
			UserAuth = new ObjectMapper().readerFor(AuthUserDto.class).readValue((String) request.getHeader("UserAuth"));
		} catch (JsonProcessingException e1) {
			log.error("UserService.loadUserByApiKey Error : {} ", e1);
		}
        String[] roles = UserAuth.getRoles().split(",");
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return new org.springframework.security.core.userdetails.User(apiKey, "", authorities);
    }

    public boolean hasAccess(String scope){
        boolean access = false;
        
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        @SuppressWarnings("null")
        HttpServletRequest request = requestAttributes.getRequest();
        AuthUserDto UserAuth = new AuthUserDto();
		try {
			UserAuth = new ObjectMapper().readerFor(AuthUserDto.class).readValue((String) request.getHeader("UserAuth"));
            for (String permission : UserAuth.getPermissions()) {
                if(permission.trim().equals(scope.trim())){
                    access = true;
                    break;
                }
            }
        } catch (Exception e1) {
            log.error("UserService.hasAccess Error : {} ", e1);
		}

        if(!access){
            log.error("Invalid Scope (hasAccess): {} in {} ", scope, UserAuth);
            throw new BadCredentialsException("Has not access to called resource.");
        }

        return access;
    }
}
