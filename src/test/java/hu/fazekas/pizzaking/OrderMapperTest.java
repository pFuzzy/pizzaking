package hu.fazekas.pizzaking;

import hu.fazekas.pizzaking.dto.OrderDto;
import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.entity.Order;
import hu.fazekas.pizzaking.entity.Pizza;
import hu.fazekas.pizzaking.entity.User;
import hu.fazekas.pizzaking.mapper.OrderMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void entityToDtoTest() {
        OrderDto expectedOrderDto = new OrderDto().id(1L)
                .userId(1L)
                .pizzaId(1L);

        Order order = new Order();

        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.hu");
        user.setAddress("test address");

        Pizza pizza = new Pizza();
        pizza.setId(1L);
        pizza.setType("sonkás");

        order.setId(1L);
        order.setUser(user);
        order.setPizza(pizza);

        Assert.assertEquals(expectedOrderDto,orderMapper.entityToDto(order));

    }
}
