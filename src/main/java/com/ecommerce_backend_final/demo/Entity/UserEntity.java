package com.ecommerce_backend_final.demo.Entity;

import com.ecommerce_backend_final.demo.DTO.UserDTO;
import com.ecommerce_backend_final.demo.Enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usertable",schema = "ecommerce")
public class UserEntity {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"

    )
    @SequenceGenerator(
            name = "product_generator",
            schema = "ecommerce", // schema should be above the sequenceName
            sequenceName = "usertable_ecommerce_sequence",
            allocationSize=1
    )
    private Long id;

    private String email;
    private String password;
    private String name;
    private UserRole userRole;

    // Don't use @lob
    @Column(name="img", columnDefinition = "bytea")
    private byte[] img;

    public UserDTO getUserDTO(){
        UserDTO userDTO= new UserDTO();

        userDTO.setId(id);
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setUserRole(userRole);

        return userDTO;


    }






}
