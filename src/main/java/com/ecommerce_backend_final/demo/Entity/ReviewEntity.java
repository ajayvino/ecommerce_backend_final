package com.ecommerce_backend_final.demo.Entity;


import com.ecommerce_backend_final.demo.DTO.ReviewDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ReviewTable",schema = "ecommerce")
public class ReviewEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"

    )
    @SequenceGenerator(
            name = "product_generator",
            schema = "ecommerce", // schema should be above the sequenceName
            sequenceName = "reviewtable_ecommerce_sequence",
            allocationSize=1
    )
    private Long id;


    private Long rating;

    @Column(name="description",length = 2000)
    private String description;

    @Column(name="img", columnDefinition = "bytea")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "userid",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @Column(name = "userid",insertable=false, updatable=false)
    @JsonProperty("userid")
    private Long userid;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "productid",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity productEntity;

    @Column(name = "productid",insertable=false, updatable=false)
    @JsonProperty("productid")
    private Long productid;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "orderid",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderEntity orderEntity;

    @Column(name = "orderid",insertable=false, updatable=false)
    @JsonProperty("orderid")
    private Long orderid;

    public ReviewDTO getReviewDTO(){
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId(id);
        reviewDTO.setRating(rating);
        reviewDTO.setDescription(description);
        reviewDTO.setReturnedImg(img);
        reviewDTO.setUserid(userEntity.getId());
        reviewDTO.setProductid(productEntity.getId());
        reviewDTO.setOrderid(orderEntity.getId());
        reviewDTO.setUsername(userEntity.getName());

        return reviewDTO;

    }

}
