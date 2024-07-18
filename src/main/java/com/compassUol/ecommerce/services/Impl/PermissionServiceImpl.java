package com.compassUol.ecommerce.services.Impl;

import com.compassUol.ecommerce.dtos.PermissionDTO;
import com.compassUol.ecommerce.exceptions.ListIsEmptyException;
import com.compassUol.ecommerce.mapper.PermissionMapper;
import com.compassUol.ecommerce.models.Permission;
import com.compassUol.ecommerce.repositories.PermissionRepository;
import com.compassUol.ecommerce.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    public List<PermissionDTO> getPermissions() {
        List<Permission> permissions = permissionRepository.findAll();

        if (permissions.isEmpty()) {
            throw new ListIsEmptyException("No permissions found on DB");
        }

        return permissions.stream().map(permissionMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public PermissionDTO create(PermissionDTO permissionDTO) {
        Permission permission = permissionMapper.convertToEntity(permissionDTO);
        permission = permissionRepository.save(permission);

        return permissionMapper.convertToDto(permission);
    }
}
