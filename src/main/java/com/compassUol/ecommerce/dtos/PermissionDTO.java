package com.compassUol.ecommerce.dtos;

import com.compassUol.ecommerce.models.enums.Roles;

public record PermissionDTO(
        Long id,
        Roles description
) {
}
