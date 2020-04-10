package com.ycz.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ycz.pojo.Menu;

/**
 * 
 * @ClassName MenuUtil
 * @Description TODO(工具类)
 * @author Administrator
 * @Date 2020年4月10日 下午1:09:08
 * @version 1.0.0
 */
public class MenuUtil {
    
    /**
     * 
     * @Description (获取所有顶级菜单)
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
     * @Description (获取所有二级菜单)
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
     * @Description (获取所有的三级菜单)
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
