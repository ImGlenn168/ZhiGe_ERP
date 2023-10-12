package com.java.ZhiGe.util;


import com.java.ZhiGe.entity.Customer;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MyTableData {
    public static DefaultTableModel getModel(List<Customer> customers) {
        String[] columnNames = new String[]{"编号", "姓名", "电话", "微信","备注"};

        String[][] data = new String[customers.size()][columnNames.length];
        for (int i = 0; i < customers.size(); i++) {
            data[i][0] = customers.get(i).getCid();
            data[i][1] = customers.get(i).getCname();
            data[i][2] = customers.get(i).getPhoneNumber();
            data[i][3] = customers.get(i).getWeChat();
            data[i][4] = customers.get(i).getNote();
        }

        return new DefaultTableModel(data, columnNames);
    }
}
