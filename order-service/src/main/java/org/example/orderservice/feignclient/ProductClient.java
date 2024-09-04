package org.example.orderservice.feignclient;

import org.example.productservice.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8083/api/products")
public interface ProductClient {

    @GetMapping("/{id}")
    Product getProductById(@PathVariable("id") Long id);
}