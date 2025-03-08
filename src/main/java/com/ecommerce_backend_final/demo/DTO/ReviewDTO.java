package com.ecommerce_backend_final.demo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ReviewDTO {

    private Long id;
    private Long rating;
    private String description;
    private MultipartFile multipartimg;
    private byte[] returnedImg;

    private Long userid;
    private Long productid;
    private Long orderid;

    private String username;


}
