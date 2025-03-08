package com.ecommerce_backend_final.demo.Repository;


import com.ecommerce_backend_final.demo.Entity.CartItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartItemsRepository extends JpaRepository<CartItemsEntity,Long> {

    Optional<CartItemsEntity> findByProductidAndOrderidAndUserid(Long productid,Long orderid,Long userid);

   List<CartItemsEntity> findAllByOrderidOrderByIdAsc(Long orderid);


    CartItemsEntity findByUseridAndProductid(Long userid, Long productid);
}
