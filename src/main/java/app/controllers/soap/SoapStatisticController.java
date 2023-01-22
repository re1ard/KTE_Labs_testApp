package app.controllers.soap;

import app.controllers.abs.StatisticController;
import app.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/soap/statistic", produces = MediaType.APPLICATION_XML_VALUE)
public class SoapStatisticController extends StatisticController {

    @Autowired
    public SoapStatisticController(StatisticService statisticService) {
        super(statisticService);
    }
}
