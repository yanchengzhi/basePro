package com.ycz.pojo;

/**
 * 
 * @ClassName Authority
 * @Description TODO(Ȩ��pojo)
 * @author Administrator
 * @Date 2020��4��7�� ����7:00:19
 * @version 1.0.0
 */
public class Authority {
    
    private Long id;//����ID
    private Long roleId;//��ɫID
    private Long menuId;//�˵�ID

    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public Long getMenuId() {
        return menuId;
    }
    
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    
}
