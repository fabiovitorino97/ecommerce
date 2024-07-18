package com.compassUol.ecommerce.models;

import com.compassUol.ecommerce.models.enums.Roles;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "permission")
public class Permission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(nullable = false, length = 180)
    private Roles description;

    public Permission() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Roles getDescription() {
        return description;
    }

    public void setDescription(Roles description) {
        this.description = description;
    }


    @Override
    public String getAuthority() {
        return this.description.toString();
    }
}
