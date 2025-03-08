package com.ecommerce_backend_final.demo.Entity;


import com.ecommerce_backend_final.demo.DTO.CategoryDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="categorytable",schema = "ecommerce")
public class CategoryEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"

    )
    @SequenceGenerator(
            name = "product_generator",
            schema = "ecommerce", // schema should be above the sequenceName
            sequenceName = "categorytable_ecommerce_sequence",
            allocationSize=1
    )
    private Long id;
    private String name;

    @Column(name="description",length = 2000)
    private String description;

    public CategoryDTO getcategoryDTO(){
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(id);
        categoryDTO.setName(name);
        categoryDTO.setDescription(description);

        return categoryDTO;


    }

}
