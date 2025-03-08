package com.ecommerce_backend_final.demo.Entity;

import com.ecommerce_backend_final.demo.DTO.WishlistDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
@Table(name = "wishlisttable")
public class WishlistEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"

    )
    @SequenceGenerator(
            name = "product_generator",
           // schema should be above the sequenceName
            sequenceName = "wishlisttable_ecommerce_sequence",
            allocationSize=1
    )
    private Long id;

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

    public WishlistDTO getWishlistDTO(){
        WishlistDTO wishlistDTO= new WishlistDTO();

        wishlistDTO.setId(id);
        wishlistDTO.setProductid(productEntity.getId());
        wishlistDTO.setUserid(userEntity.getId());
        wishlistDTO.setName(productEntity.getName());
        wishlistDTO.setImg(productEntity.getImg());
        wishlistDTO.setDescription(productEntity.getDescription());
        wishlistDTO.setPrice(productEntity.getPrice());
        wishlistDTO.setCategoryname(productEntity.getCategoryEntity().getName());
        return wishlistDTO;
    }
}
