package com.sda.pizzeria.service;


import com.sda.pizzeria.model.Ingredient;
import com.sda.pizzeria.model.dto.request.IngredientRequest;
import com.sda.pizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;


    public List<Ingredient> getAllIngredient() {
        return ingredientRepository.findAll();
    }


    public Optional<Ingredient> addIngredient(IngredientRequest request) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findByName(request.getName());

        if (optionalIngredient.isPresent()) {
            return Optional.empty();
        }

        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());
        ingredient.setPrice(request.getPrice());

        return Optional.of(ingredientRepository.save(ingredient));
    }
}
