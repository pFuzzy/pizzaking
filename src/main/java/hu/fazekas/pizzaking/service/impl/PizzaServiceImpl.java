package hu.fazekas.pizzaking.service.impl;

import hu.fazekas.pizzaking.dao.PizzaRepository;
import hu.fazekas.pizzaking.dto.PizzaDto;
import hu.fazekas.pizzaking.entity.Pizza;
import hu.fazekas.pizzaking.exception.NotFoundException;
import hu.fazekas.pizzaking.mapper.PizzaMapper;
import hu.fazekas.pizzaking.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    PizzaMapper pizzaMapper;

    @Override
    public PizzaDto getPizzaById(Long id) throws NotFoundException {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new NotFoundException("Pizza not found!"));

        return pizzaMapper.entityToDto(pizza);
    }

    @Override
    public Long createPizza(PizzaDto pizzaDto) {

        return pizzaRepository.save(pizzaMapper.dtoToEntity(pizzaDto)).getId();
    }

    @Override
    public PizzaDto modifyPizza(Long id, PizzaDto pizzaDto) throws NotFoundException {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new NotFoundException("Pizza not found!"));

        pizza.setType(pizzaDto.getType());

        Pizza savedPizza = pizzaRepository.save(pizza);

        return pizzaMapper.entityToDto(savedPizza);
    }

    @Override
    public void deletePizza(Long id) throws NotFoundException {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(() -> new NotFoundException("Pizza not found!"));

        pizzaRepository.delete(pizza);
    }
}
