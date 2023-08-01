package hu.fazekas.pizzaking.service;

import hu.fazekas.pizzaking.dto.OrderDto;
import hu.fazekas.pizzaking.exception.NotFoundException;

public interface OrderService {

    OrderDto getOrderById(Long id) throws NotFoundException;

    Long createOrder(OrderDto orderDto) throws NotFoundException;

    OrderDto modifyOrder(Long id, OrderDto orderDto) throws NotFoundException;

    void deleteOrder(Long id) throws NotFoundException;
}
