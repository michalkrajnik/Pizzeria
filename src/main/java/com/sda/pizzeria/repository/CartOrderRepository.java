package com.sda.pizzeria.repository;


import com.sda.pizzeria.model.CartOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartOrderRepository extends JpaRepository<CartOrder, Long> {
}
