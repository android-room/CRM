package com.workbench.service.impl;

import com.settings.dao.UserDao;
import com.settings.domain.User;
import com.utils.SqlSessionUtil;
import com.vo.PaginationVo;
import com.workbench.dao.ActivityDao;
import com.workbench.dao.ActivityRemarkDao;
import com.workbench.domain.Activity;
import com.workbench.domain.ActivityRemark;
import com.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    @Override
    public boolean save(Activity a) {

        boolean flag = true;
        int count = activityDao.save(a);
        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {

        int total = activityDao.getTotalByCondition(map);

        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        //将total和dataList封装到vo中
        PaginationVo<Activity> vo = new PaginationVo<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;

        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受到影响的条数（实际删除的数量）
        int count2 = activityRemarkDao.deleteByAids(ids);

        if (count1!=count2){
            flag = false;
        }
        //删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3!=ids.length){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        //取ulist
        List<User> uList = userDao.getUserList();
        //取a
        Activity a = activityDao.getById(id);
        //将ulist和a打包到map中
        Map<String,Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);

        //返回map就可以了
        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag = false;
        int count = activityDao.update(a);
        if (count != 1){
            flag = true;
        }
        return true;
    }

    @Override
    public Activity detail(String id) {
        Activity a = activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> list = activityRemarkDao.getRemarkListByAid(activityId);
        return list;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int count = activityRemarkDao.deleteRemark(id);
        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean saveRemarkBtn(ActivityRemark ar) {
        boolean flag = true;
        int count = activityRemarkDao.saveRemarkBtn(ar);
        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = true;
        int count = activityRemarkDao.updateRemark(ar);
        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityList(String clueId) {
        List<Activity> list = activityDao.getActivityList(clueId);
        return list;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        List<Activity> list = activityDao.getActivityListByNameAndNotByClueId(map);
        return list;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> list = activityDao.getActivityListByName(aname);
        return list;
    }


}
