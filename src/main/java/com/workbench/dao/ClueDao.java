package com.workbench.dao;


import com.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int save(Clue c);


    List<Clue> getClueListByCondition(Map<String, Object> map);

    int getToTalByCondition(Map<String, Object> map);

    Clue detail(String id);

    Clue getById(String clueId);

    int delete(String clueId);
}
