package com.ecommerce_backend_final.demo.Entity;


import com.ecommerce_backend_final.demo.DTO.CartItemsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "cartitemstable" , schema = "ecommerce")
@NoArgsConstructor
public class CartItemsEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"

    )
    @SequenceGenerator(
            name = "product_generator",
            schema = "ecommerce", // schema should be above the sequenceName
            sequenceName = "cartitems_ecommerce_sequence",
            allocationSize=1
    )
    private Long id;
    private Long price;
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "productid",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity productEntity;

    @Column(name = "productid",insertable=false, updatable=false)
    @JsonProperty("productid")
    private Long productid;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "userid",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @Column(name = "userid",insertable=false, updatable=false)
    @JsonProperty("userid")
    private Long userid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderid")
    private OrderEntity orderEntity;

    @Column(name = "orderid",insertable=false, updatable=false)
    @JsonProperty("orderid")
    private Long orderid;

    public CartItemsDTO getCartItemsDTO(){
        CartItemsDTO cartItemsDTO = new CartItemsDTO();

        cartItemsDTO.setId(id);
        cartItemsDTO.setPrice(price);
        cartItemsDTO.setProductname(productEntity.getName());
        cartItemsDTO.setProductid(productEntity.getId());
        cartItemsDTO.setUserid(userEntity.getId());
        cartItemsDTO.setReturnedImg(productEntity.getImg());
        cartItemsDTO.setQuantity(quantity);
        return cartItemsDTO;
    }






}
