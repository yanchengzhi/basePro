package com.ycz.pojo;

/**
 * 
 * @ClassName Authority
 * @Description TODO(权限pojo)
 * @author Administrator
 * @Date 2020年4月7日 下午7:00:19
 * @version 1.0.0
 */
public class Authority {
    
    private Long id;//主键ID
    private Long roleId;//角色ID
    private Long menuId;//菜单ID

    
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
