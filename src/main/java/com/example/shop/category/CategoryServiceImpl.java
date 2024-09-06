package com.example.shop.category;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepositry categoryRepositry;

    public CategoryServiceImpl(CategoryRepositry categoryRepositry) {
        this.categoryRepositry = categoryRepositry;
    }

    @Override
    public Boolean isCategoryExists(CategoryDTO categoryDTO) {
        return categoryRepositry.existsById(categoryDTO.getId());
    }


    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        
        if (categoryDTO == null) throw new RuntimeException("Category Must be provided");

        categoryDTO.setCategory_creation_date(Date.from(Instant.now()));

        Category category = CategoryMapper.fromDTOToEntity(categoryDTO);
        
        // Save products item

        Category savedCategory = categoryRepositry.save(category);
        System.out.println("Hey.....");
        return CategoryMapper.fromEntityToDto(savedCategory);
    }


    @Override
    public Optional<CategoryDTO> findById(UUID id) {
        Optional<Category> category = categoryRepositry.findById(id);

        if (category.isPresent()) {
            return Optional.of(CategoryMapper.fromEntityToDto(category.get()));
        }else{
            return Optional.empty();
        }
    }


    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepositry.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> CategoryMapper.fromEntityToDto(category)).collect(Collectors.toList());
        return categoryDTOs;
    }


    @Override
    public void deleteCategoryById(UUID id) {
        categoryRepositry.deleteById(id);
    }


    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCategory'");
    }
    
}
