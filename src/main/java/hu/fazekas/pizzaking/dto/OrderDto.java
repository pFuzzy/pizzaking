package hu.fazekas.pizzaking.dto;

import lombok.Data;

@Data
public class OrderDto {

    private Long id;
    private Long userId;
    private Long pizzaId;

    public OrderDto id(Long id) {
        this.id = id;
        return this;
    }

    public OrderDto userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public OrderDto pizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
        return this;
    }
}
