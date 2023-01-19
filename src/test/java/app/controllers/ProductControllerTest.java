package app.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import app.BaseTest;
import org.junit.jupiter.api.Test;

public class ProductControllerTest extends BaseTest {

    @Test
    public void GetAllProducts() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/products")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void RateProduct() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/sell/prepare?customer_id=2&products_ids=1,2&count=8,1")).andExpect(status().isOk()).andExpect(content().string("547500"));
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=2&products_ids=1,2&count=8,1&final_price=547500")).andExpect(status().isCreated());
        mockMvc.perform(post("http://localhost:8080/api/products/rate/2/2?rate=4")).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void ShowRating() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/sell/prepare?customer_id=2&products_ids=1,2&count=8,1")).andExpect(status().isOk()).andExpect(content().string("547500"));
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=2&products_ids=1,2&count=8,1&final_price=547500")).andExpect(status().isCreated());
        mockMvc.perform(post("http://localhost:8080/api/products/rate/2/2?rate=4")).andDo(print()).andExpect(status().isCreated());
        mockMvc.perform(get("http://localhost:8080/api/products")).andDo(print()).andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8080/api/products/show/2/2")).andDo(print()).andExpect(status().isOk());
    }
}
