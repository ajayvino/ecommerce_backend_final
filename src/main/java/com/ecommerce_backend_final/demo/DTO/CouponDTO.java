package com.ecommerce_backend_final.demo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CouponDTO {

    private Long id;
    private String name;
    private String code;
    private Long discount;
    private Date expirationdate;
}
