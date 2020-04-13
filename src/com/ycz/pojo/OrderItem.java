package com.ycz.pojo;

/**
 * 
 * @ClassName OrderItem
 * @Description TODO(订单子项实体类)
 * @author Administrator
 * @Date 2020年4月12日 下午4:35:45
 * @version 1.0.0
 */
public class OrderItem {
    
    private Long id;//主键ID
    private Long orderId;//订单ID
    private Long foodId;//商品ID
    private String foodName;//商品名称
    private String foodImage;//商品图片
    private float price;//商品单价
    private int foodNum;//商品数量
    private float money;//商品总金额
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public Long getFoodId() {
        return foodId;
    }
    
    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }
    
    public String getFoodName() {
        return foodName;
    }
    
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
      
    public String getFoodImage() {
        return foodImage;
    }

    
    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public float getPrice() {
        return price;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }
    
    public int getFoodNum() {
        return foodNum;
    }
    
    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }
    
    public float getMoney() {
        return money;
    }
    
    public void setMoney(float money) {
        this.money = money;
    }
    
    

}
