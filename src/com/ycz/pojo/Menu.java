package com.ycz.pojo;

/**
 * 
 * @ClassName Menu
 * @Description TODO(�˵�ʵ��pojo)
 * @author Administrator
 * @Date 2020��4��6�� ����1:32:37
 * @version 1.0.0
 */
public class Menu {
    
    private Long id;//����ID
    private Long parentId;//����ID
    private String name;//�˵�����
    private String url;//url��ַ
    private String icon;//ͼ��
    
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
