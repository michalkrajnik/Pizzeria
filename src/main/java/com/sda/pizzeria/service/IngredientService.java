package com.sda.pizzeria.service;


import com.sda.pizzeria.model.Ingredient;
import com.sda.pizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;





    public List<Ingredient> getAllIngredient(){
        return ingredientRepository.findAll();
    }



}
