package com.compassUol.ecommerce.controllers;

import com.compassUol.ecommerce.dtos.PermissionDTO;
import com.compassUol.ecommerce.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public List<PermissionDTO> findAll() {
        return permissionService.getPermissions();
    }

    @PostMapping
    public PermissionDTO create(@RequestBody PermissionDTO data) {
        return permissionService.create(data);
    }
}
