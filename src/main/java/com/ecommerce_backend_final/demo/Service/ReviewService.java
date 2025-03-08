package com.ecommerce_backend_final.demo.Service;

import com.ecommerce_backend_final.demo.Entity.OrderEntity;
import com.ecommerce_backend_final.demo.Entity.ProductEntity;
import com.ecommerce_backend_final.demo.DTO.ReviewDTO;
import com.ecommerce_backend_final.demo.Entity.ReviewEntity;
import com.ecommerce_backend_final.demo.Entity.UserEntity;
import com.ecommerce_backend_final.demo.Repository.ProductRepository;
import com.ecommerce_backend_final.demo.Repository.UserRepository;
import com.ecommerce_backend_final.demo.Repository.OrderRepository;
import com.ecommerce_backend_final.demo.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;



    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public ReviewDTO saveReview(ReviewDTO reviewDTO) throws IOException {

        Optional<OrderEntity> activeorder = orderRepository.findById(reviewDTO.getOrderid());

        Optional<ProductEntity> activeproduct = productRepository.findById(reviewDTO.getProductid());

        Optional<UserEntity> activeuser = userRepository.findById(reviewDTO.getUserid());

        if(activeorder.isPresent() && activeproduct.isPresent() && activeuser.isPresent()){

            Optional<ReviewEntity> reviewEntity = reviewRepository.findByOrderidAndProductidAndUserid(activeorder.get().getId(),activeproduct.get().getId(),activeuser.get().getId());

            if(reviewEntity.isPresent()){
                return null;
            }
            else{
              ReviewEntity savereview = new ReviewEntity();
              savereview.setRating(reviewDTO.getRating());
              savereview.setDescription(reviewDTO.getDescription());
              savereview.setUserEntity(activeuser.get());
              savereview.setProductEntity(activeproduct.get());
              savereview.setOrderEntity(activeorder.get());
              savereview.setImg(reviewDTO.getMultipartimg().getBytes());
              return reviewRepository.save(savereview).getReviewDTO();
            }

        }
        else{
            return null;
        }




    }


}
