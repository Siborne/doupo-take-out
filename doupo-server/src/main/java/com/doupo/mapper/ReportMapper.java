package com.doupo.mapper;

import com.doupo.dto.GoodsSalesDTO;
import org.apache.ibatis.annotations.Mapper;

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
     * 销量排名
     *
     * @param map
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(Map<String, Object> map);
}
