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
public class UserStatisticsDTO {
    // 注册日期
    private LocalDate regDate;
    // 当天注册人数
    private Integer count;
}
