package com.doupo.mapper;

import com.doupo.dto.GoodsSalesDTO;
import com.doupo.dto.OrderStatisticsDTO;
import com.doupo.dto.UserStatisticsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {

    /**
     * 用户统计接口
     *
     * @param map
     * @return
     */
    Integer getUsersByTime(Map map);

    /**
     * 订单统计接口
     *
     * @param map
     * @return
     */
    Integer getOrdersByTime(Map map);

    /**
     * 订单数据统计——高效版
     *
     * @param map
     * @return
     */
    List<OrderStatisticsDTO> getOrderStatistics(LocalDate begin, LocalDate end);

    /**
     * 销量排名
     *
     * @param map
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(Map<String, Object> map);

    /**
     * 获取指定时间之前的用户总数
     *
     * @param of
     * @return
     */
    Integer getTotalUserCountBefore(LocalDateTime time);

    /**
     * 获取指定时间之前的用户新增数
     *
     * @param of
     * @return
     */
    List<UserStatisticsDTO> getNewUserCountList(LocalDateTime begin, LocalDateTime end);
}
