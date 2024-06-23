package com.sky.controller.user;

import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(value = "套餐查询")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;;

    @GetMapping("/list")
    @ApiOperation("查询套餐")
    @Cacheable(cacheNames = "setmealCache" , key = "#categoryId") // 开启redis缓存
    public Result<List<SetmealVO>> getSetmealByCategoryId(Integer categoryId){
        List<SetmealVO> list = setmealService.getSetmealByCategoryId(categoryId);
        return Result.success(list);
    }

    @GetMapping("/dish/{setmealId}")
    @ApiOperation("查询菜品")
    public Result<List<SetmealDish>> getDishByCategoryId(@PathVariable Long setmealId){
        List<SetmealDish> list = setmealService.getByIdSetmealId(setmealId);
        return Result.success(list);
    }
}
