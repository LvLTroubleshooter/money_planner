package com.myapp.money_planner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendData {
    private List<DateAmountPair> incomeData;
    private List<DateAmountPair> expenseData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DateAmountPair {
        private String date;
        private Double amount;
    }
}