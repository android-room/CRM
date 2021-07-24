package com.settings.service.impl;

import com.settings.dao.DicTypeDao;
import com.settings.dao.DicValueDao;
import com.settings.domain.DicType;
import com.settings.domain.DicValue;
import com.settings.service.DicService;
import com.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> map = new HashMap<>();

        //将字典类型列表去除
        List<DicType> dicTypes = dicTypeDao.getTypeList();
        //将字典类别遍历
        for (DicType dt : dicTypes){
            //取得每一个类型的字典类型编码
            String code = dt.getCode();

            //根据每一个字典类型来取得字典值的列表
            List<DicValue> dvList = dicValueDao.getListByCode(code);
            map.put(code,dvList);
        }
        return map;
    }
}
