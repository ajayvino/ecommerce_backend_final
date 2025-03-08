package com.ecommerce_backend_final.demo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class WishlistDTO {

    private Long id;
    private Long userid;
    private Long productid;
    private String name;
    private Long price;
    private String description;
    private byte[] img;    //Sending image to user as byte[] format
    private Long categoryid;
    private MultipartFile multipartimg;   // Getting image from user as MultipartFile
    private String categoryname;

}
