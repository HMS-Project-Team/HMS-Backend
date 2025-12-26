package com.example.hms.HMS.controllers;

import com.example.hms.HMS.utils.EndpointBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.CHILD_POLICIES)
@RequiredArgsConstructor
public class ChildPoliciesController {
}
