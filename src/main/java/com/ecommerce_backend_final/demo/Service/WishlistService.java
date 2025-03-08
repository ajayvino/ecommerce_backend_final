package com.ecommerce_backend_final.demo.Service;


import com.ecommerce_backend_final.demo.DTO.WishlistDTO;
import com.ecommerce_backend_final.demo.Entity.ProductEntity;
import com.ecommerce_backend_final.demo.Entity.UserEntity;
import com.ecommerce_backend_final.demo.Entity.WishlistEntity;
import com.ecommerce_backend_final.demo.Repository.ProductRepository;
import com.ecommerce_backend_final.demo.Repository.UserRepository;
import com.ecommerce_backend_final.demo.Repository.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> savewishlist(WishlistDTO wishlistDTO){

        Optional<ProductEntity> productEntity = productRepository.findById(wishlistDTO.getProductid());
        Optional<UserEntity> userEntity = userRepository.findById(wishlistDTO.getUserid());

        if(productEntity.isPresent() && userEntity.isPresent()){

            Optional<WishlistEntity> wishlistEntity = wishlistRepository.findByProductidAndUserid(wishlistDTO.getProductid(),wishlistDTO.getUserid());

            if(wishlistEntity.isPresent()){
                wishlistRepository.deleteById(wishlistEntity.get().getId());

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);

            }
            else{
                WishlistEntity wishlistEntity1 = new WishlistEntity();
                wishlistEntity1.setProductEntity(productEntity.get());
                wishlistEntity1.setUserEntity(userEntity.get());



                WishlistDTO wishlistDTO1=  wishlistRepository.save(wishlistEntity1).getWishlistDTO();
                return ResponseEntity.status(HttpStatus.CREATED).body(wishlistDTO1);
            }

        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }

    public List<WishlistDTO> getwishlistbyuserid(Long userid) {



        return wishlistRepository.findAllByUserid(userid).stream().map(WishlistEntity::getWishlistDTO).toList();
    }
}
