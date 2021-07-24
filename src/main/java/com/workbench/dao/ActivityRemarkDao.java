package com.workbench.dao;

import com.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    /**
     *
     * @param activityId
     * @return
     */
    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemark(String id);

    int saveRemarkBtn(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}
