package com.ycz.pojo;

/**
 * 
 * @ClassName Menu
 * @Description TODO(菜单实体pojo)
 * @author Administrator
 * @Date 2020年4月6日 下午1:32:37
 * @version 1.0.0
 */
public class Menu {
    
    private Long id;//主键ID
    private Long parentId;//父类ID
    private String name;//菜单名称
    private String url;//url地址
    private String icon;//图标
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    

}
