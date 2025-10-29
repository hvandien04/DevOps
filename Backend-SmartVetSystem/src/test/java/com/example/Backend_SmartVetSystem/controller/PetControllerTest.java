package com.example.Backend_SmartVetSystem.controller;

import com.example.Backend_SmartVetSystem.dto.request.OwnerRequest;
import com.example.Backend_SmartVetSystem.dto.request.PetCreateRequest;
import com.example.Backend_SmartVetSystem.dto.response.OwnerResponse;
import com.example.Backend_SmartVetSystem.dto.response.PetResponse;
import com.example.Backend_SmartVetSystem.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@Slf4j
@WebMvcTest(PetController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //test DEV
    //test DEV_2

    @MockitoBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    private PetCreateRequest petCreateRequest;
    private PetResponse petResponse;

    @BeforeEach
    public void initData() {
        OwnerResponse ownerResponse = OwnerResponse.builder()
                .name("John")
                .email("11@11.com")
                .phone("0931360124")
                .address("None")
                .ownerId("Test")
                .build();
        OwnerRequest ownerRequest = OwnerRequest.builder()
                .name("John")
                .email("11@11.com")
                .phone("0931360124")
                .address("None")
                .build();
        petCreateRequest = PetCreateRequest.builder()
                .name("Pet 1")
                .breed("Dog")
                .birthDate(LocalDate.of(2024,2,12))
                .owner(ownerRequest)
                .build();


        petResponse = PetResponse.builder()
                .petId("test")
                .name("Pet 1")
                .breed("Dog")
                .birthDate(LocalDate.of(2024,2,12))
                .owner(ownerResponse)
                .build();

    }

    @Test
    void CreatePetTest_validRequest_success() throws Exception {

        String content = objectMapper.writeValueAsString(petCreateRequest);

        when(petService.createPet(ArgumentMatchers.any())).thenReturn(petResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/pet")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));

        log.info("Created Pet Test");
    }
}
