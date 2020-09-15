package com.geekbrains.geekmarket.services;

import com.geekbrains.geekmarket.entities.*;
import com.geekbrains.geekmarket.repositories.OrderRepository;
import com.geekbrains.geekmarket.repositories.UserRepository;
import com.geekbrains.geekmarket.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartService {
    private ProductService productService;
    private OrderRepository orderRepository;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public ShoppingCart getCurrentCart(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void resetCart(HttpSession session) {
        session.removeAttribute("cart");
    }

    public void addToCart(HttpSession session, Long productId) {
        Product product = productService.getProductById(productId);
        addToCart(session, product);
    }

    public void addToCart(HttpSession session, Product product) {
        ShoppingCart cart = getCurrentCart(session);
        cart.add(product);
    }

    public void removeFromCart(HttpSession session, Long productId) {
        Product product = productService.getProductById(productId);
        removeFromCart(session, product);
    }

    public void removeFromCart(HttpSession session, Product product) {
        ShoppingCart cart = getCurrentCart(session);
        cart.remove(product);
    }

    public void setProductCount(HttpSession session, Long productId, Long quantity) {
        ShoppingCart cart = getCurrentCart(session);
        Product product = productService.getProductById(productId);
        cart.setQuantity(product, quantity);
    }

    public void setProductCount(HttpSession session, Product product, Long quantity) {
        ShoppingCart cart = getCurrentCart(session);
        cart.setQuantity(product, quantity);
    }

    public double getTotalCost(HttpSession session) {
        return getCurrentCart(session).getTotalCost();
    }

//    @Transactional
    public void save(HttpSession session) {
        ShoppingCart cart = getCurrentCart(session);
        User user = (User) session.getAttribute("user");
        List<Orders> orders = new ArrayList<>();
        Date date = new Date();
        for (OrderItem o : cart.getItems()) {
            Orders order = new Orders();
            order.setOrder_id(date);
            order.setUser(user);
            order.setProduct(o.getProduct());
            order.setQuantity(o.getQuantity());
            order.setItemPrice(o.getItemPrice());
            order.setTotalPrice(o.getTotalPrice());
            orders.add(order);
        }
        orderRepository.saveAll(orders);

    }
}
