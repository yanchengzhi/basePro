package com.ycz.pojo;

/**
 * 
 * @ClassName Food
 * @Description TODO(��Ʒʵ����)
 * @author Administrator
 * @Date 2020��4��11�� ����6:30:25
 * @version 1.0.0
 */
public class Food {
    
    private Long id;//��Ʒ����ID
    private String name;//��Ʒ����
    private Long categoryId;//��������ID
    private float price;//��Ʒ�۸�
    private Integer sales = 0;//����,Ĭ��Ϊ0
    private String imageUrl;//��ƷͼƬ��ַ
    private String description;//��Ʒ����
    
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
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public float getPrice() {
        return price;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }
    
    public Integer getSales() {
        return sales;
    }
    
    public void setSales(Integer sales) {
        this.sales = sales;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    

}
