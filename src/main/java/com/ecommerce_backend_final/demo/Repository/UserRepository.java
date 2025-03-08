package com.ecommerce_backend_final.demo.Repository;

import com.ecommerce_backend_final.demo.Entity.UserEntity;
import com.ecommerce_backend_final.demo.Enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByEmail(String email);
    Optional<UserEntity> findByUserRole(UserRole userRole);
}
