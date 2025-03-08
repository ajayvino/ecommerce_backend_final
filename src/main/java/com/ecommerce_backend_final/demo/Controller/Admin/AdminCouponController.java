package com.ecommerce_backend_final.demo.Controller.Admin;

import com.ecommerce_backend_final.demo.DTO.CouponDTO;
import com.ecommerce_backend_final.demo.Service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/coupon")
public class AdminCouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/create")
    public ResponseEntity<?> createCoupon(@RequestBody CouponDTO couponDTO){
        CouponDTO couponDTO1 = couponService.createCoupon(couponDTO);
        if(couponDTO1 == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(couponDTO1);
    }

    @GetMapping("/getAllCoupons")
    public ResponseEntity<List<CouponDTO>>  getAllCoupons(){

        return ResponseEntity.ok(couponService.getallcoupons());
    }
}
