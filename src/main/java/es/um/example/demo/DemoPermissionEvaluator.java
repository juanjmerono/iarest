package es.um.example.demo;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class DemoPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, 
                                 Object targetDomainObject, 
                                 Object permission) {
        return hasPermission(authentication, 
                            (Serializable)targetDomainObject, 
                            "Module", 
                            permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, 
                                 Serializable targetId, 
                                 String targetType, 
                                 Object permission) {

        boolean hasRequestedPermission = authentication.getAuthorities().stream()
            .map(a -> a.getAuthority().equals(permission.toString()))
            .reduce(false, (a,b) -> a || b);
        
        boolean isUMUser = authentication.getPrincipal() 
                            instanceof Jwt jwt && jwt.getSubject().contains("@um.es"); 

        return hasRequestedPermission || isUMUser;
    }

    
}
