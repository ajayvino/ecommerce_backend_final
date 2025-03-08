package com.ecommerce_backend_final.demo.Repository;


import com.ecommerce_backend_final.demo.Entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CouponRepository extends JpaRepository<CouponEntity,Long> {

    Boolean existsByCode(String code);

    CouponEntity findByCode(String code);
}
