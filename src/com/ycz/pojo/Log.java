package com.ycz.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName Log
 * @Description TODO(������Ϊ��־����ʹ��)
 * @author Administrator
 * @Date 2020��4��10�� ����6:52:16
 * @version 1.0.0
 */
public class Log {
    
    private Long id;//����ID
    private String content;//��־����
    private Date createTime;//����ʱ��
    private String createTimeStr;//����ʱ����ַ�����ʾ
    
    public String getCreateTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(createTime!=null) {
            createTimeStr = sdf.format(createTime);
        }
        return createTimeStr;
    }

    
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
