package com.ecommerce_backend_final.demo.DTO;


import com.ecommerce_backend_final.demo.Enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private String orderdescription;
    private Date date;
    private Long amount; // Showing amount after discount
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingid;
    private String razorpaytransactionid;
    private String razorpaysignature;


    private String username;
    private String couponname;
    private Long userid;


    private List<CartItemsDTO> cartitems;



}
