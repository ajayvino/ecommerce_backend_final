package com.ecommerce_backend_final.demo.Entity;


import com.ecommerce_backend_final.demo.DTO.FAQDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
@Table(name="FAQTable",schema = "ecommerce")
public class FAQEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"

    )
    @SequenceGenerator(
            name = "product_generator",
            schema = "ecommerce", // schema should be above the sequenceName
            sequenceName = "faqtable_ecommerce_sequence",
            allocationSize=1
    )
    private Long id;
    private String question;
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "productid",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity productEntity;

    @Column(name = "productid",insertable=false, updatable=false)
    @JsonProperty("productid")
    private Long productid;

    public FAQDTO getFAQDTO(){
        FAQDTO faqdto = new FAQDTO();

        faqdto.setId(id);
        faqdto.setAnswer(answer);
        faqdto.setQuestion(question);
        faqdto.setProductid(productEntity.getId());

        return faqdto;
    }


}
