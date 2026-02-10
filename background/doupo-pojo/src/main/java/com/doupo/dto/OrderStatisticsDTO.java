package com.doupo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatisticsDTO {
    //订单日期
    private LocalDate orderDate;
    //订单总数
    private Integer totalCount;
    //有效订单数
    private Integer validCount;
}