package org.example.orderservice.rest_template;

import org.example.orderservice.Order;
import org.example.orderservice.OrderRepository;
import org.example.orderservice.OrderRequest;
import org.example.productservice.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;
    @Autowired
    public OrderService(RestTemplate restTemplate, OrderRepository orderRepository) {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
    }

    public Product getProductDetails(Long productId) {
        String url = "http://localhost:8083/api/products/" + productId;  // Adjust port number as needed
        return restTemplate.getForObject(url, Product.class);
    }

    // Example method to create an order
    public Order createOrder(OrderRequest request) {
        Product product = getProductDetails(request.getProductId());
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
