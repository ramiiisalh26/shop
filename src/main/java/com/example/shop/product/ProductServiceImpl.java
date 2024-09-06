package com.example.shop.product;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.category.Category;
import com.example.shop.category.CategoryDTO;
import com.example.shop.category.CategoryMapper;
import com.example.shop.category.CategoryService;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepositry productRepositry;
    private CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(final ProductRepositry productRepositry,final CategoryService categoryService) {
        this.productRepositry = productRepositry;
        this.categoryService = categoryService;
    }

    @Override
    public Boolean isProductExists(ProductDTO productDTO) {
        return productRepositry.existsById(productDTO.getId());
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        if (productDTO == null) throw new RuntimeException("Product Must be provided");
                
        productDTO.setAdd_time_creation(Date.from(Instant.now()));
        // System.out.println(productDTO.getCategory().getName());
        String categoryName = productDTO.getCategory().getName();
        
        if (!categoryName.isEmpty()) {
            List<CategoryDTO> allCategories = categoryService.getAllCategory();
            List<CategoryDTO> foundedCategory = allCategories
                .stream()
                .filter(category -> category.getName().equals(categoryName))
                .collect(Collectors.toList());
            Category convertToCategory = CategoryMapper.fromDTOToEntity(foundedCategory.get(0));
            System.out.println(foundedCategory);
            System.out.println();
            productDTO.setCategory(convertToCategory);
        }
        System.out.println(productDTO);
        Product product = ProductMapper.fromDTOToEntity(productDTO);

        Product savedProduct = productRepositry.save(product);
        productDTO.setId(savedProduct.getId());

        return ProductMapper.fromEntityToDto(savedProduct);
    }


    @Override
    public Optional<ProductDTO> findById(UUID id) {
        Optional<Product> foundedProduct = productRepositry.findById(id);
        
        if(foundedProduct.isPresent()){
            return Optional.of(ProductMapper.fromEntityToDto(foundedProduct.get()));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepositry.findAll();
        List<ProductDTO> productDTOs = products.stream().map(product -> ProductMapper.fromEntityToDto(product)).collect(Collectors.toList());
        return productDTOs;
    }

    @Override
    public void deleteProductById(UUID id) {
        productRepositry.deleteById(id);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

}
