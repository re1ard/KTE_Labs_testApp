package app.controllers.rest;

import app.controllers.abs.StatisticController;
import app.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/statistic", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestStatisticController extends StatisticController {

    @Autowired
    public RestStatisticController(StatisticService statisticService) {
        super(statisticService);
    }
}
