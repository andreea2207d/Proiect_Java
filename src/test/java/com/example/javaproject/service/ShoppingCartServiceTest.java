package com.example.javaproject.service;

import com.example.javaproject.dto.ShoppingCartDto;
import com.example.javaproject.dto.UserDto;
import com.example.javaproject.exception.ClothingNotFound;
import com.example.javaproject.exception.ShoppingCartNotFound;
import com.example.javaproject.mapper.ShoppingCartMapper;
import com.example.javaproject.model.Clothing;
import com.example.javaproject.model.ShoppingCart;
import com.example.javaproject.model.User;
import com.example.javaproject.repository.ShoppingCartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {
    private static final Long ID = 1L;
    private static final Long USER_ID = 1L;
    private static final String EMAIL = "andreea@gmail.com";
    private static final Long CLOTHING_ID = 1L;
    private static final String NAME = "Rochie";
    private static final Double PRICE = 89.99;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserService userService;

    @Mock
    private ClothingService clothingService;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private User User() {
        User user = new User();
        user.setEmail(EMAIL);

        return user;
    }

    private Clothing Clothing() {
        Clothing clothing = new Clothing();
        clothing.setName(NAME);
        clothing.setPrice(PRICE);

        return clothing;
    }

    private ShoppingCartDto shoppingCartDto() {
        return ShoppingCartDto.builder()
                .build();
    }

    private ShoppingCart shoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ID);
        User user = User();
        Clothing clothing = Clothing();
        shoppingCart.setUser(user);
        user.setShoppingCart(shoppingCart);
        user.getShoppingCart().totalAmount();
        shoppingCart.setClothes(List.of(clothing));

        return shoppingCart;
    }

    @Test
    @DisplayName("Add a clothing to the shopping cart successfully")
    void addClothingToCart() throws ClothingNotFound, NoSuchAlgorithmException {

        User user = User();
        user.setId(USER_ID);

        Clothing clothing = Clothing();
        clothing.setId(CLOTHING_ID);
        clothing.setPrice(PRICE);

        ShoppingCart savedShoppingCart = shoppingCart();
        savedShoppingCart.setId(ID);

        ShoppingCartDto returnedShoppingCartDto = shoppingCartDto();
        returnedShoppingCartDto.setId(ID);

        when(userService.findById(USER_ID)).thenReturn(user);
        when(clothingService.findById(CLOTHING_ID)).thenReturn(clothing);
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(savedShoppingCart);
        when(shoppingCartMapper.mapToDto(savedShoppingCart)).thenReturn(returnedShoppingCartDto);

        ShoppingCartDto result = shoppingCartService.addClothingToShoppingCart(USER_ID, CLOTHING_ID);

        assertNotNull(result);
        assertEquals(returnedShoppingCartDto.getId(), result.getId());
        assertEquals(returnedShoppingCartDto.getUserId(), result.getUserId());
    }

    @Test
    @DisplayName("Delete clothing from shopping cart successfully")
    void deleteClothingFromCart() throws ShoppingCartNotFound, ClothingNotFound {
        ShoppingCart shoppingCart = shoppingCart();
        shoppingCart.setId(ID);

        Clothing clothing = Clothing();
        clothing.setId(CLOTHING_ID);

        shoppingCart.setClothes(new ArrayList<>(List.of(clothing)));

        Optional<ShoppingCart> optionalShoppingCart = Optional.of(shoppingCart);

        ShoppingCart savedShoppingCart = shoppingCart();

        ShoppingCartDto shoppingCartDto = shoppingCartDto();
        shoppingCartDto.setClothes(List.of());

        when(clothingService.findById(CLOTHING_ID)).thenReturn(clothing);
        when(shoppingCartRepository.findById(ID)).thenReturn(optionalShoppingCart);
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(savedShoppingCart);
        when(shoppingCartMapper.mapToDto(savedShoppingCart)).thenReturn(shoppingCartDto);

        ShoppingCartDto result = shoppingCartService.deleteClothingFromShoppingCart(ID, CLOTHING_ID);

        assertNotNull(result);
        assertEquals(shoppingCartDto.getId(), result.getId());
        assertEquals(shoppingCartDto.getClothes(), result.getClothes());
    }

}