package com.example.javaproject.service;

import com.example.javaproject.dto.UserDto;
import com.example.javaproject.exception.EmailAlreadyRegistered;
import com.example.javaproject.exception.UserNotFound;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.model.User;
import com.example.javaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    private boolean EmailExists(String email) {
        User user = userRepository.findUserByEmail(email);
        return user != null;
    }

    public UserDto registerUser(UserDto userDto) throws EmailAlreadyRegistered, NoSuchAlgorithmException {
        User user = userMapper.mapToEntity(userDto);

        if (EmailExists(user.getEmail())) {
            throw new EmailAlreadyRegistered("This email is already registered!");
        }
        user.setPassword(encrypt(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.mapToDto(savedUser);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List <UserDto> usersDTO = users.stream().map(user -> userMapper.mapToDto(user))
                .collect(Collectors.toList());
        return usersDTO;
    }
    public UserDto findUserById(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFound("User not found!");
        return userMapper.mapToDto(user.get());
    }

    public UserDto updateUser(User user, Long id) throws NoSuchAlgorithmException {
        findUserById(id);
        user.setId(id);
        user.setPassword(encrypt(user.getPassword()));
        return userMapper.mapToDto(userRepository.save(user));
    }

    public User findById(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFound("User not found!");
        return user.get();
    }

    public void deleteUserById(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }

    private String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hash = bigInt.toString(16);
        while(hash.length() < 32 ){
            hash = "0" + hash;
        }
        return hash;
    }
}
