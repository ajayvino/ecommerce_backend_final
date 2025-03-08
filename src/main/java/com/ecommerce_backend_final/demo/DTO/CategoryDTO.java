package com.ecommerce_backend_final.demo.DTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;

}
