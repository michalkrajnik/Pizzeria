package com.sda.pizzeria.service;

import com.sda.pizzeria.model.AppUser;
import com.sda.pizzeria.model.CartOrder;
import com.sda.pizzeria.model.Pizza;
import com.sda.pizzeria.model.UserCart;
import com.sda.pizzeria.model.dto.request.AppUserRequest;
import com.sda.pizzeria.model.dto.request.RegisterUserRequest;
import com.sda.pizzeria.repository.AppUserRepository;
import com.sda.pizzeria.repository.CartOrderRepository;
import com.sda.pizzeria.repository.UserCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserAuthenticationService appUserAuthenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private CartOrderRepository cartOrderRepository;

    @Autowired
    private UserCartRepository userCartRepository;


    public Optional<AppUser> register(RegisterUserRequest registerUserRequest) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(registerUserRequest.getUsername());

        if (optionalAppUser.isPresent()) {
            return Optional.empty();
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(registerUserRequest.getUsername());
        appUser.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        appUser.setRoles(userRoleService.getDefaultUserRoles());

        UserCart cart = new UserCart();
        cart = userCartRepository.save(cart);
        appUser.setUserCart(cart);

        return Optional.of(appUserRepository.save(appUser));

    }

    public Optional<UserCart> addPizzaToCart(Long pizzaId) {
        Optional<AppUser> loggedInUser = appUserAuthenticationService.getLoggedInUser();
        if (!loggedInUser.isPresent()) {
            return Optional.empty();
        }

        Optional<Pizza> optionalPizza = pizzaService.getPizzaWithId(pizzaId);
        if (!optionalPizza.isPresent()) {
            return Optional.empty();
        }

        AppUser appUser = loggedInUser.get();
        Pizza pizza = optionalPizza.get();

        UserCart cart = appUser.getUserCart();

        CartOrder order = new CartOrder();
        order.setPizza(pizza);
        order.setQuantity(1);

        cartOrderRepository.save(order);

        cart.getOrders().add(order);

        userCartRepository.save(cart);

        return Optional.of(cart);
    }


    public Optional<UserCart> updateCart(UserCart userCart) {
        Optional<AppUser> loggedInUser = appUserAuthenticationService.getLoggedInUser();
        if (!loggedInUser.isPresent()) {
            return Optional.empty();
        }
        UserCart originalCart = loggedInUser.get().getUserCart();

        for (int i = 0; i < originalCart.getOrders().size(); i++) {
            for (int j = 0; j < userCart.getOrders().size(); j++) {
                if (originalCart.getOrders().get(i).getId() == userCart.getOrders().get(j).getId()) {
                    originalCart.getOrders().get(i).setQuantity(userCart.getOrders().get(j).getQuantity());
                    break;
                }

            }
        }
        originalCart = userCartRepository.save(originalCart);
        return Optional.of(originalCart);
    }

    public Optional<UserCart> removeFromCart(Long orderId) {
        Optional<AppUser> loggedInUser = appUserAuthenticationService.getLoggedInUser();
        if (!loggedInUser.isPresent()) {
            return Optional.empty();
        }
        UserCart originalCart = loggedInUser.get().getUserCart();

        for (int i = 0; i < originalCart.getOrders().size(); i++) {
            if (originalCart.getOrders().get(i).getId() == orderId) ;
            CartOrder order = originalCart.getOrders().get(i);
            originalCart.getOrders().remove(order);

            cartOrderRepository.delete(order);
            break;
        }
        originalCart = userCartRepository.save(originalCart);

        return Optional.of(originalCart);
    }


    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();

    }

    public Optional<AppUser> removeAppUser(Long appUserId) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(appUserId);
        if (optionalAppUser.isPresent()) {
            AppUser originalAppUser = optionalAppUser.get();
            appUserRepository.delete(originalAppUser);
        }

        return optionalAppUser;
    }

    public void updateAppUser(AppUserRequest appUserRequest) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(appUserRequest.getAppUserId());
        if (optionalAppUser.isPresent()) {


            AppUser originalAppUser = optionalAppUser.get();

            if (appUserRequest.getAppUserId() != null) {
                originalAppUser.setId(appUserRequest.getAppUserId());
            }

            if (appUserRequest.getEditedUsername() != null) {
                originalAppUser.setUsername(appUserRequest.getEditedUsername());
            }
            appUserRepository.saveAndFlush(originalAppUser);


        }
    }

    public Optional<AppUser> findAppUserById(Long appUserId) {
        return appUserRepository.findById(appUserId);
    }
}

