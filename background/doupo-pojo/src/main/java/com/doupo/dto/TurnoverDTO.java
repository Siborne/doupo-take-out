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
public class TurnoverDTO {
    // 日期
    private LocalDate orderDate;

    // 营业额
    private Double amount;
}
