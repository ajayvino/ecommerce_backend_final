package com.ecommerce_backend_final.demo.DTO;


import com.ecommerce_backend_final.demo.Enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationResponseDTO {

    private Long id;
    private String jwt;
    private String email;
    private UserRole userRole;
    private String username;



}
