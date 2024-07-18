package com.compassUol.ecommerce.services;

import com.compassUol.ecommerce.dtos.PermissionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {
    List<PermissionDTO> getPermissions();
    PermissionDTO create(PermissionDTO permissionDTO);
}
