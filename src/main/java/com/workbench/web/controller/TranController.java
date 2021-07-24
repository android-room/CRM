package com.workbench.web.controller;

import com.settings.domain.User;
import com.settings.service.UserService;
import com.settings.service.impl.UserServiceImpl;
import com.utils.*;
import com.vo.PaginationVo;
import com.workbench.domain.*;
import com.workbench.service.ActivityService;
import com.workbench.service.CustomerService;
import com.workbench.service.TranService;
import com.workbench.service.impl.ActivityServiceImpl;
import com.workbench.service.impl.CustomerServiceImpl;
import com.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到交易控制器");
        String path = request.getServletPath();
        if ("/workbench/transaction/add.do".equals(path)){
            add(request,response);
        } else if ("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(request,response);
        }else if ("/workbench/transaction/getActivityListListAName.do".equals(path)){
            getActivityListListAName(request,response);
        }else if ("/workbench/transaction/getCustomerListBName.do".equals(path)){
            getCustomerListBName(request,response);
        }else if ("/workbench/transaction/save.do".equals(path)){
            save(request,response);
        }else if ("/workbench/transaction/pageList.do".equals(path)){
            pageList(request,response);
        }else if ("/workbench/transaction/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/transaction/getHistoryListByTranId.do".equals(path)){
            getHistoryListByTranId(request,response);
        }else if ("/workbench/transaction/changeStage.do".equals(path)){
            changeStage(request,response);
        }else if ("/workbench/transaction/getCharts.do".equals(path)){
            getCharts(request,response);
        }
    }

    private void getCharts(HttpServletRequest request, HttpServletResponse response) {
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        /*
            业务层为我们返回
                   total
                   dataList
                   用map打包两项进行返回
         */
        Map<String,Object> map = ts.getCharts();
        PrintJson.printJsonObj(response,map);
    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("改变阶段");
        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();

        Tran t = new Tran();
        t.setId(id);
        t.setStage(stage);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        t.setEditTime(editTime);
        t.setEditBy(editBy);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag = ts.changeStage(t);

        Map<String,String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        t.setPossibility(pMap.get(stage));

        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("t",t);
        PrintJson.printJsonObj(response,map);
    }

    private void getHistoryListByTranId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据交易id取得相应的历史列表");
        String tranId = request.getParameter("tranId");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<TranHistory> thList = ts.getHistoryListByTranId(tranId);

        //将交易历史列表遍历
        Map<String,String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        for (TranHistory th : thList){
            //根据每一条交易历史，取出每一个阶段
            String stage = th.getStage();
            String possibility = pMap.get(stage);
            th.setPossibility(possibility);
        }
        PrintJson.printJsonObj(response,thList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Tran t = ts.detail(id);

        String stage = t.getStage();


        System.out.println(this.getClass().getName()+"123456");

        Map<String,String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);
//        ServletContext application = this.getServletContext();
//        ServletContext application2 = request.getServletContext();
//        ServletContext application3 = this.getServletConfig().getServletContext();





        request.setAttribute("t",t);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String customer = request.getParameter("customer");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String pageNoS = request.getParameter("pageNo");
        String pageSizeS = request.getParameter("pageSize");

        int pageNo = Integer.valueOf(pageNoS);

        int pageSize = Integer.valueOf(pageSizeS);
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("customer",customer);
        map.put("stage",stage);
        map.put("type",type);
        map.put("source",source);
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        PaginationVo<Tran> vo = ts.pageList(map);
        PrintJson.printJsonObj(response,vo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("执行交易添加操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName"); //此处暂时只有名称 没有id
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran t = new Tran();
        t.setId(id);
        t.setOwner(owner);
        t.setMoney(money);
        t.setName(name);
        t.setExpectedDate(expectedDate);

        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateTime(createTime);
        t.setCreateBy(createBy);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        boolean flag = ts.save(t,customerName);
        if (flag){
//            request.getRequestDispatcher("/workbench/transaction/index.jsp").forward(request,response);
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");

        }else {
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }



    }

    private void getActivityListListAName(HttpServletRequest request, HttpServletResponse response) {
        String AName = request.getParameter("aname");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<Activity> list = ts.getActivityListByName(AName);
        System.out.println(list);
        PrintJson.printJsonObj(response,list);

    }

    private void getCustomerListBName(HttpServletRequest request, HttpServletResponse response) {
        String BName = request.getParameter("bname");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<Contacts> list = ts.getCustomerListName(BName);
        list.forEach(customer -> System.out.println(customer));
        PrintJson.printJsonObj(response,list);
    }


    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取的 客户名称列表（模糊查询）");
        String name = request.getParameter("name");
        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        List<String> list = cs.getCustomerName(name);
        PrintJson.printJsonObj(response,list);

    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入跳转到交易添加页的操作");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();

        request.setAttribute("uList",uList);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }


}
