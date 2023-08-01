package hu.fazekas.pizzaking.service.impl;

import hu.fazekas.pizzaking.dao.OrderRepository;
import hu.fazekas.pizzaking.dao.PizzaRepository;
import hu.fazekas.pizzaking.dao.UserRepository;
import hu.fazekas.pizzaking.dto.OrderDto;
import hu.fazekas.pizzaking.entity.Order;
import hu.fazekas.pizzaking.entity.Pizza;
import hu.fazekas.pizzaking.entity.User;
import hu.fazekas.pizzaking.exception.NotFoundException;
import hu.fazekas.pizzaking.service.OrderService;
import hu.fazekas.pizzaking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @Override
    public OrderDto getOrderById(Long id) throws NotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found!"));

        return new OrderDto().id(order.getId())
                .userId(order.getUser().getId())
                .pizzaId(order.getPizza().getId());
    }

    @Override
    public Long createOrder(OrderDto orderDto) throws NotFoundException {
        Order order = new Order();

        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(() -> new NotFoundException("User not found!"));
        Pizza pizza = pizzaRepository.findById(orderDto.getPizzaId()).orElseThrow(() -> new NotFoundException("Pizza not found!"));

        order.setUser(user);
        order.setPizza(pizza);

        return orderRepository.save(order).getId();
    }

    @Override
    public OrderDto modifyOrder(Long id ,OrderDto orderDto) throws NotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found!"));

        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(() -> new NotFoundException("User not found!"));
        Pizza pizza = pizzaRepository.findById(orderDto.getPizzaId()).orElseThrow(() -> new NotFoundException("Pizza not found!"));

        order.setUser(user);
        order.setPizza(pizza);

        Order savedOrder = orderRepository.save(order);

        return new OrderDto().id(savedOrder.getId())
                .userId(orderDto.getUserId())
                .pizzaId(orderDto.getPizzaId());
    }

    @Override
    public void deleteOrder(Long id) throws NotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found!"));

        orderRepository.delete(order);
    }
}
