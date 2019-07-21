package com.sda.pizzeria.controller;


import com.sda.pizzeria.model.AppUser;
import com.sda.pizzeria.model.Ingredient;
import com.sda.pizzeria.model.Pizza;
import com.sda.pizzeria.model.dto.request.AddPizzaRequest;
import com.sda.pizzeria.model.dto.request.AppUserRequest;
import com.sda.pizzeria.model.dto.request.IngredientRequest;
import com.sda.pizzeria.model.dto.request.IngredientsRequest;
import com.sda.pizzeria.service.AppUserService;
import com.sda.pizzeria.service.IngredientService;
import com.sda.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/admin/")
public class AdminController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;


    @GetMapping(path = "pizzaForm")
    public String getPizzaForm(Model model) {

        model.addAttribute("formObject", new AddPizzaRequest());
        return "admin/pizzaForm";
    }


    @PostMapping(path = "pizzaForm")
    public String sendNewPizza(Model model, AddPizzaRequest request) {
        Optional<Pizza> pizzaOptional = pizzaService.addPizza(request);
        if (pizzaOptional.isPresent()) {
            Pizza createdPizza = pizzaOptional.get();
            return "redirect:/admin/ingredients/" + createdPizza.getId();
        }
        model.addAttribute("message", "Could not add pizza");
        model.addAttribute("formObject", request);
        return "admin/pizzaForm";
    }

    @GetMapping(path = "/ingredients/{id}")
    public String getIngredientsForm(Model model, @PathVariable(name = "id") Long id) {
        IngredientsRequest request = new IngredientsRequest();
        request.setPizzaId(id);
        request.setIngredients(pizzaService.getAllIngredients()
                .stream()
                .map(ingredient -> new IngredientRequest(ingredient.getName(), false))
                .collect(Collectors.toList()));


        model.addAttribute("formObject", request);

        return "admin/ingredientsForm";
    }


    @PostMapping(path = "/ingredients/{id}")
    public String sendIngredients(Model model, IngredientsRequest request) {
        Optional<Pizza> optionalPizza = pizzaService.updateIngredients(request);

        return "redirect:/pizzas";
    }

    @GetMapping("/userlist")
    public String getUserList(Model model) {
        List<AppUser> users = appUserService.getAllUsers();

        model.addAttribute("user_list", users);

        return "admin/userlist";

    }

    @GetMapping(path = "/removeAppUser/{id}")
    public String removeFromCart(Model model, @PathVariable(name = "id") Long appUserId) {
        Optional<AppUser> appUserOptional = appUserService.removeAppUser(appUserId);
        if (appUserOptional.isPresent()) {
            return "redirect:/admin/userlist";
        }
        model.addAttribute("message", "Unsuccessful user remove");

        List<AppUser> appUserList = appUserService.getAllUsers();
        model.addAttribute("user_list", appUserList);

        return "admin/userlist";
    }


    @GetMapping(path = "/updateAppUser/{userId}")
    public String getUsertoEdit(Model model, @PathVariable(name = "userId")Long id){

       AppUserRequest appUserRequest = new AppUserRequest();
        appUserRequest.setAppUserId(id);
        appUserRequest.setEditedUsername(appUserRequest.getEditedUsername());

        model.addAttribute("userToEdit", appUserRequest);

        return "admin/userToEdit";
    }

    @PostMapping(path = "/updateAppUser/{userId}")
    public String sendUpdatedUser(AppUserRequest request) {
       appUserService.updateAppUser(request);

        return "redirect:/admin/userlist";
    }


    @GetMapping("/ingredientList")
    public String getIngredientList(Model model) {
        List<Ingredient> ingredients = ingredientService.getAllIngredient();

        model.addAttribute("ingredient_list", ingredients);

        return "admin/ingredientList";

    }


@GetMapping(path = "/addIngredient")
    public String getIngredientForm(Model model){


        model.addAttribute("formObject", new IngredientRequest());

        return "admin/ingredientForm";


}

    @PostMapping("/addIngredient")
    public String sendRegister(Model model, IngredientRequest request) {

        Optional<Ingredient> optionalIngredient = ingredientService.addIngredient(request);
        if (optionalIngredient.isPresent()) {
            return "redirect:/admin/ingredientList";
        }
        model.addAttribute("message", "Could not Add!");
        model.addAttribute("formObject", request);


        return "admin/ingredientList";
    }


}
