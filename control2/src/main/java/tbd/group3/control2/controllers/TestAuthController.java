package tbd.group3.control2.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/method")
public class TestAuthController {
    @GetMapping("/")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/secured")
    public String helloSecured(){
        return "Hello World";
    }
}
