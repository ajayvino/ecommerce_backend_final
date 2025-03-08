package com.ecommerce_backend_final.demo.Controller.Customer;

import com.ecommerce_backend_final.demo.DTO.*;
import com.ecommerce_backend_final.demo.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemsService cartItemsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<?> getProductName(@PathVariable String name){
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductToCartDTO addProductToCartDTO){
        return cartItemsService.addProductToCart(addProductToCartDTO);

    }

    @GetMapping("/cart/{userid}")
    public ResponseEntity<?> getcartItemsbyUserId(@PathVariable Long userid){
        return ResponseEntity.ok(cartItemsService.getallcartitemsbyuserid(userid));
    }

    @GetMapping("/coupon/{userid}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userid,@PathVariable String code){

        OrderDTO orderDTO = cartItemsService.applyCoupon(userid,code);
        if(orderDTO!=null){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDTO);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/cart/delete/{userid}/{productid}")
    public ResponseEntity<?> deleteCart(@PathVariable Long userid, @PathVariable Long productid ){
        return cartItemsService.deleteCart(userid, productid);
    }

    @PostMapping("/cart/update")
    public ResponseEntity<?> updateQuantity(@RequestBody AddProductToCartDTO addProductToCartDTO){

        return ResponseEntity.ok(cartItemsService.updateQuantity(addProductToCartDTO));
    }

    @PostMapping("/cart/delete")
    public ResponseEntity<?> deleteQuantity(@RequestBody AddProductToCartDTO addProductToCartDTO){

        return ResponseEntity.ok(cartItemsService.deleteQuantity(addProductToCartDTO));
    }

    @PostMapping("/cart/placeorder")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){

        return ResponseEntity.ok(cartItemsService.placeOrder(placeOrderDTO));
    }

    @GetMapping("/orders/{userid}")
    public ResponseEntity<?> getAllOrdersByUserid(@PathVariable Long userid){

        return ResponseEntity.ok(orderService.getallordersbyuserid(userid));
    }

    @GetMapping("/order/{orderid}")
    public ResponseEntity<?> getAllOrdersByOrderid(@PathVariable Long orderid){

        return ResponseEntity.ok(cartItemsService.getallcartitemsbyorderid(orderid));
    }

    @PostMapping("/createreview")
    public ResponseEntity<?> saveReview(@ModelAttribute ReviewDTO reviewDTO) throws IOException {
        ReviewDTO reviewDTO1 = reviewService.saveReview(reviewDTO);

        if(reviewDTO1!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewDTO1);

        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getProductDetails/{productid}")
    public ResponseEntity<?> getProductDetails(@PathVariable Long productid){
        ProductDetailDTO productDetailDTO = productService.getProductDetailDTO(productid);
        if(productDetailDTO!=null){
            return ResponseEntity.status(HttpStatus.OK).body(productDetailDTO);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productDetailDTO);
        }
    }

    @PostMapping("/savewishlist")
    public ResponseEntity<?> addWishlist(@RequestBody WishlistDTO wishlistDTO){

        return wishlistService.savewishlist(wishlistDTO);
    }

    @GetMapping("/getwishlistbyuserid/{userid}")
    public ResponseEntity<?> getwishlistbyuserid(@PathVariable Long userid){

        List<WishlistDTO> wishlistDTOS = wishlistService.getwishlistbyuserid(userid);

        return ResponseEntity.status(HttpStatus.OK).body(wishlistDTOS);


    }


}
