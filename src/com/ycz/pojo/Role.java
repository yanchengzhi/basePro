package com.ycz.pojo;

/**
 * 
 * @ClassName Role
 * @Description TODO(角色pojo)
 * @author Administrator
 * @Date 2020年4月7日 下午5:21:20
 * @version 1.0.0
 */
public class Role {
    
    private Long id;//主键ID
    private String name;//角色名称
    private String remark;//角色备注
    
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
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    

}
