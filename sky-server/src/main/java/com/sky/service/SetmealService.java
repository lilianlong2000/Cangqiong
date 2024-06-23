package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    List<SetmealVO> getSetmealByCategoryId(Integer categoryId);

    PageResult queryPage(SetmealPageQueryDTO setmealPageQueryDTO);

    void insert(SetmealDTO setmealDTO);

    void updateStatus(Integer status, Long id);

    SetmealVO getById(Long id);

    void update(SetmealDTO setmealDTO);

    void delete(List<Long> ids);

    List<SetmealDish> getByIdSetmealId(Long setmealId);
}
