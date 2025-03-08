package com.ecommerce_backend_final.demo.Service;


import com.ecommerce_backend_final.demo.DTO.FAQDTO;
import com.ecommerce_backend_final.demo.Entity.FAQEntity;
import com.ecommerce_backend_final.demo.Entity.ProductEntity;
import com.ecommerce_backend_final.demo.Repository.FAQRepository;
import com.ecommerce_backend_final.demo.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FAQService {

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private ProductRepository productRepository;

    public FAQDTO postFAQ(FAQDTO faqdto){

        Optional<ProductEntity> productEntity = productRepository.findById(faqdto.getProductid());

        if(productEntity.isPresent()){
            FAQEntity faqEntity = new FAQEntity();
            faqEntity.setQuestion(faqdto.getQuestion());
            faqEntity.setAnswer(faqdto.getAnswer());
            faqEntity.setProductEntity(productEntity.get());

            return faqRepository.save(faqEntity).getFAQDTO();
        }

        return null;
    }
}
