package org.example.wechat.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.wechat.entity.Exercitation;
import org.example.wechat.mapper.ExercitationMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


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
    public boolean deleExercitation(Exercitation exercitation) throws RuntimeException {
//        String work_name = exercitation.getWork_name();
//        String company = exercitation.getCompany();
//
//        Map<String, Object> deleMap = new HashMap<>();
//        deleMap.put("company", company);
//        deleMap.put("work_name", work_name);
        try {
            int result = this.baseMapper.deleteById(exercitation);

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
            int result =this.baseMapper.updateById(exercitation);
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
