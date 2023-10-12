package com.java.ZhiGe.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.java.ZhiGe.dao.CustomerDao;
import com.java.ZhiGe.entity.Customer;
import com.java.ZhiGe.service.CustomerService;
import com.java.ZhiGe.swing.MsgFrame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao = new CustomerDao();

    private final File file = new File("data");

    /**
     * 新增客户信息
     *
     * @param customer
     */
    @Override
    public void addCustomer(Customer customer) {
        int i = customerDao.addCustomer(customer);
        if (i > 0) {
            new MsgFrame("客户新增成功！");
        } else {
            new MsgFrame("客户新增失败！");
        }
    }

    /**
     * 修改客户信息
     *
     * @param customer
     */
    @Override
    public void updateCustomer(Customer customer) {
        List<Customer> allCustomer = customerDao.findAllCustomer();
        boolean flag = false;
        for (Customer customer1 : allCustomer) {
            if (customer1.getCid().equals(customer.getCid())) {
                flag = true;
            }
        }
        if (flag) {
            customerDao.updateCustomer(customer);
        } else {
            new MsgFrame("修改失败！");
        }
    }

    /**
     * @param ids
     * @return
     */
    @Override
    public void removeCustomers(List<Integer> ids) {
        int count = 0;
        for (Integer id : ids) {
            customerDao.removeCustomer(id);
            count += 1;
        }
        if (count > 0) {
            new MsgFrame("删除成功！");
        } else {
            new MsgFrame("删除失败");
        }
    }

    @Override
    public void removeCustomer(int id) {
        customerDao.removeCustomer(id);
    }

    /**
     * 查询客户信息
     *
     * @param name
     * @return
     */
    @Override
    public List<Customer> queryStudent(String name) {
        if (StrUtil.isBlank(name)) {
            return customerDao.findAllCustomer();
        }
        return customerDao.findCustomerByName(name);
    }

    @Override
    public List<Customer> getList() {
        return customerDao.findAllCustomer();
    }

    /**
     * 保存客户信息到本地
     */
    @Override
    public void savaData() {

    }

    @Override
    public void exportData() {
        //1、创建一个文件对象
        File excelFile = new File("D:/客户信息.xlsx");
        //2、判断文件是否存在，不存在则创建一个Excel文件
        if (!excelFile.exists()) {
            try {
                excelFile.createNewFile();//创建一个新的文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //3、指定需要那个class去写。然后写到第一个sheet，名字为模版，然后文件流会自动关闭
        EasyExcel.write(excelFile, Customer.class).sheet("订单模版").doWrite(queryToExcel());
        new MsgFrame("文件已存入D:/客户信息.xlsx");
    }

    public List<Customer> queryToExcel() {
        //业务代码,获取数据集
        List<Customer> customers = customerDao.findAllCustomer();
        List<Customer> excels = new ArrayList<>();
        //遍历数据集，导出Excel
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            Customer data = new Customer();
            data.setCid(customer.getCid());
            data.setCname(customer.getCname());
            data.setPhoneNumber(customer.getPhoneNumber());
            data.setWeChat(customer.getWeChat());
            data.setNote(customer.getNote());
            excels.add(data);
        }
        return excels;
    }

}
