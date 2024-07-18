package com.compassUol.ecommerce.dtos;

import com.compassUol.ecommerce.models.Permission;

import java.util.List;

public record UserDTO(
        Long id,
        String username,
        String fullname,
        String password,
        String email,
        List<Permission> permissions
) {
}
