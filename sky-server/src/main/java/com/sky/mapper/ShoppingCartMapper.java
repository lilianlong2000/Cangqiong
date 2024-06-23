package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    @Insert("insert into shopping_cart (name, user_id, dish_id, setmeal_id, dish_flavor, number, image, amount, create_time) " +
            "values (#{name}, #{userId} , #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{image}, #{amount}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> getListByUserId(Long userId);

    @Delete("delete from shopping_cart where user_id = #{userId}")
    void clean(Long userId);
}
