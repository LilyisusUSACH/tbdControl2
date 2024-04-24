package tbd.group3.control2.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbd.group3.control2.controllers.DTO.AuthLoginDTO;
import tbd.group3.control2.controllers.DTO.AuthRegisterDTO;
import tbd.group3.control2.controllers.DTO.AuthResponse;
import tbd.group3.control2.services.UserDetailServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginDTO authLoginDTO){
        return new ResponseEntity<AuthResponse>(userDetailService.loginUser(authLoginDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthRegisterDTO registerUser){
        return new ResponseEntity<>(this.userDetailService.createUser(registerUser), HttpStatus.CREATED);
    }

}
