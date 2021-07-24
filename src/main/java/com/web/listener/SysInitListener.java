package com.web.listener;

import com.settings.domain.DicValue;
import com.settings.service.DicService;
import com.settings.service.impl.DicServiceImpl;
import com.utils.ServiceFactory;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {

    /*
        改方法是用来监听上下文域对象的方法，当服务器启动，上下文域对象创建
        对象创建，马上执行改方法
     */

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("服务器缓存处理数据字典开始");
        ServletContext application = sce.getServletContext();
        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());

        /*
            要
                7个List
         */
        Map<String, List<DicValue>> map = ds.getAll();
        Set<String> set = map.keySet();
        for (String key:set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");

//        application.setAttribute(key,"");


        //------------------------------------------------------------------------
        /*
            处理Stage2Possibility.properties
                解析该文件，将该属性文件的键值对关系处理成为java中键值对关系

                Properties
         */
        Map<String,String> pMap = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> e = rb.getKeys();
        while (e.hasMoreElements()){
            //阶段
            String key = e.nextElement();
            //可能性
            String value = rb.getString(key);

            pMap.put(key,value);
        }
        //将PMap保存到服务器缓存中
        application.setAttribute("pMap",pMap);
    }

}
