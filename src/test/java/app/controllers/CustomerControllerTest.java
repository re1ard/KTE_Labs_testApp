package app.controllers;

import app.BaseTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest extends BaseTest {
    @Test
    public void GetAllCustomers() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/customers")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void ChangeUserDiscount() throws Exception {
        mockMvc.perform(patch("http://localhost:8080/api/customers/1?first_discount=15")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void RemoveUserDiscount() throws Exception {
        mockMvc.perform(patch("http://localhost:8080/api/customers/1?first_discount=0")).andDo(print()).andExpect(status().isOk());
    }
}
