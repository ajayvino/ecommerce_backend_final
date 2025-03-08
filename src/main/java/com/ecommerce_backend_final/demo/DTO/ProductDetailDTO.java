package com.ecommerce_backend_final.demo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDetailDTO {

    private ProductDTO productDTO;

    private List<ReviewDTO> reviewDTOList;

    private List<FAQDTO> faqdtoList;
}
