package com.lkyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lkyi.damain.po.User;
import com.lkyi.dao.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lkyi
 * @since 2020-04-24
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {

}
