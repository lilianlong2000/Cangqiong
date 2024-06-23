package com.sky.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    @Autowired
    SetmealDishMapper setmealDishMapper;

    //新增菜品
    @Transactional
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.save(dish);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0) {
            flavors.forEach(f -> {
                        f.setDishId(id);
                    }
            );
            dishFlavorMapper.save(flavors);
        }
    }

    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getTotal(),page.getResult());
    }

    //菜品批量删除
    @Transactional
    public void delete(List<Long> ids) {
        //判断当前菜品是否能删除---是否存在起售中的菜品？
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否能够删除---是否关联套餐？
        List<Long> list = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(list != null && list.size()>0 ) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品中的菜品数据
        dishMapper.deleteByIds(ids);
        //删除菜品关联的口味数据
//        for(Long id : ids) {
//            dishFlavorMapper.deleteByDishId(id);
//        }
        dishFlavorMapper.deleteByDishIds(ids);
    }

    public DishVO getDishById(Long id) {
        //根据id查询菜品
        Dish dish = dishMapper.getById(id);
        //根据id查询菜品的所有口味
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        //返回DishVo对象
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    public void update(DishDTO dishDTO) {
        //更新菜品的基本数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        //更新菜品的口味数据
         //1.删除菜品的所有口味
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
         //2.批量插入菜品所有口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0) {
            flavors.forEach(f -> {
                        f.setDishId(dishDTO.getId());
                    }
            );
            dishFlavorMapper.save(flavors);
        }
    }
    public void updateDishStatus(Integer status, Long id) {
        //更新菜品的基本数据
        Dish dish = Dish.builder().status(status).id(id).build();
        dishMapper.update(dish);
    }

    public List<DishVO> getDishListByDish(Dish dish) {
        List<Dish> list =  dishMapper.getDishListByDish(dish);
        List<DishVO> voList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DishVO dishVO = getDishById(list.get(i).getId());
            voList.add(i,dishVO);
        }
        return voList;
    }
}
