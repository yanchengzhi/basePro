package com.ycz.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName Order
 * @Description TODO(订单实体类)
 * @author Administrator
 * @Date 2020年4月12日 下午4:28:31
 * @version 1.0.0
 */
public class Order {
    
    private Long id;//订单ID
    private Long accountId;//客户ID
    private Account account;//订单所属客户
    private String receiveName;//收货人
    private String phone;//联系方式
    private String address;//住址
    private int productNum;//商品数量
    private float money;//总金额
    private Date createTime;//下单时间
    private String createTimeStr;
    private int status;//商品状态：0为未配送，1为配送中，2为已完成
    private List<OrderItem> orderItems;//订单子项集合
    
    public String getCreateTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(createTime!=null) {
            createTimeStr = sdf.format(new Date());
        }
        return createTimeStr;
    }
 
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getReceiveName() {
        return receiveName;
    }
    
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getProductNum() {
        return productNum;
    }
    
    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }
    
    public float getMoney() {
        return money;
    }
    
    public void setMoney(float money) {
        this.money = money;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }

    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    
    public Long getAccountId() {
        return accountId;
    }

    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    
    public Account getAccount() {
        return account;
    }

    
    public void setAccount(Account account) {
        this.account = account;
    }
    
       
}
