package com.java.ZhiGe.dao;

import com.java.ZhiGe.entity.Customer;
import com.java.ZhiGe.util.DBUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    private PreparedStatement pstmt;
    private int i;

    public List<Customer> findCustomerByName(String cname) {
        List<Customer> customers = new ArrayList<>();
        //?为占位符
        String sql = "select * from customer where cname like ? ;";
        pstmt = DBUtil.getPstmt(sql);
        try {
            pstmt.setString(1, "%" + cname + "%");
            // 分割出sql，取第二部分 select * from customer where cname = 'xxx' ;
            String[] split = pstmt.toString().split(":");
            String sql2 = split[1];
            ResultSet rs = pstmt.executeQuery(sql2); //执行查询语句
            while (rs.next()) {
                addCustomerList(customers, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private void addCustomerList(List<Customer> customers, ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCid(rs.getString("cid"));
        customer.setCname(rs.getString("cname"));
        customer.setPhoneNumber(rs.getString("phoneNumber"));
        customer.setWeChat(rs.getString("weChat"));
        customer.setNote(rs.getString("note"));
        customers.add(customer);
    }

    public List<Customer> findAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        //?为占位符
        String sql = "select * from customer ;";
        pstmt = DBUtil.getPstmt(sql);
        try {
            // 分割出sql，取第二部分 select * from customer where cname = 'xxx' ;
            String[] split = pstmt.toString().split(":");
            String sql2 = split[1];
            ResultSet rs = pstmt.executeQuery(sql2); //执行查询语句
            while (rs.next()) {
                addCustomerList(customers, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public int addCustomer(Customer customer) {
        //?为占位符
        String sql = "insert into customer(`cid`, `cname`, `phoneNumber`, `weChat`, `note`) values (?,?,?,?,?) ;";
        pstmt = DBUtil.getPstmt(sql);
        try {
            pstmt.setString(1, customer.getCid());
            pstmt.setString(2, customer.getCname());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setString(4, customer.getWeChat());
            pstmt.setString(5, customer.getNote());
            // 分割出sql，取第二部分 select * from customer where cname = 'xxx' ;
            String[] split = pstmt.toString().split(":");
            String sql2 = split[1];
            i = pstmt.executeUpdate(sql2);//执行新增语句
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int updateCustomer(Customer customer) {
        //?为占位符
        String sql = "update customer set cid=?, cname=? , phoneNumber=? , weChat=? , note=? where cid=?;";
        pstmt = DBUtil.getPstmt(sql);
        try {
            pstmt.setString(1, customer.getCid());
            pstmt.setString(2, customer.getCname());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setString(4, customer.getWeChat());
            pstmt.setString(5, customer.getNote());
            pstmt.setString(6, String.valueOf(customer.getCid()));
            // 分割出sql，取第二部分 select * from customer where cname = 'xxx' ;
            String[] split = pstmt.toString().split(":");
            String sql2 = split[1];
            i = pstmt.executeUpdate(sql2);//执行新增语句
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public void removeCustomer(int id) {
        //?为占位符
        String sql = "delete from customer where cid = ? ; ";
        pstmt = DBUtil.getPstmt(sql);
        try {
            pstmt.setString(1, String.valueOf(id));
            // 分割出sql，取第二部分 select * from customer where cname = 'xxx' ;
            String[] split = pstmt.toString().split(":");
            String sql2 = split[1];
            i = pstmt.executeUpdate();//执行新增语句
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
