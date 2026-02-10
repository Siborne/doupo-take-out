package com.doupo.service.impl;

import com.doupo.dto.GoodsSalesDTO;
import com.doupo.dto.OrderStatisticsDTO;
import com.doupo.dto.TurnoverDTO;
import com.doupo.dto.UserStatisticsDTO;
import com.doupo.entity.Orders;
import com.doupo.mapper.OrderMapper;
import com.doupo.mapper.ReportMapper;
import com.doupo.service.ReportService;
import com.doupo.service.WorkspaceService;
import com.doupo.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;
    private final OrderMapper orderMapper;
    private final WorkspaceService workspaceService;

    /**
     * 根据时间区间统计营业额
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
        /*List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1); //日期计算，获得指定日期后1天的日期
            dateList.add(begin);
        }

        List<Object> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map<String, Object> map = new HashMap<>();
            map.put("date", Orders.COMPLETED);
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }

        //数据封装
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
         */
        // 1. 生成日期列表 (保证 begin 不被修改)
        List<LocalDate> dateList = new ArrayList<>();
        LocalDate tempDate = begin;
        while (!tempDate.isAfter(end)) {
            dateList.add(tempDate);
            tempDate = tempDate.plusDays(1);
        }

        // 2. 一次性查询营业额数据
        // 注意：传参记得转成 LocalDateTime 的起始和结束点
        List<TurnoverDTO> turnoverStats = orderMapper.getTurnoverStatistics(
                LocalDateTime.of(begin, LocalTime.MIN),
                LocalDateTime.of(end, LocalTime.MAX)
        );

        // 3. 转换为 Map 结构 (日期 -> 金额)
        Map<LocalDate, Double> turnoverMap = turnoverStats.stream()
                .collect(Collectors.toMap(TurnoverDTO::getOrderDate, TurnoverDTO::getAmount));

        // 4. 组装数据列表
        List<Double> turnoverList = dateList.stream()
                .map(date -> turnoverMap.getOrDefault(date, 0.0))
                .collect(Collectors.toList());

        // 5. 封装 VO 返回
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 用户统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        /*List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Object> newUserList = new ArrayList<>();
        List<Object> totalUserList = new ArrayList<>();
        for (LocalDate date : dateList) {
            Map map = new HashMap();
            //获取当天时间最大值
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            map.put("end", endTime);
            Integer totalUsers = reportMapper.getUsersByTime(map);
            totalUsers = totalUsers == null ? 0 : totalUsers;
            totalUserList.add(totalUsers);

            //获取当天时间最小值
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            map.put("begin", beginTime);
            Integer newUsers = reportMapper.getUsersByTime(map);
            newUsers = newUsers == null ? 0 : newUsers;
            newUserList.add(newUsers);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
         */
        // 1. 生成日期列表
        List<LocalDate> dateList = new ArrayList<>();
        LocalDate tempDate = begin;
        while (!tempDate.isAfter(end)) {
            dateList.add(tempDate);
            tempDate = tempDate.plusDays(1);
        }

        // 2. 查询 begin 之前的初始总用户数
        Integer currentTotalUser = reportMapper.getTotalUserCountBefore(LocalDateTime.of(begin, LocalTime.MIN));
        currentTotalUser = (currentTotalUser == null) ? 0 : currentTotalUser;

        // 3. 一次性查出每天的新用户增量
        List<UserStatisticsDTO> newUserStats = reportMapper.getNewUserCountList(
                LocalDateTime.of(begin, LocalTime.MIN),
                LocalDateTime.of(end, LocalTime.MAX)
        );
        Map<LocalDate, Integer> newUserMap = newUserStats.stream()
                .collect(Collectors.toMap(UserStatisticsDTO::getRegDate, UserStatisticsDTO::getCount));

        // 4. 计算每天的数据
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            int todayNewUsers = newUserMap.getOrDefault(date, 0);
            currentTotalUser += todayNewUsers; // 累加：昨天的总数 + 今天的新增 = 今天的总数

            newUserList.add(todayNewUsers);
            totalUserList.add(currentTotalUser);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }

    /**
     * 订单统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        /*List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();
        for (LocalDate date : dateList) {
            //获取当天时间最大值
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            Integer orderCountTemp = reportMapper.getOrdersByTime(map);
            orderCountList.add(orderCountTemp);

            map.put("status", Orders.COMPLETED);
            Integer validOrderCountTemp = reportMapper.getOrdersByTime(map);
            validOrderCountList.add(validOrderCountTemp);
        }

        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }
        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
         */
        // 1. 生成日期列表 (注意：不要直接修改参数 begin，否则后面查询会出错)
        List<LocalDate> dateList = new ArrayList<>();
        LocalDate tempDate = begin;
        while (!tempDate.isAfter(end)) {
            dateList.add(tempDate);
            tempDate = tempDate.plusDays(1);
        }

        // 2. 一次性从数据库查询统计数据 (1条SQL搞定)
        List<OrderStatisticsDTO> statsList = reportMapper.getOrderStatistics(begin, end);

        // 3. 转成 Map 结构：日期 -> DTO对象，方便 O(1) 速度查找
        Map<LocalDate, OrderStatisticsDTO> statsMap = statsList.stream()
                .collect(Collectors.toMap(OrderStatisticsDTO::getOrderDate, dto -> dto));

        // 4. 根据日期列表抽取对应的“总订单数”和“有效订单数”
        List<Integer> orderCountList = dateList.stream()
                .map(date -> statsMap.containsKey(date) ? statsMap.get(date).getTotalCount() : 0)
                .collect(Collectors.toList());

        List<Integer> validOrderCountList = dateList.stream()
                .map(date -> statsMap.containsKey(date) ? statsMap.get(date).getValidCount() : 0)
                .collect(Collectors.toList());

        // 5. 计算合计数
        // 使用 stream 的 mapToInt 避免之前的泛型报错问题
        int totalOrderCount = orderCountList.stream().mapToInt(Integer::intValue).sum();
        int validOrderCount = validOrderCountList.stream().mapToInt(Integer::intValue).sum();

        // 6. 计算订单完成率
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = (double) validOrderCount / totalOrderCount;
        }

        // 7. 封装 VO 返回
        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 销量排名top10
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        Map<String, Object> map = new HashMap<>();
        map.put("begin", beginTime);
        map.put("end", endTime);
        List<GoodsSalesDTO> top10List = reportMapper.getSalesTop10(map);
        log.info("top10List:{}", top10List);

        String nameList = StringUtils.join(top10List.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList()), ",");
        String numberList = StringUtils.join(top10List.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList()), ",");

        return SalesTop10ReportVO.builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    /**
     * 导出营业数据报表
     *
     * @param response
     */
    @Override
    public void exportBusinessData(HttpServletResponse response) {
        // 1. 确定时间范围
        LocalDate begin = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now().minusDays(1);

        // 2. 获取 30 天的概览数据 (用于填充表格头部)
        BusinessDataVO allBusinessData = workspaceService.getBusinessData(
                LocalDateTime.of(begin, LocalTime.MIN),
                LocalDateTime.of(end, LocalTime.MAX));

        // 3. 获取明细数据 (调用之前写的批量统计方法)
        TurnoverReportVO turnoverReport = this.getTurnover(begin, end);
        UserReportVO userReport = this.userStatistics(begin, end);
        OrderReportVO orderReport = this.ordersStatistics(begin, end);

        // 将逗号分隔的字符串转为数组
        String[] turnoverArray = turnoverReport.getTurnoverList().split(",");
        String[] newUsersArray = userReport.getNewUserList().split(",");
        String[] totalOrdersArray = orderReport.getOrderCountList().split(",");
        String[] validOrdersArray = orderReport.getValidOrderCountList().split(",");

        // 4. 加载模板并填充
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/businessDataTemplate.xlsx");
             XSSFWorkbook excel = new XSSFWorkbook(inputStream)) {

            // ======= A. 填充概览数据  =======
            XSSFSheet sheet = excel.getSheet("Sheet1");

            // 填充时间区间
            sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end);

            // 第 4 行：营业额、订单完成率、新增用户数
            XSSFRow row3 = sheet.getRow(3);
            row3.getCell(2).setCellValue(allBusinessData.getTurnover());
            row3.getCell(4).setCellValue(allBusinessData.getOrderCompletionRate());
            row3.getCell(6).setCellValue(allBusinessData.getNewUsers());

            // 第 5 行：有效订单、平均客单价
            XSSFRow row4 = sheet.getRow(4);
            row4.getCell(2).setCellValue(allBusinessData.getValidOrderCount());
            row4.getCell(4).setCellValue(allBusinessData.getUnitPrice());

            // ======= B. 填充明细数据  =======
            for (int i = 29; i >= 0; i--) {
                // 1. 计算当前数组下标对应的数据日期
                LocalDate date = begin.plusDays(i);

                // 2. 关键：Excel 行索引应该从 7 开始往后排 (7, 8, 9...)
                // 当 i=29 (最新的一天) 时，我们填在第 7 行
                // 当 i=0  (最老的一天) 时，我们填在第 36 行
                int rowIndex = 7 + (29 - i);
                XSSFRow row = sheet.getRow(rowIndex);

                row.getCell(1).setCellValue(date.toString()); // 这里填的就是 2026-02-08

                // 3. 从数组里取值 (i 对应原始数组下标)
                double turnover = Double.parseDouble(turnoverArray[i]);
                row.getCell(2).setCellValue(turnover);

                int validOrders = Integer.parseInt(validOrdersArray[i]);
                row.getCell(3).setCellValue(validOrders);

                int totalOrders = Integer.parseInt(totalOrdersArray[i]);
                double completionRate = (totalOrders == 0) ? 0.0 : (double) validOrders / totalOrders;
                row.getCell(4).setCellValue(completionRate);

                double unitPrice = (validOrders == 0) ? 0.0 : turnover / validOrders;
                row.getCell(5).setCellValue(unitPrice);

                row.getCell(6).setCellValue(Integer.parseInt(newUsersArray[i]));
            }

            // 5. 导出文件
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
