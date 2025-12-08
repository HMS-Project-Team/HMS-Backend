package com.example.hms.HMS.controllers;

import com.example.hms.HMS.services.RoleService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointBundle.ROLES)
public class RoleController {
    private final RoleService roleService;

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<Void>> deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);

        ResponseWrapper<Void> res =new ResponseWrapper<>();
        res.setStatusCode(2000);
        res.setStatusMessage("Role Deleted Successfully");
        res.setData(null);

        return ResponseEntity.ok(res);

    }

}
