package com.ecommerce_backend_final.demo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalyticsResponseDTO {

    private Long placed;

    private Long shipped;

    private Long delivered;

    private Long currentMonthOrders;

    private Long previousMonthOrders;

    private Long currentMonthEarnings;

    private Long previousMonthEarnings;


}
