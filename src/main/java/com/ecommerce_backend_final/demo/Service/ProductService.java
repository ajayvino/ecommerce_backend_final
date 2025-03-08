package com.ecommerce_backend_final.demo.Service;

import com.ecommerce_backend_final.demo.DTO.FAQDTO;
import com.ecommerce_backend_final.demo.DTO.ProductDTO;
import com.ecommerce_backend_final.demo.DTO.ProductDetailDTO;
import com.ecommerce_backend_final.demo.DTO.ReviewDTO;
import com.ecommerce_backend_final.demo.Entity.CategoryEntity;
import com.ecommerce_backend_final.demo.Entity.FAQEntity;
import com.ecommerce_backend_final.demo.Entity.ProductEntity;
import com.ecommerce_backend_final.demo.Entity.ReviewEntity;
import com.ecommerce_backend_final.demo.Repository.CategoryRepository;
import com.ecommerce_backend_final.demo.Repository.FAQRepository;
import com.ecommerce_backend_final.demo.Repository.ProductRepository;
import com.ecommerce_backend_final.demo.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    public ProductDTO addProduct(ProductDTO productDTO) throws IOException {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setImg(productDTO.getMultipartimg().getBytes());

        CategoryEntity categoryEntity = categoryRepository.findById(productDTO.getCategoryid()).orElse(null);

        productEntity.setCategoryEntity(categoryEntity);

        return productRepository.save(productEntity).getProductDTO();


    }

    public List<ProductDTO> getAllProducts(){
        return productRepository.findAll().stream().map(ProductEntity::getProductDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByName(String name) {

        return productRepository.findAllByNameContainingIgnoreCase(name).stream().map(ProductEntity::getProductDTO).collect(Collectors.toList());
    }

    public void deleteProduct(Long id) {

        ProductEntity findproduct = productRepository.findById(id).orElse(null);

        if(findproduct!=null){
            productRepository.deleteById(id);
        }
    }

    public ProductDTO getProductById(Long id) {

        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if(productEntity.isPresent()){
            return productEntity.get().getProductDTO();
        }
        else{
            return null;
        }


    }

    public ProductDTO updateProduct(Long productid, ProductDTO productDTO) throws IOException {
        Optional<ProductEntity>  productEntity = productRepository.findById(productid);
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(productDTO.getCategoryid());

        if(productEntity.isPresent() && categoryEntity.isPresent()){
            ProductEntity productEntity1 = productEntity.get();
            productEntity1.setName(productDTO.getName());
            productEntity1.setPrice(productDTO.getPrice());
            productEntity1.setDescription(productDTO.getDescription());
            productEntity1.setCategoryEntity(categoryEntity.get());

            if(productDTO.getMultipartimg()!=null){
                productEntity1.setImg(productDTO.getMultipartimg().getBytes());
            }

            return productRepository.save(productEntity1).getProductDTO();

        }
        else{
            return null;
        }
    }

    public ProductDetailDTO getProductDetailDTO(Long productid){

        ProductEntity productEntity = productRepository.findById(productid).orElse(null);

        if(productEntity!=null){
            List<FAQDTO> faqdtoList = faqRepository.findAllByProductid(productEntity.getId()).stream().map(FAQEntity::getFAQDTO).toList();
            List<ReviewDTO> reviewDTOList = reviewRepository.findAllByProductid(productEntity.getId()).stream().map(ReviewEntity::getReviewDTO).toList();
             ProductDetailDTO productDetailDTO = new ProductDetailDTO();
             productDetailDTO.setProductDTO(productEntity.getProductDTO());
             productDetailDTO.setFaqdtoList(faqdtoList);
             productDetailDTO.setReviewDTOList(reviewDTOList);
             return productDetailDTO;
        }
        else{
            return null;
        }

    }
}
