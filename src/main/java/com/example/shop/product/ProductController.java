package com.example.shop.product;

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
@RequestMapping("/api/v1/product")
public class ProductController {

    private ProductService productService;
    
    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody final ProductDTO productDTO){

        final ProductDTO savedProduct = productService.save(productDTO);

        return new ResponseEntity<ProductDTO>(savedProduct,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id){
        
        Optional<ProductDTO> foundedProduct = productService.findById(id);

        return foundedProduct
                .map(product -> new ResponseEntity<ProductDTO>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        return new ResponseEntity<List<ProductDTO>>(productService.getAllProduct(),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable UUID id){
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
