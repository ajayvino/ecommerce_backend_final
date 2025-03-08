package com.ecommerce_backend_final.demo.DTO;

import com.ecommerce_backend_final.demo.Enums.PaymentMode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaceOrderDTO {

    private Long userid;
    private String address;
    private String description;
    private String payment;
    private String razorpay_payment_id;
    private String razorpay_signature;

}
