package com.example.Backend_SmartVetSystem.controller;

import com.example.Backend_SmartVetSystem.dto.request.UserCreateRequest;
import com.example.Backend_SmartVetSystem.dto.response.UserResponse;
import com.example.Backend_SmartVetSystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserCreateRequest request;
    private UserResponse response;

    @BeforeEach
    public void initData() {
        request = UserCreateRequest.builder()
                .email("<EMAIL>")
                .fullName("John")
                .role("DOCTOR")
                .phone("0931360124")
                .build();
        response = UserResponse.builder()
                .userId("test")
                .fullName("John")
                .email("<EMAIL>")
                .phone("0931360124")
                .role("DOCTOR")
                .build();

    }

    @Test
    public void CreateUser_validRequest_success() throws Exception {
        String content = objectMapper.writeValueAsString(request);

        when(userService.createUser(ArgumentMatchers.any())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
    }
}
