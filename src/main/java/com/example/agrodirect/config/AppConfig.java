package com.example.agrodirect.config;

import com.example.agrodirect.models.dtos.AddProductDTO;
import com.example.agrodirect.models.dtos.UserRegistrationDTO;
import com.example.agrodirect.models.entities.Category;
import com.example.agrodirect.models.entities.Product;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.CategoryName;
import com.example.agrodirect.services.CategoryService;
import com.example.agrodirect.services.RoleService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {


    private final RoleService roleService;
    private final CategoryService categoryService;
    private final LoggedUserHelperService loggedUserHelperService;
    private final PasswordEncoder passwordEncoder;

    public AppConfig(RoleService roleService, CategoryService categoryService, LoggedUserHelperService loggedUserHelperService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.categoryService = categoryService;
        this.loggedUserHelperService = loggedUserHelperService;
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

//        //AddProductDTO -> Product
//        Converter<CategoryName, Category> categoryConverter = ctx ->
//                ctx.getSource() == null ? null : categoryService.getByName(ctx.getSource());
//
//        Provider<User> farmerProvider = req -> loggedUserHelperService.get();
//
//        modelMapper
//                .createTypeMap(AddProductDTO.class, Product.class)
//                .addMappings(mapper -> mapper
//                        .using(categoryConverter)
//                        .map(AddProductDTO::getCategory,Product::setCategory))
//                .addMappings(mapper -> mapper
//                        .when(Conditions.isNull())
//                        .with(farmerProvider)
//                        .map(AddProductDTO::getAuthor, Product::setFarmer));


        return modelMapper;


    }

}
