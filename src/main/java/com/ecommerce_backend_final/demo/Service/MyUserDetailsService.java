package com.ecommerce_backend_final.demo.Service;

import com.ecommerce_backend_final.demo.Entity.UserEntity;
import com.ecommerce_backend_final.demo.Entity.UserPrincipal;
import com.ecommerce_backend_final.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null){

            throw new UsernameNotFoundException("User Not Found");

        }
        return new UserPrincipal(userEntity);

    }
}
