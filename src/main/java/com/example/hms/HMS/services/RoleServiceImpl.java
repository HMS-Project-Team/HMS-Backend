package com.example.hms.HMS.services;

import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));

        if (role.getUsers() != null) {
            role.getUsers().forEach(user -> user.getRoles().remove(role));
            role.getUsers().clear();
        }

        roleRepository.delete(role);
    }


}
