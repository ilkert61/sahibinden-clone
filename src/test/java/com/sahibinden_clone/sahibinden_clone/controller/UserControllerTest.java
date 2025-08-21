package com.sahibinden_clone.sahibinden_clone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahibinden_clone.sahibinden_clone.dto.DeleteUserRequest;
import com.sahibinden_clone.sahibinden_clone.dto.EditUserRequest;
import com.sahibinden_clone.sahibinden_clone.dto.UserDTO;
import com.sahibinden_clone.sahibinden_clone.entity.Gender;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addUser_shouldReturnUser() throws Exception {
        UserDTO dto = new UserDTO("Ali","Veli","ali@mail.com","5551112233",
                "aliveli","123456", Gender.male, LocalDate.of(2000,1,1));

        Users user = new Users();
        user.setId(UUID.randomUUID());
        user.setUsername("aliveli");
        user.setEmail("ali@mail.com");

        given(userService.addUser(any())).willReturn(user);

        mockMvc.perform(post("/users/addUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("aliveli"));
    }

    @Test
    void deleteUser_shouldReturnOk() throws Exception {
        DeleteUserRequest req = new DeleteUserRequest();
        req.setPassword("123456");

        mockMvc.perform(delete("/users/username/aliveli")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("Kullanıcı silindi."));
    }

    @Test
    void updateContact_shouldReturnUpdatedUser() throws Exception {
        EditUserRequest req = new EditUserRequest();
        req.setEmail("new@mail.com");

        Users user = new Users();
        user.setId(UUID.randomUUID());
        user.setUsername("aliveli");
        user.setEmail("new@mail.com");

        given(userService.updateUserContactByUsername(eq("aliveli"), any())).willReturn(user);

        mockMvc.perform(put("/users/username/aliveli/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("new@mail.com"));
    }
}
