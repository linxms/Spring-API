package org.example.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.wechat.entity.Exercitation;


public interface ExercitationMapper extends BaseMapper<Exercitation> {
    @Select("select count(*) from Exercitation where company = #{company} and work_name = #{work_name}")
    int getWork(String company, String work_name);
}
