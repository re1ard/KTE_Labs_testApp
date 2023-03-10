package app.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import app.BaseTest;
import org.junit.jupiter.api.Test;

public class ProductControllerTest extends BaseTest {

    @Test
    //3.Получение списка всех товаров
    public void GetAllProducts() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/products")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    //Проверка точности цены на выходе
    public void RateProduct() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/sell/prepare?customer_id=2&products_ids=1,2&count=8,1")).andExpect(status().isOk()).andExpect(content().string("547500"));
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=2&products_ids=1,2&count=8,1&final_price=547500")).andExpect(status().isCreated());
        mockMvc.perform(post("http://localhost:8080/api/products/rate?rate=4&product_id=2&customer_id=2")).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void DiscountTest() throws Exception {
        String order_url = "http://localhost:8080/api/sell/prepare?customer_id=3&products_ids=1,2&count=8,2";
        //
        Long prepared_price1 = Long.valueOf(mockMvc.perform(get(order_url)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=3&products_ids=1,2&count=8,2&final_price=" + prepared_price1.toString())).andExpect(status().isCreated());
        mockMvc.perform(patch("http://localhost:8080/api/customers/3?second_discount=15")).andExpect(status().isOk());
        //
        Long prepared_price2 = Long.valueOf(mockMvc.perform(get(order_url)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=3&products_ids=1,2&count=8,2&final_price=" + prepared_price2.toString())).andExpect(status().isCreated());
        assert prepared_price1 != prepared_price2;
    }

    @Test
    //Попытка поставить рейтинг не купленому товару
    public void RateProductIfNotBuy() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/products/rate&product_id=2&customer_id=4&rate=5")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    //4.Получение дополнительной информации о товаре
    public void ShowRating() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/sell/prepare?customer_id=2&products_ids=1,2&count=8,1")).andExpect(status().isOk()).andExpect(content().string("547500"));
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=2&products_ids=1,2&count=8,1&final_price=547500")).andExpect(status().isCreated());
        mockMvc.perform(post("http://localhost:8080/api/products/rate?rate=4&product_id=2&customer_id=2")).andDo(print()).andExpect(status().isCreated());
        mockMvc.perform(get("http://localhost:8080/api/products")).andDo(print()).andExpect(status().isOk());
        mockMvc.perform(get("http://localhost:8080/api/products/show?product_id=2&customer_id=2")).andDo(print()).andExpect(status().isOk());
        mockMvc.perform(post("http://localhost:8080/api/products/rate?product_id=2&customer_id=2")).andDo(print()).andExpect(status().isCreated());
        mockMvc.perform(get("http://localhost:8080/api/products/show?product_id=2&customer_id=2")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    //4.Получение дополнительной информации о товаре если покупали другие и ставили оценку
    public void ShowRatingIfBuyOther() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/sell/prepare?customer_id=2&products_ids=1,2&count=8,1")).andExpect(status().isOk()).andExpect(content().string("547500"));
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=2&products_ids=1,2&count=8,1&final_price=547500")).andExpect(status().isCreated());
        mockMvc.perform(get("http://localhost:8080/api/sell/prepare?customer_id=1&products_ids=1,2&count=8,1")).andExpect(status().isOk()).andExpect(content().string("547500"));
        mockMvc.perform(post("http://localhost:8080/api/sell/register?customer_id=1&products_ids=1,2&count=8,1&final_price=547500")).andExpect(status().isCreated());
        mockMvc.perform(post("http://localhost:8080/api/products/rate?rate=4&product_id=2&customer_id=2")).andDo(print()).andExpect(status().isCreated());
        mockMvc.perform(get("http://localhost:8080/api/products/show?product_id=2&customer_id=1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void ForceChangeRandomDiscount() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/force/random")).andExpect(status().isOk());
    }
}
