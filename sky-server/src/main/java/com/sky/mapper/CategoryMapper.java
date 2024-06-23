package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("insert into category (type, name , sort,status,create_user,create_time,update_user,update_time) values (#{type} , #{name} , #{sort}, #{status} , #{createUser}, #{createTime} , #{updateUser},#{updateTime})")
    @AutoFill(value = OperationType.INSERT)
    void save(Category category);

    Page<Category> getPage(CategoryPageQueryDTO categoryPageQueryDto);

    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Long id);

    List<Category> getList(Integer type);
}
