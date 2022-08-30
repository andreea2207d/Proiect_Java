package com.example.javaproject.service;

import com.example.javaproject.dto.ShoppingCartDto;
import com.example.javaproject.exception.ShoppingCartNotFound;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.mapper.ShoppingCartMapper;
import com.example.javaproject.model.ShoppingCart;
import com.example.javaproject.model.Clothing;
import com.example.javaproject.model.User;
import com.example.javaproject.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClothingService clothingService;

    public ShoppingCartDto addClothingToShoppingCart(Long userId, Long clothingId) throws ClothingNotFound, NoSuchAlgorithmException {
        User user = userService.findById(userId);
        Clothing clothing = clothingService.findById(clothingId);
        ShoppingCart shoppingCart = user.getShoppingCart();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        shoppingCart.setUser(user);
        user.setShoppingCart(shoppingCart);
        shoppingCart.getClothes().add(clothing);
        user.getShoppingCart().totalAmount();
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.mapToDto(savedShoppingCart);
    }

    public ShoppingCartDto findShoppingCartByUser(Long userId) throws ShoppingCartNotFound {
        ShoppingCart shoppingCart =  shoppingCartRepository.findShoppingCartByUserId(userId);
        if (shoppingCart == null)
            throw new ShoppingCartNotFound("ShoppingCart not found!");
        return shoppingCartMapper.mapToDto(shoppingCart);
    }

    public ShoppingCartDto deleteClothingFromShoppingCart(Long cartId, Long clothingId) throws ClothingNotFound, ShoppingCartNotFound {
        Clothing clothing = clothingService.findById(clothingId);
        ShoppingCart shoppingCart = findById(cartId);
        shoppingCart.getClothes().remove(clothing);
        shoppingCart.totalAmount();
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.mapToDto(savedShoppingCart);
    }

    public ShoppingCart findById(Long id) throws ShoppingCartNotFound {
        Optional<ShoppingCart> shoppingCart =  shoppingCartRepository.findById(id);
        if (shoppingCart.isEmpty())
            throw new ShoppingCartNotFound("ShoppingCart not found!");
        return shoppingCart.get();
    }

    @Transactional
    public void deleteShoppingCartByUserId(Long userId) throws ShoppingCartNotFound {
        User user = userService.findById(userId);
        ShoppingCart shoppingCart = findById(user.getShoppingCart().getId());
        user.setShoppingCart(null);
        shoppingCartRepository.delete(shoppingCart);
    }

}
