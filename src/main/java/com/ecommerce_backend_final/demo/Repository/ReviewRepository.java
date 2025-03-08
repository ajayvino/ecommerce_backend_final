package com.ecommerce_backend_final.demo.Repository;


import com.ecommerce_backend_final.demo.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {





    Optional<ReviewEntity> findByOrderidAndProductidAndUserid(Long orderid, Long productid, Long userid);

    List<ReviewEntity> findAllByProductid(Long productid);
}
