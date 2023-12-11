package com.ntu.edu.group5.ecommerce;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntu.edu.group5.ecommerce.entity.Seller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SellerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Get seller by Id")
    @Test
    public void getSellerByIdTest() throws Exception {
        // Step 1: Build a GET request to /sellers/1
        RequestBuilder request = MockMvcRequestBuilders.get("/sellers/1");

        // Step 2: Perform the request, get the response and assert
        mockMvc.perform(request)
            // Assert that the status code is 200
            .andExpect(status().isOk())
            // Assert that the content type is JSON
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            // Assert that the id returned is 1
            .andExpect(jsonPath("$.id").value(1));
    }

    @DisplayName("Get all customers")
    @Test
    public void getAllSellersTest() throws Exception {
        // Step 1: SETUP
        RequestBuilder request = MockMvcRequestBuilders.get("/sellers");

        // Step 2 & 3: EXECUTE and ASSERT
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void validSellerCreationTest() throws Exception {
        Seller seller = Seller.builder().firstName("Ben").lastName("Thomas").email("ben@yahoo.com")
        .contactNo("12345678").password("seller").build();

        String newSellerAsJSON = objectMapper.writeValueAsString(seller);

        RequestBuilder request = MockMvcRequestBuilders.post("/sellers").contentType(MediaType.APPLICATION_JSON)
        .content(newSellerAsJSON);

        mockMvc.perform(request)
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.firstName").value("Ben"))
            .andExpect(jsonPath("$.lastName").value("Thomas"));
    }

    @Test
    public void invalidSellerCreationTest() throws Exception {

       Seller invalidSeller = new Seller("  ", "  ", "bruce@a.com", "12345678", "1990");

        String invalidSellerAsJSON = objectMapper.writeValueAsString(invalidSeller);

        RequestBuilder request = MockMvcRequestBuilders.post("/sellers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidSellerAsJSON);

        mockMvc.perform(request)
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
