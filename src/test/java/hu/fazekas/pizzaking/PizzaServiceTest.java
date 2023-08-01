package hu.fazekas.pizzaking;

import hu.fazekas.pizzaking.dao.PizzaRepository;
import hu.fazekas.pizzaking.dto.PizzaDto;
import hu.fazekas.pizzaking.entity.Pizza;
import hu.fazekas.pizzaking.exception.AlreadyExistsException;
import hu.fazekas.pizzaking.exception.NotFoundException;
import hu.fazekas.pizzaking.service.PizzaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PizzaServiceTest {

    @Autowired
    PizzaService pizzaService;

    @MockBean
    PizzaRepository pizzaRepository;

    @Test
    public void getPizzaByIdTest_pizzaNotFound(){
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pizzaService.getPizzaById(1L));

        verify(pizzaRepository, times(1))
                .findById(1L);
    }

    @Test
    public void getPizzaByIdTest_success() throws NotFoundException {
        PizzaDto expectedPizzaDto = new PizzaDto().id(1L).type("sonkás");

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(1L);
        pizzaEntity.setType("sonkás");

        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizzaEntity));

        assertEquals(expectedPizzaDto, pizzaService.getPizzaById(pizzaEntity.getId()));

        verify(pizzaRepository, times(1))
                .findById(expectedPizzaDto.getId());
    }

    @Test
    public void createPizzaTest_success() {
        PizzaDto givenPizzaDto = new PizzaDto().type("gombás");

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(1L);
        pizzaEntity.setType("gombás");

        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizzaEntity);

        assertEquals(Optional.of(1L), Optional.ofNullable(pizzaService.createPizza(givenPizzaDto)));

        verify(pizzaRepository, times(1))
                .save(any(Pizza.class));
    }

    @Test
    public void modifyPizzaTest_pizzaNotFound(){
        PizzaDto givenPizzaDto = new PizzaDto().id(1L).type("sonkás");

        when(pizzaRepository.findById(givenPizzaDto.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pizzaService.modifyPizza(givenPizzaDto.getId(), givenPizzaDto));

        verify(pizzaRepository, times(1))
                .findById(givenPizzaDto.getId());
    }

    @Test
    public void modifyPizzaTest_success() throws AlreadyExistsException, NotFoundException {
        PizzaDto givenPizzaDto = new PizzaDto().id(1L).type("sonkás");

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(1L);
        pizzaEntity.setType("sonkás");

        when(pizzaRepository.findById(givenPizzaDto.getId())).thenReturn(Optional.of(pizzaEntity));
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(pizzaEntity);

        assertEquals(givenPizzaDto, pizzaService.modifyPizza(givenPizzaDto.getId(),  givenPizzaDto));

        verify(pizzaRepository, times(1))
                .findById(givenPizzaDto.getId());
        verify(pizzaRepository, times(1))
                .save(any(Pizza.class));
    }

    @Test
    public void deletePizzaTest_pizzaNotFound(){
        Long pizzaId = 1L;
        when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pizzaService.deletePizza(pizzaId));

        verify(pizzaRepository, times(1))
                .findById(1L);
    }

    @Test
    public void deleteUserTest_success() throws NotFoundException {
        Long pizzaId = 1L;

        Pizza pizzaEntity = new Pizza();
        pizzaEntity.setId(pizzaId);
        pizzaEntity.setType("sonkás");

        when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.of(pizzaEntity));

        pizzaService.deletePizza(pizzaEntity.getId());

        verify(pizzaRepository, times(1))
                .findById(1L);
        verify(pizzaRepository, times(1))
                .delete(pizzaEntity);
    }


}
