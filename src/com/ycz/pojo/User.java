package com.ycz.pojo;

/**
 * 
 * @ClassName User
 * @Description TODO(�û�ʵ��pojo)
 * @author Administrator
 * @Date 2020��4��5�� ����9:29:54
 * @version 1.0.0
 */
public class User {
    
    private Long id;//ID��Ψһ����
    private String username;//�û���
    private String password;//����
    private String photo;//ͷ���ַ
    private String sex;//�Ա�0����Ů��1������
    private Integer age;//����
    private String adderss;//סַ
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getAdderss() {
        return adderss;
    }
    
    public void setAdderss(String adderss) {
        this.adderss = adderss;
    }
    
    

}
