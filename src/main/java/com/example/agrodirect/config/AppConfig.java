package com.example.agrodirect.config;

import com.example.agrodirect.models.dtos.UserRegistrationDTO;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.services.RoleService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {


    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AppConfig(RoleService roleService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public ModelMapper modelMapper(){


        final ModelMapper modelMapper = new ModelMapper();

        //UserRegistrationDTO -> User
        Provider<User> newUserProvider = req -> {
            UserRegistrationDTO source = (UserRegistrationDTO) req.getSource();
            String selectedRole = source.getRole();
            return new User()
                    .setRoles(Set.of(roleService.getRoleByName(selectedRole)));
        };

        Converter<String, String> passwordConverter = ctx ->
                ctx.getSource() == null ? null : passwordEncoder.encode(ctx.getSource());

        modelMapper
                .createTypeMap(UserRegistrationDTO.class, User.class)
                .setProvider(newUserProvider)
                .addMappings(mapper -> mapper
                        .using(passwordConverter)
                        .map(UserRegistrationDTO::getPassword, User::setPassword));

        return modelMapper;


    }

}
