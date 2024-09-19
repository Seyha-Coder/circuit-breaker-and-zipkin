package org.example.orderservice.rest_template;

import org.example.orderservice.Order;
import org.example.orderservice.OrderRepository;
import org.example.orderservice.OrderRequest;
import org.example.orderservice.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;

@Service
public class OrderServiceRest {

    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;
    @Autowired
    public OrderServiceRest(RestTemplate restTemplate, OrderRepository orderRepository) {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
    }

    public ProductDto getProductDetails(Long productId) {
        String url = "http://localhost:8083/api/products/" + productId;  // Adjust port number as needed
        return restTemplate.getForObject(url, ProductDto.class);
    }

    // Example method to create an order
    public Order createOrder(OrderRequest request) {
        ProductDto product = getProductDetails(request.getProductId());
        Order order = new Order();
        double totalPrice = product.getPrice() * request.getQuantity();
        order.setProductId(product.getId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        orderRepository.save(order);
        return order;
    }
}
