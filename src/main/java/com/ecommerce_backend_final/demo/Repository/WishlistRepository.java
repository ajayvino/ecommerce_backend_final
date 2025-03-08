package com.ecommerce_backend_final.demo.Repository;
import com.ecommerce_backend_final.demo.Entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity,Long> {

    Optional<WishlistEntity> findByProductidAndUserid(Long productid, Long userid);

    List<WishlistEntity> findAllByUserid(Long userid);
}
