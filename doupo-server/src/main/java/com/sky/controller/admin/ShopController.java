package com.sky.controller.admin;

import com.sky.constant.RedisConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
@RequiredArgsConstructor
public class ShopController {

    private static final String KEY = RedisConstant.REDIS_KEY_PREFIX + "SHOP_STATUS";

    private final RedisTemplate redisTemplate;

    /**
     * 获取店铺的营业状态
     *
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺营业状态:{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY, status.toString());
        return Result.success();
    }

    /**
     * 获取店铺的营业状态
     *
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus() {
//        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        Object value = redisTemplate.opsForValue().get(KEY);

        Integer status = 0; // 默认值：打烊
        if (value != null) {
            // 因为 setValueSerializer 是 StringRedisSerializer，所以 value 实际是 String
            String statusStr = value.toString();
            try {
                status = Integer.valueOf(statusStr);
            } catch (NumberFormatException e) {
                log.warn("Redis 中的店铺状态值格式异常: {}", statusStr);
                status = 0; // 异常时也返回默认值
            }
        }

        log.info("获取店铺营业状态:{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }

}
