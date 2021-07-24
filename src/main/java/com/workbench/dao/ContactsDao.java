package com.workbench.dao;

import com.workbench.domain.Contacts;
import com.workbench.domain.Customer;

import java.util.List;

public interface ContactsDao {

    int save(Contacts con);

    List<Contacts> getCustomerListName(String bName);
}
