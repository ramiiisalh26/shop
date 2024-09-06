package com.example.shop.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {

    Boolean isCategoryExists(CategoryDTO categoryDTO);

    CategoryDTO save(CategoryDTO categoryDTO);

    Optional<CategoryDTO> findById(UUID id);

    List<CategoryDTO> getAllCategory();

    void deleteCategoryById(UUID id);
    
    CategoryDTO updateCategory(CategoryDTO categoryDTO, UUID id);
}
