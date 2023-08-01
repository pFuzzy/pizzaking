package hu.fazekas.pizzaking;

import hu.fazekas.pizzaking.dto.PizzaDto;
import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.entity.Pizza;
import hu.fazekas.pizzaking.entity.User;
import hu.fazekas.pizzaking.mapper.PizzaMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PizzaMapperTest {

    @Autowired
    PizzaMapper pizzaMapper;

    @Test
    public void entityToDtoTest() {
        PizzaDto expectedPizzaDto = new PizzaDto().id(1L)
                .type("sonkás");
        Pizza pizza = new Pizza();

        pizza.setId(1L);
        pizza.setType("sonkás");

        Assert.assertEquals(expectedPizzaDto, pizzaMapper.entityToDto(pizza));
    }

    @Test
    public void dtoToEntityTest() {
        PizzaDto pizzaDto = new PizzaDto().id(1L).type("sonkás");

        Pizza mappedPizza = pizzaMapper.dtoToEntity(pizzaDto);

        Assert.assertEquals(mappedPizza.getId(), pizzaDto.getId());
        Assert.assertEquals(mappedPizza.getType(), pizzaDto.getType());

    }
}
