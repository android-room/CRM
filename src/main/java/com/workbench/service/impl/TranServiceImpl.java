package com.workbench.service.impl;

import com.utils.DateTimeUtil;
import com.utils.SqlSessionUtil;
import com.utils.UUIDUtil;
import com.vo.PaginationVo;
import com.workbench.dao.*;
import com.workbench.domain.*;
import com.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper((CustomerDao.class));

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> list = activityDao.getActivityListByName(aname);
        return list;
    }

    @Override
    public List<Contacts> getCustomerListName(String bName) {
        List<Contacts> list =contactsDao.getCustomerListName(bName);
        return list;
    }

    @Override
    public boolean save(Tran t, String customerName) {
        /*
            交易添加业务
                添加之前，参数t少了 customerId
                1.判断customerName,根据客户名称在列表里面精确查询
         */
        boolean flag = true;
        Customer cus = customerDao.getCustomerByName(customerName);
        if (cus==null){
            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setName(customerName);
            cus.setCreateBy(t.getCreateBy());
            cus.setCreateTime(DateTimeUtil.getSysTime());
            cus.setContactSummary(t.getContactSummary());
            cus.setNextContactTime(t.getNextContactTime());
            cus.setOwner(t.getOwner());
            //添加客户
            int count = customerDao.save(cus);
            if (count!=1){
                flag = false;
            }
        }
        //通过以上对于客户的处理，不论是查询的，还是没有的，客户id都有了
        t.setCustomerId(cus.getId());

        //添加交易
        int count1 = tranDao.save(t);
        if (count1!=1){
            flag = false;
        }
        //添加交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getCreateBy());
        int count2 = tranHistoryDao.save(th);
        if (count2!=1){
            flag = false;
        }

        return true;
    }

    @Override
    public PaginationVo<Tran> pageList(Map<String, Object> map) {

        int total = tranDao.getTotal(map);
        List<Tran> dataList = tranDao.getTranList(map);

        PaginationVo<Tran> vo = new PaginationVo<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public Tran detail(String id) {
        Tran t = tranDao.detail(id);
        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {

        List<TranHistory> thList = tranHistoryDao.getHistoryListByTranId(tranId);
        return thList;
    }


    @Override
    public boolean changeStage(Tran t) {
        boolean flag = true;

        //改变交易阶段
        int count = tranDao.changeStage(t);
        if (count != 1){
            flag = false;
        }

        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setExpectedDate(t.getExpectedDate());
        th.setMoney(t.getMoney());
        th.setTranId(t.getId());
        int count1 = tranHistoryDao.save(th);
        if (count1 != 1){
            flag = false;
        }

        return flag;
    }


    @Override
    public Map<String, Object> getCharts() {
        //取得total
        int total = tranDao.Total();
        //取得dataList
        List<Map<String,Object>> dataList = tranDao.Charts();
        //返回map
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("dataList",dataList);

        return map;
    }
}
