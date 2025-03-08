package com.ecommerce_backend_final.demo.Controller.Admin;

import com.ecommerce_backend_final.demo.DTO.FAQDTO;
import com.ecommerce_backend_final.demo.DTO.ProductDTO;
import com.ecommerce_backend_final.demo.Service.FAQService;
import com.ecommerce_backend_final.demo.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FAQService faqService;


    //@ModelAttribute is used because we use MultipartFile
    @PostMapping("/createProduct")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductDTO productDTO) throws IOException {
        ProductDTO saveproduct = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveproduct);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<?> getProductName(@PathVariable String name){
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/postFAQ")
    public ResponseEntity<?> addFAQ(@RequestBody FAQDTO faqdto) {
        FAQDTO faqdto1 = faqService.postFAQ(faqdto);
        if(faqdto1!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(faqdto1);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/productinfo/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){

        return ResponseEntity.ok(productService.getProductById(id));

    }

    @PutMapping("/updateProduct/{productID}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productID, @ModelAttribute ProductDTO productDTO) throws IOException {
        ProductDTO updateproduct = productService.updateProduct(productID,productDTO);

        if(updateproduct!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(updateproduct);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }



}
