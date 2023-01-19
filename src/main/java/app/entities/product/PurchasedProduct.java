package app.entities.product;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
public class PurchasedProduct {

    private String description;

    private Byte user_review;

    private Double avg_rate;

    private HashMap <Byte, Integer> users_rate;

    //Получение средней оценки //4
    public Double getAvg_rate() {
        if (users_rate == null) return 0.0;
        return users_rate.keySet().stream().mapToDouble(Double::valueOf).average().getAsDouble();
    }
}
