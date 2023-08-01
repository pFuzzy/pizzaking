package hu.fazekas.pizzaking.mapper;

import hu.fazekas.pizzaking.dto.OrderDto;
import hu.fazekas.pizzaking.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDto entityToDto(Order order){
        return new OrderDto().id(order.getId()).
                userId(order.getUser().getId())
                .pizzaId(order.getPizza().getId());
    }
}
