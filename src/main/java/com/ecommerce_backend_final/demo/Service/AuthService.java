package com.ecommerce_backend_final.demo.Service;


import com.ecommerce_backend_final.demo.DTO.AuthenticationResponseDTO;
import com.ecommerce_backend_final.demo.DTO.LoginDTO;
import com.ecommerce_backend_final.demo.DTO.SignUpRequestDTO;
import com.ecommerce_backend_final.demo.DTO.UserDTO;
import com.ecommerce_backend_final.demo.Entity.OrderEntity;
import com.ecommerce_backend_final.demo.Entity.UserEntity;
import com.ecommerce_backend_final.demo.Enums.OrderStatus;
import com.ecommerce_backend_final.demo.Enums.UserRole;
import com.ecommerce_backend_final.demo.Repository.OrderRepository;
import com.ecommerce_backend_final.demo.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    private OrderRepository orderRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //@PostConstruct is used to run the function when the application starts running
    @PostConstruct
    private void createAdminAccount(){
        Optional<UserEntity> optionalUser = userRepository.findByUserRole(UserRole.ADMIN);

        if(optionalUser.isEmpty()){

            UserEntity newadmin= new UserEntity();
            newadmin.setName("admin");
            newadmin.setEmail("admin@gmail.com");
            newadmin.setPassword(new BCryptPasswordEncoder(12).encode("admin123"));
            newadmin.setUserRole(UserRole.ADMIN);
            userRepository.save(newadmin);

        }
        else{
            System.out.println("Admin Account Already Exists");
        }
    }


    public boolean hasUserWithEmail(String email) {

        UserEntity userEntity= userRepository.findByEmail(email);
        if(userEntity!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public UserDTO signup(SignUpRequestDTO signUpRequestDTO) {

        UserEntity userEntity = new UserEntity();
        userEntity.setName(signUpRequestDTO.getName());
        userEntity.setEmail(signUpRequestDTO.getEmail());
        userEntity.setPassword(new BCryptPasswordEncoder(12).encode(signUpRequestDTO.getPassword()));
        userEntity.setUserRole(UserRole.CUSTOMER);
        UserEntity createdUser= userRepository.save(userEntity);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAmount(0L);
        orderEntity.setTotalAmount(0L);
        orderEntity.setDiscount(0L);
        orderEntity.setUserEntity(createdUser);
        orderEntity.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(orderEntity);

        return createdUser.getUserDTO();



    }

    public AuthenticationResponseDTO verify(LoginDTO loginDTO) {

        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));


        if(authentication.isAuthenticated()){

            AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
            UserEntity userEntity= userRepository.findByEmail(loginDTO.getEmail());
            String jwt= jwtService.generatetoken(loginDTO.getEmail());

            authenticationResponseDTO.setId(userEntity.getId());
            authenticationResponseDTO.setJwt(jwt);
            authenticationResponseDTO.setEmail(userEntity.getEmail());
            authenticationResponseDTO.setUserRole(userEntity.getUserRole());
            authenticationResponseDTO.setUsername(userEntity.getName());

            return authenticationResponseDTO;
        }
        return null;
    }

}
