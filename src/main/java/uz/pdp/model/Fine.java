package uz.pdp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Fine {
    long id;

    @JsonProperty(value = "user_id")
    long userId;

    @JsonProperty(value = "car_id")
    long car_id;

    double amount;


    @JsonProperty(value = "paid_date")
    LocalDate paidDate;

    @JsonProperty(value = "is_paid")
    boolean isPaid;

    @JsonProperty(value = "created_date")
    LocalDate createdDate=LocalDate.now();

    public void setPaid(boolean paid) {
        if(paid)
            this.paidDate=LocalDate.now();
        isPaid = paid;
    }
}
