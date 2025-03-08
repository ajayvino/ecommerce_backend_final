package com.ecommerce_backend_final.demo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RazorpayTransactionDetailsDTO {

    private Long id;
    private String orderid;
    private String currency;
    private Integer amount;
    private String key;


}
