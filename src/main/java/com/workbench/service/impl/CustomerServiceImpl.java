package com.workbench.service.impl;

import com.utils.SqlSessionUtil;
import com.workbench.dao.ContactsDao;
import com.workbench.dao.CustomerDao;
import com.workbench.domain.Customer;
import com.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {

        List<String> list = customerDao.getCustomerName(name);

        return list;
    }


}
