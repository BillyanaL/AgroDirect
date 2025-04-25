package com.example.agrodirect.models.entities;

import com.example.agrodirect.models.enums.UserRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserRoles name;

    public UserRoles getName() {
        return name;
    }

    public void setName(UserRoles role) {
        this.name = role;
    }
}
