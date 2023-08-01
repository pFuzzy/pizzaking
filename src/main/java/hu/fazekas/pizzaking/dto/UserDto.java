package hu.fazekas.pizzaking.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String address;

    public UserDto id(Long id){
        this.id = id;
        return this;
    }

    public UserDto email(String email){
        this.email = email;
        return this;
    }

    public UserDto address(String address){
        this.address = address;
        return this;
    }
}
