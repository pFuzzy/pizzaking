package hu.fazekas.pizzaking.service;

import hu.fazekas.pizzaking.dto.PizzaDto;
import hu.fazekas.pizzaking.exception.NotFoundException;

public interface PizzaService {

    PizzaDto getPizzaById(Long id) throws NotFoundException;

    Long createPizza(PizzaDto pizzaDto);

    PizzaDto modifyPizza(Long id, PizzaDto pizzaDto) throws NotFoundException;

    void deletePizza(Long id) throws NotFoundException;
}
