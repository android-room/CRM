package com.web.filter;

import com.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入到验证登录过滤器");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

//        当前页面地址
//        System.out.println(request.getServletPath());
//        System.out.println(request.getContextPath());
        String path = request.getServletPath();
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            System.out.println(path+"：www");
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            User user = (User) session.getAttribute("user");
            if (user != null){
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
            /*
                重定向到登录页
                request.getContextPath()/项目名
            */
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                System.out.println(request.getContextPath()+"/login.jsp"+"sss");
            }
        }



    }
}
