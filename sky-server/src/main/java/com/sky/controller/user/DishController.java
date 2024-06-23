package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(value = "分类查询")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;;

    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/list")
    @ApiOperation("查询菜品")
    public Result<List<DishVO>> getDishByCategoryId(Long categoryId){

        // 构造redis中的key，规则dish_分类id
        String key = "dish_" + categoryId;

        // 查询redis中是否存在菜品数据
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        // 如果存在直接返回无需查询数据库
        if(list !=null && list.size() >0){
            return Result.success(list);
        }
        // 如果不存在，查询数据库并把数据缓存到redis中
        Dish dish = Dish.builder().categoryId(categoryId).status(StatusConstant.ENABLE).build();
        list = dishService.getDishListByDish(dish);
        redisTemplate.opsForValue().set(key,list);
        return Result.success(list);
    }

}
