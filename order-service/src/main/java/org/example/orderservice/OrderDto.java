package org.example.orderservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private int quantity;
    private double totalPrice;
    private Date orderDate;
    private ProductDto product;

    public OrderDto(Order order, ProductDto product) {
        this.id = order.getId();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.product = product;
    }
}
