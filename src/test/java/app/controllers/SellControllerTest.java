package app.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import app.BaseTest;
import org.junit.jupiter.api.Test;

public class SellControllerTest extends BaseTest {

    @Test
    public void PrepareSellAndRegisterSell() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/sell/prepare?customer_id=1&products_ids=1,2&count=8,1")).andDo(print()).andExpect(status().isOk()).andExpect(content().string("547500"));
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=2&products_ids=1,2&count=8,1&final_price=547500")).andDo(print()).andExpect(status().isCreated());
    }
}
