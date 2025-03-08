package com.ecommerce_backend_final.demo.Controller.Auth;

import com.ecommerce_backend_final.demo.DTO.*;
import com.ecommerce_backend_final.demo.Service.AuthService;
import com.ecommerce_backend_final.demo.Service.OrderService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@NoArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        if (authService.hasUserWithEmail(signUpRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User Already Exist With The Provided Email");
        } else {
            UserDTO userDTO = authService.signup(signUpRequestDTO);

            if (userDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
            }


        }


    }

    @PostMapping("/login")
    public AuthenticationResponseDTO login(@RequestBody LoginDTO loginDTO){
        return authService.verify(loginDTO);
    }


    @GetMapping("/trackorder/{uuid}")
    public ResponseEntity<?> getorderstatus(@PathVariable UUID uuid){

        OrderDTO orderDTO = orderService.gettrackinngbyID(uuid);

        if(orderDTO !=null){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDTO);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
