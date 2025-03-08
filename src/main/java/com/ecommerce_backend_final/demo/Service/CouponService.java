package com.ecommerce_backend_final.demo.Service;

import com.ecommerce_backend_final.demo.DTO.CouponDTO;
import com.ecommerce_backend_final.demo.Entity.CouponEntity;
import com.ecommerce_backend_final.demo.Repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;


    public CouponDTO createCoupon(CouponDTO couponDTO){

        String name = couponDTO.getCode();
        CouponEntity couponEntity = new CouponEntity();


        if(couponRepository.existsByCode(name)){
            return null;
        }
        else{
            couponEntity.setName(couponDTO.getName());
            couponEntity.setCode(couponDTO.getCode());
            couponEntity.setDiscount(couponDTO.getDiscount());
            couponEntity.setExpirationdate(couponDTO.getExpirationdate());
            return couponRepository.save(couponEntity).getCouponDTO();
        }
    }

    public List<CouponDTO> getallcoupons(){
        return couponRepository.findAll().stream().map(CouponEntity::getCouponDTO).collect(Collectors.toList());
    }
}
