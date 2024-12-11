package com.arji.arji_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Byte id;

    @Column(name = "role_name")
    private UserRole roleName;

    public Role(UserRole roleName) {
        this.roleName = roleName;
    }
}
