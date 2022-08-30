package com.example.javaproject.controller;

import com.example.javaproject.dto.UserDto;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    private static final Long ID = 1L;
    private static final String FIRST_NAME = "Bianca";
    private static final String LAST_NAME = "Dinu";
    private static final String EMAIL = "bianca27d@gmail.com";
    private static final String PASSWORD = "password";
    private static final String PHONE_NUMBER = "0737476725";
    private static final String ADDRESS = "Craiova";

    private UserDto UserDto() {
        return UserDto.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .phoneNumber(PHONE_NUMBER)
                .address(ADDRESS)
                .build();
    }
    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test - register a user")
    void testRegisterUser() throws Exception {
        //Arrange
        UserDto dto = UserDto();
        when(userService.registerUser(any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(post("/shop/user/register")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Test - find all users")
    void testFindAllUsers() throws Exception {
        List<UserDto> dto = List.of(UserDto());
        when(userService.findAllUsers()).thenReturn(dto);

        mockMvc.perform(get("/shop/user/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName", is(dto.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(dto.get(0).getLastName())))
                .andExpect(jsonPath("$[0].email", is(dto.get(0).getEmail())))
                .andExpect(jsonPath("$[0].password", is(dto.get(0).getPassword())))
                .andExpect(jsonPath("$[0].phoneNumber", is(dto.get(0).getPhoneNumber())))
                .andExpect(jsonPath("$[0].address", is(dto.get(0).getAddress())));
    }

    @Test
    @DisplayName("Test - find a user")
    void testFindUser() throws Exception {
        UserDto dto = UserDto();
        Long id = 1L;
        when(userService.findUserById(any())).thenReturn(dto);

        mockMvc.perform(get("/shop/user/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.phoneNumber", is(dto.getPhoneNumber())))
                .andExpect(jsonPath("$.password", is(dto.getPassword())))
                .andExpect(jsonPath("$.address", is(dto.getAddress())))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(dto.getLastName())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())));
    }

    @Test
    @DisplayName("Test - update a user")
    void testUpdateUser() throws Exception {
        //Arrange
        UserDto dto = UserDto();
        Long id = 1L;
        when(userService.updateUser(any(), any())).thenReturn(dto);

        //Act
        MvcResult result = mockMvc.perform(put("/shop/user/update/" + id)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dto));
    }

    @Test
    @DisplayName("Test - delete a user")
    void testDeleteUser() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/shop/user/delete/" + id))
                .andExpect(status().isNoContent());
    }

}