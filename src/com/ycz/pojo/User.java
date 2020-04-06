package com.ycz.pojo;

/**
 * 
 * @ClassName User
 * @Description TODO(用户实体pojo)
 * @author Administrator
 * @Date 2020年4月5日 下午9:29:54
 * @version 1.0.0
 */
public class User {
    
    private Long id;//ID，唯一主键
    private String username;//用户名
    private String password;//密码
    private String photo;//头像地址
    private String sex;//性别，0代表女，1代表男
    private Integer age;//年龄
    private String adderss;//住址
    
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
