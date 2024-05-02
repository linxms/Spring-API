package org.example.wechat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.wechat.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService extends IService<User> {
    boolean addUser(User user) throws RuntimeException;
    boolean delUser(Map<String, Object> map) throws RuntimeException;

    boolean updateUser(User user) throws RuntimeException;
    IPage<User> selectPage(Page<User> page, User user) throws RuntimeException;

}
