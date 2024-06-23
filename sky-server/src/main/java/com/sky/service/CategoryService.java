package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    void save(CategoryDTO categoryDTO);

    PageResult getPage(CategoryPageQueryDTO categoryPageQueryDto);

    void updateStatus(Integer status, Long id);

    void update(CategoryDTO categoryDto);

    void delete(Long id);

    List<Category> getList(Integer type);


}
