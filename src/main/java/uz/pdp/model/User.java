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
public class User {
    long id;
    String name;

    @JsonProperty(value = "passport_code")
    String passportCode;

    @JsonProperty(value = "created_date")
    LocalDate createdDate=LocalDate.now();
}
