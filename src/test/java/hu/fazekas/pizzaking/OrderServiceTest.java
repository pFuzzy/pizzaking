package hu.fazekas.pizzaking;

import hu.fazekas.pizzaking.dao.OrderRepository;
import hu.fazekas.pizzaking.dao.PizzaRepository;
import hu.fazekas.pizzaking.dao.UserRepository;
import hu.fazekas.pizzaking.dto.OrderDto;
import hu.fazekas.pizzaking.dto.PizzaDto;
import hu.fazekas.pizzaking.entity.Order;
import hu.fazekas.pizzaking.entity.Pizza;
import hu.fazekas.pizzaking.entity.User;
import hu.fazekas.pizzaking.exception.NotFoundException;
import hu.fazekas.pizzaking.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    PizzaRepository pizzaRepository;

    @MockBean
    UserRepository userRepository;

    @Test
    public void getOrderByIdTest_orderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.getOrderById(1L));

        verify(orderRepository, times(1))
                .findById(1L);
    }

    @Test
    public void getOrderByIdTest_success() throws NotFoundException {
        OrderDto expectedOrderDto = new OrderDto().id(1L).pizzaId(1L).userId(1L);

        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(1L);
        pizzaEntity.setType("sonkás");

        Order orderEntity = new Order();
        orderEntity.setId(1L);
        orderEntity.setPizza(pizzaEntity);
        orderEntity.setUser(userEntity);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));

        assertEquals(expectedOrderDto, orderService.getOrderById(expectedOrderDto.getId()));

        verify(orderRepository, times(1))
                .findById(expectedOrderDto.getId());
    }

    @Test
    public void createOrderTest_orderNotFound() {
        OrderDto givenOrderDto = new OrderDto().id(1L).pizzaId(2L).userId(3L);

        when(userRepository.findById(givenOrderDto.getUserId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.createOrder(givenOrderDto));

        verify(userRepository, times(1))
                .findById(givenOrderDto.getUserId());

    }

    @Test
    public void createOrderTest_pizzaNotFound() {
        OrderDto givenOrderDto = new OrderDto().id(1L).pizzaId(2L).userId(3L);

        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        when(userRepository.findById(givenOrderDto.getUserId())).thenReturn(Optional.of(userEntity));
        when(pizzaRepository.findById(givenOrderDto.getPizzaId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.createOrder(givenOrderDto));

        verify(pizzaRepository, times(1))
                .findById(givenOrderDto.getPizzaId());
    }

    @Test
    public void createOrderTest_success() throws NotFoundException {
        OrderDto givenOrderDto = new OrderDto().pizzaId(2L).userId(3L);

        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(1L);
        pizzaEntity.setType("sonkás");

        Order orderEntity = new Order();
        orderEntity.setId(1L);
        orderEntity.setPizza(pizzaEntity);
        orderEntity.setUser(userEntity);

        when(userRepository.findById(givenOrderDto.getUserId())).thenReturn(Optional.of(userEntity));
        when(pizzaRepository.findById(givenOrderDto.getPizzaId())).thenReturn(Optional.of(pizzaEntity));
        when(orderRepository.save(any(Order.class))).thenReturn(orderEntity);

        assertEquals(Optional.of(1L), Optional.ofNullable(orderService.createOrder(givenOrderDto)));


        verify(userRepository, times(1))
                .findById(givenOrderDto.getUserId());
        verify(pizzaRepository, times(1))
                .findById(givenOrderDto.getPizzaId());
        verify(orderRepository, times(1))
                .save(any(Order.class));
    }

    @Test
    public void modifyOrderTest_orderNotFound(){
        OrderDto givenOrderDto = new OrderDto().id(1L).pizzaId(2L).userId(3L);

        when(orderRepository.findById(givenOrderDto.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.modifyOrder(givenOrderDto.getId(), givenOrderDto));

        verify(orderRepository, times(1))
                .findById(givenOrderDto.getId());
    }

    @Test
    public void modifyOrderTest_userNotFound(){
        OrderDto givenOrderDto = new OrderDto().id(1L).pizzaId(2L).userId(3L);

        User userEntity = new User();
        userEntity.setId(3L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(2L);
        pizzaEntity.setType("sonkás");

        Order orderEntity = new Order();
        orderEntity.setId(1L);
        orderEntity.setPizza(pizzaEntity);
        orderEntity.setUser(userEntity);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.modifyOrder(givenOrderDto.getId(), givenOrderDto));

        verify(orderRepository, times(1))
                .findById(givenOrderDto.getId());
        verify(userRepository, times(1))
                .findById(givenOrderDto.getUserId());
    }

    @Test
    public void modifyOrderTest_pizzaNotFound(){
        OrderDto givenOrderDto = new OrderDto().id(1L).pizzaId(2L).userId(3L);

        User userEntity = new User();
        userEntity.setId(3L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(2L);
        pizzaEntity.setType("sonkás");

        Order orderEntity = new Order();
        orderEntity.setId(1L);
        orderEntity.setPizza(pizzaEntity);
        orderEntity.setUser(userEntity);

        when(orderRepository.findById(givenOrderDto.getId())).thenReturn(Optional.of(orderEntity));
        when(userRepository.findById(givenOrderDto.getUserId())).thenReturn(Optional.of(userEntity));
        when(pizzaRepository.findById(givenOrderDto.getPizzaId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.modifyOrder(givenOrderDto.getId(), givenOrderDto));

        verify(orderRepository, times(1))
                .findById(givenOrderDto.getId());
        verify(userRepository, times(1))
                .findById(givenOrderDto.getUserId());
        verify(pizzaRepository, times(1))
                .findById(givenOrderDto.getPizzaId());

    }

    @Test
    public void modifyOrderTest_success() throws NotFoundException {
        OrderDto givenOrderDto = new OrderDto().id(1L).pizzaId(2L).userId(3L);

        User userEntity = new User();
        userEntity.setId(3L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(2L);
        pizzaEntity.setType("sonkás");

        Order orderEntity = new Order();
        orderEntity.setId(1L);
        orderEntity.setPizza(pizzaEntity);
        orderEntity.setUser(userEntity);

        when(orderRepository.findById(givenOrderDto.getId())).thenReturn(Optional.of(orderEntity));
        when(userRepository.findById(givenOrderDto.getUserId())).thenReturn(Optional.of(userEntity));
        when(pizzaRepository.findById(givenOrderDto.getPizzaId())).thenReturn(Optional.of(pizzaEntity));
        when(orderRepository.save(any(Order.class))).thenReturn(orderEntity);

        assertEquals(givenOrderDto, orderService.modifyOrder(givenOrderDto.getId(), givenOrderDto));

        verify(orderRepository, times(1))
                .findById(givenOrderDto.getId());
        verify(userRepository, times(1))
                .findById(givenOrderDto.getUserId());
        verify(pizzaRepository, times(1))
                .findById(givenOrderDto.getPizzaId());
        verify(orderRepository, times(1))
                .save(any(Order.class));
    }

    @Test
    public void deleteOrderTest_orderNotFound(){
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.deleteOrder(orderId));

        verify(orderRepository, times(1))
                .findById(1L);
    }

    @Test
    public void deleteOrderTest_success() throws NotFoundException {
        Long orderId = 1L;

        User userEntity = new User();
        userEntity.setId(3L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(2L);
        pizzaEntity.setType("sonkás");

        Order orderEntity = new Order();
        orderEntity.setId(orderId);
        orderEntity.setPizza(pizzaEntity);
        orderEntity.setUser(userEntity);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));

        orderService.deleteOrder(orderId);

        verify(orderRepository, times(1))
                .findById(1L);
        verify(orderRepository, times(1))
                .delete(orderEntity);
    }
}
