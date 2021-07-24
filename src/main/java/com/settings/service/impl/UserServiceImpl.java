package com.settings.service.impl;

import com.settings.dao.UserDao;
import com.settings.domain.User;
import com.exception.LoginException;
import com.settings.service.UserService;
import com.utils.DateTimeUtil;
import com.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);


        User user = dao.login(map);


        if (user == null){
            throw new LoginException("账号密码错误");
        }
        //如果走到这里说明账号密码错误

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0){
            throw new LoginException("账号已失效");
        }

        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("账号已锁定");
        }

//        String allowIps = user.getAllowIps();
//        if (!allowIps.contains(ip)){
//            throw new LoginException("ip地址受限");
//        }

        return user;
    }

    @Override
    public List<User> getUserList() {

        List<User> list = dao.getUserList();
        return list;
    }

    public static void main(String[] args) throws LoginException {
        UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
        UserService userService = new UserServiceImpl();
        User user = userService.login("ls","202cb962ac59075b964b07152d234b70","0.0.0.0.0.0.1");
        System.out.println(user);

    }
}
