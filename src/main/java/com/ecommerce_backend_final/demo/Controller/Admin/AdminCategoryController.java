package com.ecommerce_backend_final.demo.Controller.Admin;

import com.ecommerce_backend_final.demo.DTO.CategoryDTO;
import com.ecommerce_backend_final.demo.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO){

        CategoryDTO savecategoryDTO = categoryService.createCategoryDTO(categoryDTO);
        if(savecategoryDTO !=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(savecategoryDTO);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/allCategories")
    public ResponseEntity<?> getAllCategory(){

        return ResponseEntity.ok(categoryService.getallcategories());


    }




}
