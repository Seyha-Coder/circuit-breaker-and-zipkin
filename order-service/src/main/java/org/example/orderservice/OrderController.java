package org.example.orderservice;

import org.example.common.exception.CustomNotfoundException;
import org.example.orderservice.feignclient.ProductClient;
import org.example.orderservice.rest_template.OrderService;
import org.example.productservice.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;


    //use feign client
    @PostMapping
    public Order placeOrder(@RequestBody OrderRequest orderRequest) {
        // Fetch product details from product-service using Feign
        Product product;
        try {
            product = productClient.getProductById(orderRequest.getProductId());
        } catch (Exception e) {
            throw new CustomNotfoundException("Product with ID " + orderRequest.getProductId() + " not found");
        }

        // Calculate the total price
        double totalPrice = product.getPrice() * orderRequest.getQuantity();

        // Create and save the order
        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());

        return orderRepository.save(order);
    }

    //use rest template
//    @PostMapping("/create")
//    public Order createOrder(@RequestBody OrderRequest orderRequest) throws CustomNotfoundException {
//        Order order = orderService.createOrder(orderRequest);
//        return order;
//    }
}
