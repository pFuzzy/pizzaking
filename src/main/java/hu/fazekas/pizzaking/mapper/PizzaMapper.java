package hu.fazekas.pizzaking.mapper;

import hu.fazekas.pizzaking.dto.PizzaDto;
import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.entity.Pizza;
import hu.fazekas.pizzaking.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PizzaMapper {

    public PizzaDto entityToDto(Pizza pizza){
        return new PizzaDto().id(pizza.getId()).type(pizza.getType());
    }

    public Pizza dtoToEntity(PizzaDto pizzaDto){
        Pizza pizza = new Pizza();

        pizza.setId(pizzaDto.getId());
        pizza.setType(pizzaDto.getType());

        return pizza;
    }
}
