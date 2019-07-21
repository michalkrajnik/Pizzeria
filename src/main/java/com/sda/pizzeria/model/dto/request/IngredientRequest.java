package com.sda.pizzeria.model.dto.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientRequest {

private String name;
private boolean added;
private double price;


    public IngredientRequest(String name, boolean added) {
        this.name = name;
        this.added = added;
    }

    public IngredientRequest(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
