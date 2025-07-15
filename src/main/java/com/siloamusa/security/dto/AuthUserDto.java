package com.siloamusa.security.dto;

import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthUserDto  implements Serializable {
    private int id;
    private String userName;
    private String userPassword;
    private String environment;
    private String roles;
    private List<String> permissions;
    private String currentRefreshToken;
}
