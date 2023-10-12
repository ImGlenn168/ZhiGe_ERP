package com.java.ZhiGe.swing;

import com.java.ZhiGe.entity.Customer;
import com.java.ZhiGe.service.CustomerService;
import com.java.ZhiGe.service.impl.CustomerServiceImpl;
import com.java.ZhiGe.util.MyTableData;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private CustomerService customerService;
    // 数据存储在students集合中，关闭程序时存入文件，启动程序时将数据载入students
    private MainFrame mainFrame = this;
    JTable jTable;
    JScrollPane jScrollPane;
    JButton add, del, update, query, export;
    JTextField nameCheck;

    /**
     * 客户列表展示
     */
    public MainFrame() {
        // 提供增删改查方法的对象
        customerService = new CustomerServiceImpl();
        // 载入数据
        this.setBounds(500, 250, 800, 500);
        this.setTitle("客户管理");
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        this.addCom();
        this.showData(customerService.getList());
        this.setVisible(true);
    }

    /**
     * 主要控件
     */
    public void addCom() {
        jTable = new JTable();

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBounds(2, 2, 790, 300);

        add = new JButton("添加");
        add.setBounds(50, 390, 80, 25);

        update = new JButton("修改");
        update.setBounds(180, 390, 80, 25);

        del = new JButton("删除");
        del.setBounds(310, 390, 80, 25);

        query = new JButton("查询");
        query.setBounds(180, 340, 80, 25);

        export = new JButton("导出");
        export.setBounds(310, 340, 80, 25);

        nameCheck = new JTextField();
        nameCheck.setBounds(50, 340, 100, 25);

        addListener();
        add(add);
        add(update);
        add(del);
        add(query);
        add(export);
        add(nameCheck);
        add(jScrollPane);
    }

    // 不同的集合展示不同的数据
    public void showData(List<Customer> data) {
        // 设置表格数据模型
        jTable.setModel(MyTableData.getModel(data));
        // 表格数据变化时，修改数据
        jTable.getModel().addTableModelListener(e -> {
            customerService.updateCustomer(getCurrentCustomer());
        });
    }

    // 为控件添加事件监听
    public void addListener() {
        addCustomer();
        delCustomer();
        queryCustomer();
        updateCustomer();
        exportCustomer();
        // 关闭窗口时，保存数据到文件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO 使用数据库后无需存入文件
                customerService.savaData();
            }
        });
    }

    public void addCustomer() {
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AddAndUpdateFrame(mainFrame, 1);
            }
        });
    }

    public void delCustomer() {
        del.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int[] rows = jTable.getSelectedRows();
                List<Integer> ids = new ArrayList<>();
                for (int i : rows) {
                    int id = Integer.parseInt((String) jTable.getValueAt(i, 0));
                    ids.add(id);
                }
                customerService.removeCustomers(ids);
                showData(customerService.getList());
            }
        });
    }

    public void updateCustomer() {
        update.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getCurrentCustomer() != null) {
                    new AddAndUpdateFrame(mainFrame, 2, getCurrentCustomer());
                }
            }
        });
    }

    public void queryCustomer() {
        query.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<Customer> list = customerService.queryStudent(nameCheck.getText());
                showData(list);
            }
        });
    }

    /**
     * TODO 待实现
     */
    public void exportCustomer() {
        export.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                customerService.exportData();
            }
        });
    }

    // 获取当前选中的数据对象
    public Customer getCurrentCustomer() {
        int row = jTable.getSelectedRow();
        if (row == -1) {
            try {
                throw new Exception();
            } catch (Exception e) {
                new MsgFrame("请选择！");
                return null;
            }
        }
        return new Customer((String) jTable.getValueAt(row, 0), (String) jTable.getValueAt(row, 1),
                (String) jTable.getValueAt(row, 2), (String) jTable.getValueAt(row, 3), (String) jTable.getValueAt(row, 4));
    }
}
