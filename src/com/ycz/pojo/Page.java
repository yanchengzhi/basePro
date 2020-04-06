package com.ycz.pojo;

/**
 * 
 * @ClassName Page
 * @Description TODO(此类封装分页信息)
 * @author Administrator
 * @Date 2020年4月6日 下午6:09:56
 * @version 1.0.0
 */
public class Page {
    
    private int page = 1;//当前页码
    private int rows;//每页记录条数
    private int offset;//偏移量
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getRows() {
        return rows;
    }
    
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public int getOffset() {
        this.offset = (page-1)*rows;
        return offset;
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }

    
}
