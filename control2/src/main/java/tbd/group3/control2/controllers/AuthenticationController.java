package tbd.group3.control2.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbd.group3.control2.controllers.DTO.AuthLoginDTO;
import tbd.group3.control2.controllers.DTO.AuthResponse;
import tbd.group3.control2.services.UserDetailServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginDTO authLoginDTO){
        return new ResponseEntity<AuthResponse>(userDetailService.loginUser(authLoginDTO), HttpStatus.OK);
    }

}
