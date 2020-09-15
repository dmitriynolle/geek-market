package com.geekbrains.geekmarket.repositories;

import com.geekbrains.geekmarket.entities.Orders;
import com.geekbrains.geekmarket.entities.User;
import com.geekbrains.geekmarket.utils.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orders, Long> {

}
