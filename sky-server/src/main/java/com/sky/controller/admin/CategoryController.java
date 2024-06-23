package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类接口")
public class CategoryController {

   @Autowired
   private CategoryService categoryService;
   @PostMapping
   @ApiOperation(value = "新增分类")
   public Result<String> save(@RequestBody CategoryDTO categoryDto){
       categoryService.save(categoryDto);
       return Result.success();
   }

   @GetMapping("/page")
   @ApiOperation(value = "分页查询")
    public Result<PageResult> getPage(CategoryPageQueryDTO categoryPageQueryDto){
       PageResult page = categoryService.getPage(categoryPageQueryDto);
       return Result.success(page);
   }

   @PostMapping("/status/{status}")
   @ApiOperation(value = "禁用启用")
    public Result<String> updateStatus(@PathVariable Integer status, Long id){
       categoryService.updateStatus(status,id);
       return Result.success();
   }

    @PutMapping
    @ApiOperation(value = "修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDto){
        categoryService.update(categoryDto);
        return Result.success();
   };

    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public Result<String> delete(Long id){
       categoryService.delete(id);
       return Result.success();
   }

    @GetMapping("/list")
    @ApiOperation(value = "类型查询不分页")
    public Result<List<Category>> getList(Integer type){
        List<Category> page = categoryService.getList(type);
        return Result.success(page);
    }
}
