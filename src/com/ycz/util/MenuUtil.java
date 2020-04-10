package com.ycz.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ycz.pojo.Menu;

/**
 * 
 * @ClassName MenuUtil
 * @Description TODO(������)
 * @author Administrator
 * @Date 2020��4��10�� ����1:09:08
 * @version 1.0.0
 */
public class MenuUtil {
    
    /**
     * 
     * @Description (��ȡ���ж����˵�)
     * @param mList
     * @return
     */
    public static List<Menu> getAllTopMenus(List <Menu> mList) {
        List<Menu> thirdMenus = new ArrayList<>();
        for(Menu m:mList) {
            if(m.getParentId()==0) {
                thirdMenus.add(m);
            }
        }
        return thirdMenus;
    }
    
    /**
     * 
     * @Description (��ȡ���ж����˵�)
     * @param mList
     * @return
     */
    public static List<Menu> getAllSecondMenus(List <Menu> mList) {
        List<Menu> topMenu = getAllTopMenus(mList);
        List<Menu> secondMenus = new ArrayList<>();
        for(Menu m:mList) {
            for(Menu tm:topMenu) {
                if(m.getParentId()==tm.getId()) {
                    secondMenus.add(m);
                }
            }
        }
        return secondMenus;
    }
    
    /**
     * 
     * @Description (��ȡ���е������˵�)
     * @param mList
     * @param request
     * @return
     */
    public static List<Menu> getAllThirdMenus(List <Menu> mList,Long secondMenuId) {
        List<Menu> thirdMenus = new ArrayList<>();
        for(Menu m:mList) {
            if(m.get_parentId()==secondMenuId) {
                thirdMenus.add(m);
            }
        }
        return thirdMenus;
    }
    
}
