package com.java.ZhiGe.service;

import com.java.ZhiGe.entity.Customer;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);

    // 删除多条
    void removeCustomers(List<Integer> ids);

    // 删除选中数据
    void removeCustomer(int id);

    // 修改数据
    void updateCustomer(Customer student);

    // 根据姓名模糊查询数据
    List<Customer> queryStudent(String name);
    // 加载全部数据（从文件中）
    List<Customer> getList();

    // 退出时保存数据
    void savaData();

    void exportData();
}
