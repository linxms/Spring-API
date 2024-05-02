package org.example.wechat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.wechat.entity.Exercitation;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ExercitationService extends IService<Exercitation> {
    boolean addExercitation(Exercitation exercitation) throws RuntimeException;

    boolean deleExercitation(Map<String, Object> map)throws RuntimeException;

    boolean updateExercitation(Exercitation exercitation) throws  RuntimeException;

    IPage<Exercitation> selectPage(Page<Exercitation> page, Exercitation exercitation) throws RuntimeException;

}
