package com.ycz.pojo;

/**
 * 
 * @ClassName OrderItem
 * @Description TODO(��������ʵ����)
 * @author Administrator
 * @Date 2020��4��12�� ����4:35:45
 * @version 1.0.0
 */
public class OrderItem {
    
    private Long id;//����ID
    private Long orderId;//����ID
    private Long foodId;//��ƷID
    private String foodName;//��Ʒ����
    private String foodImage;//��ƷͼƬ
    private float price;//��Ʒ����
    private int foodNum;//��Ʒ����
    private float money;//��Ʒ�ܽ��
    
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
