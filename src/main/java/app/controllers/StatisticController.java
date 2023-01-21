package app.controllers;

import app.entities.statistic.CustomerStatistic;
import app.entities.statistic.ProductStatistic;
import app.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
    private StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping
    public ResponseEntity RequestStatistic(
            @RequestParam(required = false, defaultValue = "0") Long customer_id,
            @RequestParam(required = false, defaultValue = "0") Long product_id
    ) {
        if((customer_id == 0 && product_id == 0) || (customer_id != 0 && product_id != 0)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (customer_id > 0) {
            CustomerStatistic statistic = statisticService.getCustomer(customer_id);
            return statistic != null ? new ResponseEntity<>(statistic, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
        } else if (product_id > 0) {
            ProductStatistic statistic = statisticService.getProduct(product_id);
            return statistic != null ? new ResponseEntity<>(statistic, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
