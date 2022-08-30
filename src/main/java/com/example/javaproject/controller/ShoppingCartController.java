package com.example.javaproject.controller;

import com.example.javaproject.dto.ShoppingCartDto;
import com.example.javaproject.exception.ShoppingCartNotFound;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.exception.UserNotFound;
import com.example.javaproject.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/shop/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add/{userId}/{clothingId}")
    public ResponseEntity<ShoppingCartDto> addClothingToShoppingCart(@PathVariable Long userId, @PathVariable Long clothingId) throws ClothingNotFound, NoSuchAlgorithmException {
        return ResponseEntity
                .ok()
                .body(shoppingCartService.addClothingToShoppingCart(userId, clothingId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartDto> findShoppingCartByUser(@PathVariable Long userId) throws ShoppingCartNotFound {
        return ResponseEntity
                .ok()
                .body(shoppingCartService.findShoppingCartByUser(userId));
    }

    @PutMapping("/delete/{cartId}/{clothingId}")
    public ResponseEntity<ShoppingCartDto> deleteClothingFromShoppingCart(@PathVariable Long cartId, @PathVariable Long clothingId) throws UserNotFound, ClothingNotFound, ShoppingCartNotFound {
        return ResponseEntity
                .ok()
                .body(shoppingCartService.deleteClothingFromShoppingCart(cartId, clothingId));
    }

}
