package hu.fazekas.pizzaking.dto;

import lombok.Data;

@Data
public class PizzaDto {

    private Long id;
    private String type;

    public PizzaDto id(Long id){
        this.id = id;
        return this;
    }

    public PizzaDto type(String type){
        this.type = type;
        return this;
    }
}
