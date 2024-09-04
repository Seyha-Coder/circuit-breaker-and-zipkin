package org.example.studentservice;

import org.example.cardservice.Card;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "card-service", url = "http://localhost:8084")
public interface CardClient {

    @GetMapping("/api/cards/{id}")
    Card getCardById(@PathVariable("id") Long id);
    @DeleteMapping("/api/cards/{id}")
    void deleteCardById(@PathVariable("id") Long id);
}
