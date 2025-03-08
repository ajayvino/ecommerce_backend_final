package com.ecommerce_backend_final.demo.Entity;


import com.ecommerce_backend_final.demo.DTO.CouponDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "coupontable",schema = "ecommerce")
@NoArgsConstructor
public class CouponEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"

    )
    @SequenceGenerator(
            name = "product_generator",
            schema = "ecommerce", // schema should be above the sequenceName
            sequenceName = "coupontable_ecommerce_sequence",
            allocationSize=1
    )
    private Long id;
    private String name;
    private String code;
    private Long discount;
    private Date expirationdate;


    public CouponDTO getCouponDTO(){
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setId(id);
        couponDTO.setName(name);
        couponDTO.setCode(code);
        couponDTO.setDiscount(discount);
        couponDTO.setExpirationdate(expirationdate);

        return couponDTO;
    }


}
