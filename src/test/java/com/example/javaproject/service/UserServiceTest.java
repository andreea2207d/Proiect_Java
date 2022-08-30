package com.example.javaproject.service;

import com.example.javaproject.dto.UserDto;
import com.example.javaproject.exception.EmailAlreadyRegistered;
import com.example.javaproject.exception.UserNotFound;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.model.User;
import com.example.javaproject.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "andreea@gmail.com";

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDto UserDto() {
        return UserDto.builder()
                .email(EMAIL)
                .build();
    }

    private User User() {
        User user = new User();
        user.setEmail(EMAIL);

        return user;
    }

    @Test
    @DisplayName("Register a user successfully")
    void registerUserSuccess() throws EmailAlreadyRegistered, NoSuchAlgorithmException {
        UserDto userDto = UserDto();
        User user = User();
        user.setPassword("password");

        User savedUser = User();
        savedUser.setId(ID);

        UserDto returnedUserDto = UserDto();
        returnedUserDto.setId(ID);

        when(userMapper.mapToEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapToDto(savedUser)).thenReturn(returnedUserDto);
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(null);

        UserDto result = userService.registerUser(userDto);

        assertNotNull(result);
        assertEquals(returnedUserDto.getEmail(), result.getEmail());
        assertEquals(returnedUserDto.getId(), result.getId());
    }

    @Test
    @DisplayName("Register a user fails")
    void addUserExistingEmail() {
        UserDto userDto = UserDto();
        User user = User();
        user.setPassword("password");

        when(userMapper.mapToEntity(userDto)).thenReturn(user);
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(new User());

        assertThrows(EmailAlreadyRegistered.class, () -> userService.registerUser(userDto));
    }

    @Test
    @DisplayName("Update a user successfully")
    void updateUserSuccess() throws NoSuchAlgorithmException {
        User user = User();
        user.setPassword("password");

        User findUser = User();
        findUser.setId(ID);
        Optional<User> optionalUser = Optional.of(findUser);

        User savedUser = User();
        savedUser.setId(ID);

        UserDto returnedUserDto = UserDto();
        returnedUserDto.setId(ID);

        when(userRepository.findById(ID)).thenReturn(optionalUser);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapToDto(savedUser)).thenReturn(returnedUserDto);

        UserDto result = userService.updateUser(user, ID);

        assertNotNull(result);
        assertEquals(returnedUserDto.getEmail(), result.getEmail());
        assertEquals(returnedUserDto.getId(), result.getId());
    }

    @Test
    @DisplayName("Update a user fails")
    void updateUserNotFound() {
        User user = User();

        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> userService.updateUser(user, ID));
    }

    @Test
    @DisplayName("Delete user successfully")
    void deleteUserSuccess()  {
        User user = User();
        user.setId(ID);
        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(ID)).thenReturn(optionalUser);

        userService.deleteUserById(ID);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Delete user fails")
    void deleteUserNotFound()  {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> userService.deleteUserById(ID));
    }

}