package hu.fazekas.pizzaking.controller;

import hu.fazekas.pizzaking.dto.PizzaDto;
import hu.fazekas.pizzaking.exception.NotFoundException;
import hu.fazekas.pizzaking.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PizzaController {

    @Autowired
    PizzaService pizzaService;

    @GetMapping(value = "/pizza/{id}")
    public PizzaDto getPizzaById(@PathVariable Long id) throws NotFoundException {
        return pizzaService.getPizzaById(id);
    }

    @PostMapping(value = "/pizza")
    public Long createPizza(@RequestBody PizzaDto pizzaDto){
        return pizzaService.createPizza(pizzaDto);
    }

    @PutMapping(value = "/pizza/{id}")
    public PizzaDto modifyPizza(@PathVariable Long id, @RequestBody PizzaDto pizzaDto) throws NotFoundException {
        return pizzaService.modifyPizza(id, pizzaDto);
    }

    @DeleteMapping(value = "/pizza/{id}")
    public ResponseEntity<String> deletePizza(@PathVariable Long id) throws NotFoundException {
        pizzaService.deletePizza(id);

        return ResponseEntity.ok("Pizza deleted successfully");
    }
}
