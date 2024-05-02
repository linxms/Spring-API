package org.example.wechat.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.wechat.entity.Exercitation;
import org.example.wechat.mapper.ExercitationMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExercitationServiceImpl extends ServiceImpl<ExercitationMapper, Exercitation> implements ExercitationService{

    public ExercitationServiceImpl(ExercitationMapper exercitationMapper){
        this.baseMapper = exercitationMapper;
    }
    @Override
    public boolean addExercitation(Exercitation exercitation) throws RuntimeException {
        try {
            int isWork = this.baseMapper.getWork(exercitation.getCompany(), exercitation.getWork_name());

            if (isWork == 0){
                save(exercitation);
                return true;
            }else {
                throw new RuntimeException("数据已经存在，您不能插入");
            }

        } catch (Exception e) {
            throw new RuntimeException("插入数据失败" + e.getMessage());
        }
    }

    @Override
    public boolean deleExercitation(Map<String, Object> map) throws RuntimeException {

        try {
            int result = this.baseMapper.deleteByMap(map);

            if (result < 1) {
                throw new RuntimeException("删除错误");
            }
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean updateExercitation(Exercitation exercitation) throws RuntimeException {

        try {
            int findId = this.baseMapper.getWorkId(exercitation.getCompany(), exercitation.getWork_name());
            UpdateWrapper<Exercitation> updateWrapper = Wrappers.update();
            updateWrapper.eq("id", findId);
            int result = this.baseMapper.update(exercitation, updateWrapper);
            if (result < 1){
                throw new RuntimeException("更新异常");
            }
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

    }



    @Override
    public IPage<Exercitation> selectPage(Page<Exercitation> page, Exercitation exercitation) throws RuntimeException {
        try {
            LambdaQueryWrapper<Exercitation> wrapper =new LambdaQueryWrapper<Exercitation>();

            wrapper.like(exercitation.getCompany()!= null, Exercitation::getCompany, exercitation.getCompany())
                    .like(exercitation.getWork_name() != null, Exercitation::getWork_name, exercitation.getWork_name())
                    .like(exercitation.getSalary() != null, Exercitation::getSalary, exercitation.getSalary());

            IPage<Exercitation> pages = this.baseMapper.selectPage(page, wrapper);

            if(pages==null){
                throw new RuntimeException("未知异常");
            }

            return pages;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
