package com.workbench.dao;

import com.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran t);

    int getTotal(Map<String, Object> map);

    List<Tran> getTranList(Map<String, Object> map);

    Tran detail(String id);

    int changeStage(Tran t);

    int Total();

    List<Map<String, Object>> Charts();
}
