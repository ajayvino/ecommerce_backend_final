package com.ecommerce_backend_final.demo.Entity;


import com.ecommerce_backend_final.demo.DTO.OrderDTO;
import com.ecommerce_backend_final.demo.Enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ordertable")
public class OrderEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"

    )
    @SequenceGenerator(
            name = "product_generator",
             // schema should be above the sequenceName
            sequenceName = "ordertable_ecommerce_sequence",
            allocationSize=1
    )
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

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(name = "userid",insertable=false, updatable=false)
    @JsonProperty("userid")
    private Long userid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "couponid", referencedColumnName = "id")
    private CouponEntity couponEntity;

    @Column(name = "couponid",insertable=false, updatable=false)
    @JsonProperty("couponid")
    private Long couponid;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderEntity")
    private List<CartItemsEntity> cartitems;


    public OrderDTO getOrderDTO(){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(id);
        orderDTO.setOrderdescription(orderdescription);
        orderDTO.setDate(date);
        orderDTO.setAmount(amount);
        orderDTO.setAddress(address);
        orderDTO.setPayment(payment);
        orderDTO.setOrderStatus(orderStatus);
        orderDTO.setTotalAmount(totalAmount);
        orderDTO.setDiscount(discount);
        orderDTO.setTrackingid(trackingid);
        orderDTO.setUsername(userEntity.getName());
        orderDTO.setUserid(userEntity.getId());
        orderDTO.setRazorpaytransactionid(razorpaytransactionid);
        orderDTO.setRazorpaysignature(razorpaysignature);

        if(couponEntity!=null){
            orderDTO.setCouponname(couponEntity.getName());
        }

        return orderDTO;


    }


}
