package com.sda.pizzeria.service;


import com.sda.pizzeria.model.Ingredient;
import com.sda.pizzeria.model.Pizza;
import com.sda.pizzeria.model.dto.request.AddIngredientRequest;
import com.sda.pizzeria.model.dto.request.AddPizzaRequest;
import com.sda.pizzeria.repository.IngredientRepository;
import com.sda.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;


    public Optional<Pizza> addPizza(AddPizzaRequest addPizzaRequest) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findByName(addPizzaRequest.getName());
        if (!pizzaOptional.isPresent()) {
            Pizza pizza = new Pizza();
            pizza.setName(addPizzaRequest.getName());

            return Optional.of(pizzaRepository.save(pizza));
        }
        return Optional.empty();
    }

    public Optional<Ingredient> addIngredient(AddIngredientRequest addIngredientRequest) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(addIngredientRequest.getName());
        if (!ingredientOptional.isPresent()) {
            return Optional.of(ingredientRepository.save(new Ingredient(null, addIngredientRequest.getName())));
        }
        return Optional.empty();
    }

    public List<Pizza> getPizzaList() {
        return pizzaRepository.findAll();
    }


}
