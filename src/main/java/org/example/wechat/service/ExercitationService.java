package org.example.wechat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.wechat.entity.Exercitation;


public interface ExercitationService extends IService<Exercitation> {
    boolean addExercitation(Exercitation exercitation) throws RuntimeException;

    boolean deleExercitation(Exercitation exercitation) throws RuntimeException;

    boolean updateExercitation(Exercitation exercitation) throws  RuntimeException;

    IPage<Exercitation> selectPage(Page<Exercitation> page, Exercitation exercitation) throws RuntimeException;
}
