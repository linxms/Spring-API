package org.example.wechat.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.wechat.entity.User;
import org.example.wechat.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    public UserServiceImpl(UserMapper userMapper) {
        this.baseMapper = userMapper;
    }
    @Override
    public boolean addUser(User user) throws RuntimeException {
        try {
            int isUser = this.baseMapper.getUser(user.getUserid());
            if (isUser == 0){
                save(user);
                return true;
            }else
                throw new RuntimeException("用户已存在");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean delUser(Map<String, Object> map) throws RuntimeException {
        try {

            int result = this.baseMapper.deleteByMap(map);
            if(result < 1) {
                throw new RuntimeException("更新异常");
            }
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean updateUser(User user) throws RuntimeException {
        try {
            int findId = this.baseMapper.getTableId(user.getUserid());
            UpdateWrapper<User> updateWrapper = Wrappers.update();
            updateWrapper.eq("id", findId);
            int result = this.baseMapper.update(user, updateWrapper);
            if (result < 1){
                throw new RuntimeException("更新异常");
            }
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public IPage<User> selectPage(Page<User> page, User user) throws RuntimeException {
        try {
            LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
            wrapper.like(user.getUserid()!=null,User::getUserid,user.getUserid())
                    .like(user.getUsername()!=null,User::getUsername,user.getUsername())
                    .like(user.getPassword()!=null,User::getPassword,user.getPassword());
            IPage<User> pages= this.baseMapper.selectPage(page,wrapper);
            if(pages==null){
                throw new RuntimeException("未知异常");
            }
            return pages;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
