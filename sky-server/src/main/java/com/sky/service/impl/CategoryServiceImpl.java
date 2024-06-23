package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(StatusConstant.DISABLE);
        categoryMapper.save(category);
    }

    public PageResult getPage(CategoryPageQueryDTO categoryPageQueryDto) {
        PageHelper.startPage(categoryPageQueryDto.getPage(),categoryPageQueryDto.getPageSize());
        Page<Category> page = categoryMapper.getPage(categoryPageQueryDto);

        long total = page.getTotal();
        List<Category> result = page.getResult();

        return new PageResult(total, result);
    }

    public void updateStatus(Integer status, Long id){
        Category category = new Category();
        category.setStatus(status);
        category.setId(id);
        categoryMapper.update(category);
    }

    public void update(CategoryDTO categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto,category);
        categoryMapper.update(category);
    }

    public void delete(Long id) {
        categoryMapper.delete(id);
    }

    public List<Category> getList(Integer type) {
        List<Category> list = categoryMapper.getList(type);
        return list;
    }
}
