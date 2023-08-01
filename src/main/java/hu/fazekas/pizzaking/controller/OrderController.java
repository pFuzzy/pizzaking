package hu.fazekas.pizzaking.controller;

import hu.fazekas.pizzaking.dto.OrderDto;
import hu.fazekas.pizzaking.exception.NotFoundException;
import hu.fazekas.pizzaking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/order/{id}")
    public OrderDto getOrderById(@PathVariable Long id ) throws NotFoundException {
        return orderService.getOrderById(id);
    }

    @PostMapping(value = "/order")
    public Long createOrder(@RequestBody OrderDto orderDto) throws NotFoundException {
        return orderService.createOrder(orderDto);
    }

    @PutMapping(value = "/order/{id}")
    public OrderDto modifyOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) throws NotFoundException {
        return orderService.modifyOrder(id, orderDto);
    }

    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) throws NotFoundException {
        orderService.deleteOrder(id);

        return ResponseEntity.ok("Order deleted successfully");
    }

}
