package app.controllers;

import app.services.ProductService;
import app.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/force")
public class ForceController {

    private StatisticService statisticService;
    private ProductService productService;

    @Autowired
    public ForceController(StatisticService statisticService,
                           ProductService productService) {
        this.statisticService = statisticService;
        this.productService = productService;
    }

    @PostMapping("/update")
    public ResponseEntity ForceUpdateStatistic(){
        statisticService.updateCustomerStatistic();
        statisticService.updateProductStatistic();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/random")
    public ResponseEntity ForceChangeRandomDiscount(){
        productService.changeDiscountRandomly();
        return new ResponseEntity(HttpStatus.OK);
    }
}
