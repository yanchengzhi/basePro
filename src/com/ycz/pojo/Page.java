package com.ycz.pojo;

/**
 * 
 * @ClassName Page
 * @Description TODO(�����װ��ҳ��Ϣ)
 * @author Administrator
 * @Date 2020��4��6�� ����6:09:56
 * @version 1.0.0
 */
public class Page {
    
    private int page = 1;//��ǰҳ��
    private int rows;//ÿҳ��¼����
    private int offset;//ƫ����
    
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
