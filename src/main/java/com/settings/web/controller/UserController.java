package com.settings.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.settings.domain.User;
import com.settings.service.UserService;
import com.settings.service.impl.UserServiceImpl;
import com.utils.MD5Util;
import com.utils.PrintJson;
import com.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户控制器");
        String path = request.getServletPath();
        System.out.println(path);
        if ("/settings/user/login.do".equals(path)){
                login(request,response);
        }else if ("/settings/user/***.do".equals(path)){

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("验证登入");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //MD5转换
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收ip
        String ip = request.getRemoteAddr();
        System.out.println("ip--------"+ip);

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
//        UserService us = new UserServiceImpl();

        try {
            User user = us.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            //走到此处，说明业务层没有抛出异常
            /*
                {"success":true}
             */
            PrintJson.printJsonFlag(response,true);
//            Map<String,Boolean> map = new HashMap<String,Boolean>();
//            map.put("success",true);
//            ObjectMapper om = new ObjectMapper();
//            String json = om.writeValueAsString(map);
//            response.getWriter().print(json);

//            System.out.println("json成功:"+json);
        }catch (Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            System.out.println(map.get("msg"));
            PrintJson.printJsonObj(response,map);
        }

    }


}
