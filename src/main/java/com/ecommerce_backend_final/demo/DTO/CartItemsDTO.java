package com.ecommerce_backend_final.demo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemsDTO {

    private Long id;
    private String productname;
    private Long price;
    private Long productid;
    private Long userid;
    private Long quantity;
    private byte[] returnedImg;

}
