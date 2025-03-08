package com.ecommerce_backend_final.demo.Service;


import com.ecommerce_backend_final.demo.DTO.CategoryDTO;
import com.ecommerce_backend_final.demo.Entity.CategoryEntity;
import com.ecommerce_backend_final.demo.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO createCategoryDTO(CategoryDTO categoryDTO){
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(categoryEntity).getcategoryDTO();
    }


    public List<CategoryDTO> getallcategories() {

        return categoryRepository.findAll().stream().map(CategoryEntity ::getcategoryDTO).collect(Collectors.toList());
    }
}
