package com.workbench.service;

import com.vo.PaginationVo;
import com.workbench.domain.Clue;
import com.workbench.domain.Tran;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ClueService {
    boolean save(Clue c);

    PaginationVo<Clue> pageList(Map<String, Object> map);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId, Tran t, String createBy);
}
