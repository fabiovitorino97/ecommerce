package com.compassUol.ecommerce.mapper;

import com.compassUol.ecommerce.dtos.PermissionDTO;
import com.compassUol.ecommerce.models.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {
    public PermissionDTO convertToDto(Permission permission) {
        return new PermissionDTO(
                permission.getId(),
                permission.getDescription()
        );
    }

    public Permission convertToEntity(PermissionDTO permissionDTO) {
        Permission permission = new Permission();
        permission.setDescription(permissionDTO.description());

        return permission;
    }
}
