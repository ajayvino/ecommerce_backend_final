package com.ecommerce_backend_final.demo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FAQDTO {

    private Long id;
    private String question;
    private String answer;
    private Long productid;
}
