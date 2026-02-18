package es.um.example.demo;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
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
        return authentication.getAuthorities().stream()
            .map(a -> a.getAuthority().equals(permission.toString()))
            .reduce(false, (a,b) -> a || b);
    }

    
}
