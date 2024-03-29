package com.sda.pizzeria.repository;

import com.sda.pizzeria.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);

    Set<Ingredient> findAllByNameIn(Set<String> names);
}
