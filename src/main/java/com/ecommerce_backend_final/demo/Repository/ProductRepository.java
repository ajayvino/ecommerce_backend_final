package com.ecommerce_backend_final.demo.Repository;


import com.ecommerce_backend_final.demo.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

   List<ProductEntity> findAllByNameContainingIgnoreCase(String name);
}
