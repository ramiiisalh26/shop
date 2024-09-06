package com.example.shop.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    
    private CategoryService categoryService;

    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody final CategoryDTO categoryDTO){
        
        System.out.println("Controller");
        final CategoryDTO savedCategory = categoryService.save(categoryDTO);

        return new ResponseEntity<CategoryDTO>(savedCategory,HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable final UUID id){
        Optional<CategoryDTO> foundedCategory = categoryService.findById(id);
        return foundedCategory
            .map(category -> new ResponseEntity<CategoryDTO>(category, HttpStatus.OK))
            .orElse(new ResponseEntity<CategoryDTO>(HttpStatus.NOT_FOUND)); 
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
        return new ResponseEntity<List<CategoryDTO>>(categoryService.getAllCategory(),HttpStatus.OK);
    } 

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable final UUID id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
