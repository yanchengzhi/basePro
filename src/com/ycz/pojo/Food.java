package com.ycz.pojo;

/**
 * 
 * @ClassName Food
 * @Description TODO(菜品实体类)
 * @author Administrator
 * @Date 2020年4月11日 下午6:30:25
 * @version 1.0.0
 */
public class Food {
    
    private Long id;//菜品主键ID
    private String name;//菜品名称
    private Long categoryId;//所属分类ID
    private float price;//菜品价格
    private Integer sales = 0;//销量,默认为0
    private String imageUrl;//菜品图片地址
    private String description;//菜品描述
    
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
