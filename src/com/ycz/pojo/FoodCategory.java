package com.ycz.pojo;

/**
 * 
 * @ClassName FoodCategory
 * @Description TODO(��Ʒ������Ϣʵ��)
 * @author Administrator
 * @Date 2020��4��11�� ����3:25:31
 * @version 1.0.0
 */
public class FoodCategory {
    
    private Long id;//����ID
    private String name;//��Ʒ��������
    private String remark;//��ע����
    
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