package com.ycz.pojo;

/**
 * 
 * @ClassName Account
 * @Description TODO(客户实体类)
 * @author Administrator
 * @Date 2020年4月12日 下午2:38:06
 * @version 1.0.0
 */
public class Account {
    
    private Long id;//客户主键
    private String name;//登录名
    private String password;//登录密码
    private String realName;//真实姓名
    private String phone;//手机号
    private String address;//住址
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRealName() {
        return realName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
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
    
    

}
