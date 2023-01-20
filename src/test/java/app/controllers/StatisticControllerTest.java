package app.controllers;

import app.BaseTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StatisticControllerTest extends BaseTest {

    @Test
    public void CheckStatistic() throws Exception {
        mockMvc.perform(post("http://localhost:8080/api/statistic/update")).andExpect(status().isOk());
    }
}
