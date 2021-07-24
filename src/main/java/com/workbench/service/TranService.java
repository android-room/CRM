package com.workbench.service;

import com.vo.PaginationVo;
import com.workbench.domain.*;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<Activity> getActivityListByName(String aname);

    List<Contacts> getCustomerListName(String bName);

    boolean save(Tran t, String customerName);

    PaginationVo<Tran> pageList(Map<String, Object> map);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean changeStage(Tran t);

    Map<String, Object> getCharts();
}
