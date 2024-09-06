package com.example.shop.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    
    Boolean isProductExists(ProductDTO productDTO);

    ProductDTO save(ProductDTO productDTO);

    Optional<ProductDTO> findById(UUID id);

    List<ProductDTO> getAllProduct();

    void deleteProductById(UUID id);

    ProductDTO updateProduct(ProductDTO productDTO, UUID id);

}
