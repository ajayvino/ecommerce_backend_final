package com.ecommerce_backend_final.demo.Entity;


import com.ecommerce_backend_final.demo.DTO.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
@Table(name = "producttable")
public class ProductEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"

    )
    @SequenceGenerator(
            name = "product_generator",
             // schema should be above the sequenceName
            sequenceName = "producttable_ecommerce_sequence",
            allocationSize=1
    )
    private Long id;

    private String name;
    private Long price;

    @Column(name="description",length = 2000)
    private String description;

    @Column(name="img", columnDefinition = "bytea")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "categoryid",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CategoryEntity categoryEntity;

    @Column(name = "categoryid",insertable=false, updatable=false)
    @JsonProperty("categoryid")
    private Long categoryid;

    public ProductDTO getProductDTO(){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setImg(img);
        productDTO.setPrice(price);
        productDTO.setCategoryid(categoryEntity.getId());
        productDTO.setCategoryname(categoryEntity.getName());

        return productDTO;

    }

}
