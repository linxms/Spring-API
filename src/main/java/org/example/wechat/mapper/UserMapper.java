package org.example.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.wechat.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select userid from User where username = #{username}")
    String getTableUserId(String username);

    @Select("select id from User where userid = #{userid}")
    int getTableId(String userid);

    @Select("select count(*) from User where userid = #{userid}")
    int getUser(String userid);

}
