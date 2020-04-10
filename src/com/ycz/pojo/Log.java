package com.ycz.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName Log
 * @Description TODO(此类作为日志类来使用)
 * @author Administrator
 * @Date 2020年4月10日 下午6:52:16
 * @version 1.0.0
 */
public class Log {
    
    private Long id;//主键ID
    private String content;//日志内容
    private Date createTime;//创建时间
    private String createTimeStr;//创建时间的字符串表示
    
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
