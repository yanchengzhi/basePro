package com.ycz.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName Order
 * @Description TODO(����ʵ����)
 * @author Administrator
 * @Date 2020��4��12�� ����4:28:31
 * @version 1.0.0
 */
public class Order {
    
    private Long id;//����ID
    private Long accountId;//�ͻ�ID
    private Account account;//���������ͻ�
    private String receiveName;//�ջ���
    private String phone;//��ϵ��ʽ
    private String address;//סַ
    private int productNum;//��Ʒ����
    private float money;//�ܽ��
    private Date createTime;//�µ�ʱ��
    private String createTimeStr;
    private int status;//��Ʒ״̬��0Ϊδ���ͣ�1Ϊ�����У�2Ϊ�����
    private List<OrderItem> orderItems;//���������
    
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
