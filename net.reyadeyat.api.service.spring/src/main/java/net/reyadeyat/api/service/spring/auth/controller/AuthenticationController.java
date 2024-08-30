package net.reyadeyat.api.service.spring.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import net.reyadeyat.api.library.authentication.Authentication;
import net.reyadeyat.api.library.authentication.Authenticator;
import net.reyadeyat.api.library.authorization.Authorization;
import net.reyadeyat.api.library.authorization.AuthorizedResource;
import net.reyadeyat.api.library.environment.ApiEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/net.reyadeyat.api/authentication")
public class AuthenticationController {
    
    @PostMapping("/authenticate")
    public ResponseEntity<Authorization> authenticate(HttpServletRequest request) {
        String api_jwt = request.getHeader(ApiEnvironment.getString(Authenticator.API_JWT_HEADER_NAME));
        Authentication authentication = new Authentication(api_jwt);
        if (authentication.isAuthentic()) {
            HashMap<String, AuthorizedResource> authorized_resource_map = new HashMap<>();
            Authorization authorization = new Authorization(authentication, authorized_resource_map, "Authentication successful");
            return ResponseEntity.status(200).body(authorization);
        } else {
            Authorization authorization = new Authorization(authentication, null, "Authentication failure");
            return ResponseEntity.status(401).body(authorization);
        }
    }
}
