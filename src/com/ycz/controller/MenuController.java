package com.ycz.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Page;
import com.ycz.service.MenuService;

/**
 * 
 * @ClassName MenuController
 * @Description TODO(菜单管理控制器)
 * @author Administrator
 * @Date 2020年4月5日 下午11:51:07
 * @version 1.0.0
 */

@Controller
@RequestMapping("/menu/")
public class MenuController {
    
    @Autowired
    private MenuService mService;
    
    /**
     * 
     * @Description (跳转到菜单主页)
     * @param mav
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav) {
        //获取父级菜单
        List<Menu> topList = mService.findTopList();
        mav.addObject("topList",topList);
        mav.setViewName("menu/list");
        return mav;
    }
    
    /**
     * 
     * @Description (菜单添加)
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(Menu menu) {
        AjaxResult result = new AjaxResult();
        try {
            if(menu.getParentId()==null) {
                menu.setParentId(-1L);
            }
            mService.add(menu);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (分页查询)
     * @param name
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String, Object> listData(
            @RequestParam(name="name",required= false ) String name,
            Page page
            ) {
        Map<String, Object> ret = new HashMap<String,Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("name",name);
        map.put("pageSize", page.getRows());
        map.put("offset",page.getOffset());
        List<Menu> findList = mService.findList(map);
        ret.put("rows",findList);
        //获取总记录条数
        int total = mService.getTotal(map);
        ret.put("total",total);
        return ret;
    }
    
    @ResponseBody
    @RequestMapping("getIcons")
    public AjaxResult getIcons(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        try {
            //获取应用的绝对路径
            String path = request.getServletContext().getRealPath("/");
            //基于图标文件夹建立文件流
            File file = new File(path+"/static/easyui/css/icons");
            if(!file.exists()) {
                result.setData("目录不存在！");
                return result;
            }
            File [] files = file.listFiles();
            List <String> icons = new ArrayList<>();
            for(File f:files) {
              if(f!=null && f.getName().contains(".png")) {
                  String str = f.getName().substring(0,f.getName().indexOf(".")).replace("_", "-");//截取字符串
                  icons.add("icon-"+str);
                  if(icons.size()==504) {//限定504个图标
                      break;
                  }
              } 
            }
            result.setData(icons);
            result.setSuccess(true);
        }catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
