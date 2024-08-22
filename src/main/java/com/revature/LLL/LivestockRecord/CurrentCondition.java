package com.revature.LLL.LivestockRecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CurrentCondition {
    @JsonProperty("examination_date")
    private LocalDate examination_date;
    @JsonProperty("diagnosis")
    private String diagnosis;
    @JsonProperty("diagnosis_tests")
    private String[] diagnosis_tests;
    @JsonProperty("symptoms")
    private String[] symptoms;
}
