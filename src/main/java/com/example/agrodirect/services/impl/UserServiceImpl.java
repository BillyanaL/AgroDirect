package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.UserViewDTO;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.repositories.UserRepository;
import com.example.agrodirect.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public boolean isUniqueEmail(String email) {
        return this.userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public List<UserViewDTO> getUsersByRole(UserRoles role) {
        return userRepository.findAll().stream()
                .filter(user -> !user.isDeleted())
                .filter(user -> user.getRoles().stream()
                        .anyMatch(r -> r.getName().equals(role)))
                .map(this::mapToUserViewDTO)
                .toList();
    }


    @Override
    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Потребителят не съществува.");
        }

        userRepository.deleteById(userId);
    }

    @Override
    public void markUserAsDeleted(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setDeleted(true);
        user.setEnabled(false);
        userRepository.save(user);
    }


    private UserViewDTO mapToUserViewDTO(User user) {
        String role = user.getRoles().stream()
                .map(r -> r.getName().name())
                .collect(Collectors.joining(", "));

        UserViewDTO dto = new UserViewDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFirstName() + " " + user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(role);
        dto.setEnabled(user.isEnabled());
        return dto;
    }

}
