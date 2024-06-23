package com.sky.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    public List<SetmealVO> getSetmealByCategoryId(Integer categoryId) {
       List<Setmeal> list = setmealMapper.getByCategoryId(categoryId , StatusConstant.ENABLE);
       List<SetmealVO> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
           List<SetmealDish> dishList = setmealDishMapper.getDishBySetmealId(list.get(i).getId());
           SetmealVO setmealVO = new SetmealVO();
           BeanUtils.copyProperties(list.get(i),setmealVO);
           setmealVO.setSetmealDishes(dishList);
           resultList.add(setmealVO);
        }
        return resultList;
    }

    public PageResult queryPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> list = setmealMapper.queryPage(setmealPageQueryDTO);
        return new PageResult(list.getTotal(),list.getResult());
    }

    public void insert(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.insert(setmeal);
        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes){
            setmealDish.setSetmealId(id);
        }
        setmealDishMapper.insertAll(setmealDishes);
    }

    public void updateStatus(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder().id(id).status(status).build();
        setmealMapper.update(setmeal);
    }

    public SetmealVO getById(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        List<SetmealDish> list = setmealDishMapper.getBySetmealId(id);
        SetmealVO setmealVo = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVo);
        setmealVo.setSetmealDishes(list);
        return setmealVo;
    }

    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && setmealDishes.size() > 0){
            setmealDishes.forEach(f->{
                f.setDishId(setmealDTO.getId());
            });
            setmealDishMapper.insertAll(setmealDishes);
        }
    }

    public void delete(List<Long> ids) {
        if(ids != null && ids.size() > 0){
            setmealMapper.deleteByIds(ids);
            setmealDishMapper.deleteBySetmealIds(ids);
        }

    }

    public List<SetmealDish> getByIdSetmealId(Long setmealId) {
        List<SetmealDish> setmealDishList = setmealDishMapper.getBySetmealId(setmealId);
        return setmealDishList;
    }


}
