import com.settings.dao.UserDao;
import com.settings.domain.DicValue;
import com.settings.domain.User;
import com.exception.LoginException;
import com.settings.service.UserService;
import com.settings.service.impl.UserServiceImpl;
import com.utils.MD5Util;
import com.utils.SqlSessionUtil;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Test;

import java.util.List;
import java.util.Scanner;

import java.util.UUID;

public class app {
    @Test
    public void text() throws LoginException {
//        UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

//        System.out.println(dao);
//        User user = dao.app();
//        System.out.println(user);

//        List<User> list = dao.arrar();
//        list.forEach(user -> System.out.println("list:"+user));

//        try {
//            int i = 10/0;
//        }catch (Exception e){
//            e.printStackTrace();
//            String msg = e.getMessage();
//            System.out.println(msg);
//            throw new LoginException("登入失败");
//        }

//        Map<String,String> map = new HashMap<>();
//        map.put("loginAct","lss");
//        map.put("loginPwd","202cb962ac59075b964b07152d234b70");
//        User user = dao.login(map);
//        if (user == null){
//            throw new LoginException("登入失败");
//        }
//        System.out.println(user);

//        String loginAct = "zs";
//        String loginPwd = "202cb962ac59075b964b07152d234b70";
//        Map<String,String> map = new HashMap<>();
//        map.put("loginAct",loginAct);
//        map.put("loginPwd",loginPwd);
//
//        User user = dao.login(map);
//
//        System.out.println(user);

//        UserService userService = new UserServiceImpl();
//        User user = userService.login("ls", "202cb962ac59075b964b07152d234b70", "0.0.0.0.0.0.1");
//        System.out.println(user);

//        int a = 5;
//        int b = 10;
//
//        int c = (a-1)*b;
//        System.out.println(c);




    }
}
