package com.example.agrodirect.services.helpers;

import com.example.agrodirect.exceptions.UserNotFoundException;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserHelperService {

    private final UserRepository userRepository;

    public LoggedUserHelperService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get() {
        String email = getEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " was not found"));
    }


    public boolean isLogged() {
        return !hasRole(UserRoles.ANONYMOUS);
    }

    public boolean isAdmin() {
        return hasRole(UserRoles.ADMIN);
    }

    public boolean isFarmer() {
        return hasRole(UserRoles.FARMER);
    }

    public boolean isOnlyUser() {
        return getAuthentication().getAuthorities().stream()
                .allMatch(role -> role.getAuthority().equals("ROLE_" + UserRoles.USER));
    }

    public String getEmail() {
        return getUserDetails().getUsername();
    }


    public boolean hasRole(UserRoles userRoles) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_" + userRoles));
    }

    private UserDetails getUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
