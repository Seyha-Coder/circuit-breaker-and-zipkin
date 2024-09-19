package org.example.orderservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.example.orderservice.feignclient.ProductClient;
import org.example.common.exception.CustomNotfoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Qualifier("org.example.orderservice.feignclient.ProductClient")
    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderRepository orderRepository;

    @CircuitBreaker(name = "ProductClientgetProductByIdLong")
    @Retry(name = "ProductClientgetProductRetry", fallbackMethod = "getProductById")
    @RateLimiter(name = "ProductClientgetProductRateLimit", fallbackMethod = "getProductById")
    @Bulkhead(name = "ProductClientgetProductBulkhead", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "getProductFallback")
    public ProductDto getProductById(Long productId) {
        // Fetch product details using Feign client
        return productClient.getProductById(productId);
    }

    public OrderDto placeOrder(OrderRequest orderRequest) {
        ProductDto product = getProductById(orderRequest.getProductId());

        if (product == null) {
            throw new CustomNotfoundException("Product with ID " + orderRequest.getProductId() + " not found");
        }
        double totalPrice = product.getPrice() * orderRequest.getQuantity();

        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());

        orderRepository.save(order);
        return new OrderDto(order, product);
    }

    public OrderDto getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomNotfoundException("Order with ID " + id + " not found"));

        ProductDto product = getProductById(order.getProductId());
        return new OrderDto(order, product);
    }
}
