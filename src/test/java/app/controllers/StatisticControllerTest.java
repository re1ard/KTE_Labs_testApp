package app.controllers;

import app.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class StatisticControllerTest extends BaseTest {

    @Test
    public void A_CheckUpdateStatistic() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/statistic/update")).andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8080/api/statistic")).andExpect(status().isBadRequest());
        mockMvc.perform(get("http://localhost:8080/api/statistic?customer_id=1&product_id=1")).andExpect(status().isBadRequest());
    }

    @Test
    public void B1_CheckCustomer() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/statistic?customer_id=1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void B2_CheckProduct() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/statistic?product_id=1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void C1_BuyAndUpdateStatsAndCheckCustomer() throws Exception {
        String order_url = "http://localhost:8080/api/sell/prepare?customer_id=1&products_ids=1,2&count=8,2";
        Long prepared_price1 = Long.valueOf(mockMvc.perform(get(order_url)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=1&products_ids=1,2&count=8,2&final_price=" + prepared_price1.toString())).andExpect(status().isCreated());
        mockMvc.perform(post("http://localhost:8080/api/statistic/update")).andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8080/api/statistic?customer_id=1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void C2_BuyAndUpdateStatsAndCheckProduct() throws Exception {
        String order_url = "http://localhost:8080/api/sell/prepare?customer_id=1&products_ids=1,2&count=8,2";
        Long prepared_price1 = Long.valueOf(mockMvc.perform(get(order_url)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=1&products_ids=1,2&count=8,2&final_price=" + prepared_price1.toString())).andExpect(status().isCreated());
        mockMvc.perform(post("http://localhost:8080/api/statistic/update")).andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8080/api/statistic?product_id=1")).andDo(print()).andExpect(status().isOk());
    }
}
