package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(value = "套餐查询")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;;

    @GetMapping("/page")
    @ApiOperation("查询套餐")
    public Result<PageResult> queryPage(SetmealPageQueryDTO setmealPageQueryDTO ){
        PageResult result = setmealService.queryPage(setmealPageQueryDTO);
        return Result.success(result);
    }

    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache" , key = "#setmealDTO.categoryId")
    public Result insert(@RequestBody SetmealDTO setmealDTO){
        setmealService.insert(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("起售停售")
    @CacheEvict(cacheNames = "setmealCache" , allEntries = true)
    public Result updateStatus(@PathVariable Integer status, Long id){
        setmealService.updateStatus(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取套餐")
    public Result<SetmealVO> getSetmealById(@PathVariable Long id){
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("更新套餐")
    @CacheEvict(cacheNames = "setmealCache" , allEntries = true)
    public Result update(SetmealDTO setmealDTO){
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除套餐")
    @CacheEvict(cacheNames = "setmealCache" , allEntries = true)
    public Result delete(@RequestParam List<Long> ids){
        setmealService.delete(ids);
        return Result.success();
    }
}
