package org.example.orderservice.feignclient;

import org.example.orderservice.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public ProductDto getProductById(Long id) {
        ProductDto fallbackProduct = new ProductDto();
        fallbackProduct.setId(id);
        fallbackProduct.setName("Unknown Product");
        fallbackProduct.setPrice(0.0);
        return fallbackProduct;
    }
}
