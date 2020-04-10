package com.ycz.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.LogService;
import com.ycz.service.MenuService;
import com.ycz.service.RoleService;
import com.ycz.util.MenuUtil;

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
    
    @Autowired
    private RoleService rService;
    
    @Autowired
    private LogService lService;
    
    
    /**
     * 
     * @Description (跳转到菜单主页)
     * @param mav
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav,HttpServletRequest request) {
        String mid = request.getParameter("mid");
        //获取角色对应的所有菜单
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        //获取三级菜单
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        //获取顶级菜单
        List<Menu> topList = mService.findTopList();
        mav.addObject("topList",topList);
        mav.addObject("thirdMenus", thirdMenus);
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
    public AjaxResult add(Menu menu,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            if(menu.getParentId()==null) {
                menu.setParentId(0L);
            }
            mService.add(menu);
            result.setSuccess(true);
            if(menu.getParentId()==0) {
                lService.addLog(role.getName(), currentUser.getUsername(), "为系统添加了顶级菜单【"+menu.getName()+"】！");
            }else{
                //获取所有的顶级菜单
                List<Menu> mList = mService.findTopList();
                StringBuilder sb = new StringBuilder("");
                for(Menu m:mList) {
                    sb.append(m.getId()).append(",");
                }
                if(sb.toString().contains(menu.getParentId().toString())) {
                    //获取该二级菜单的父菜单
                    Menu pMenu = mService.findMenu(menu.getParentId());
                    lService.addLog(role.getName(), currentUser.getUsername(), "为顶级菜单【"+pMenu.getName()+"】添加了二级菜单【"+menu.getName()+"】！");
                }else {
                    //获取该三级菜单的父菜单（二级菜单）
                    Menu pMenu = mService.findMenu(menu.getParentId());
                    lService.addLog(role.getName(), currentUser.getUsername(), "为二级菜单【"+pMenu.getName()+"】添加了按钮【"+menu.getName()+"】！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (菜单修改)
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Menu menu,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            if(menu.getParentId()==null) {
                menu.setParentId(0L);
            }
            mService.edit(menu);
            result.setSuccess(true);
            if(menu.getParentId()==0) {
                lService.addLog(role.getName(), currentUser.getUsername(), "修改了顶级菜单【"+menu.getName()+"】！");
            }else{
                //获取所有的顶级菜单
                List<Menu> mList = mService.findTopList();
                StringBuilder sb = new StringBuilder("");
                for(Menu m:mList) {
                    sb.append(m.getId()).append(",");
                }
                if(sb.toString().contains(menu.getParentId().toString())) {
                    //获取该二级菜单的父菜单
                    Menu pMenu = mService.findMenu(menu.getParentId());
                    lService.addLog(role.getName(), currentUser.getUsername(), "修改了顶级菜单【"+pMenu.getName()+"】下的二级菜单【"+menu.getName()+"】！");
                }else {
                    //获取该三级菜单的父菜单（二级菜单）
                    Menu pMenu = mService.findMenu(menu.getParentId());
                    lService.addLog(role.getName(), currentUser.getUsername(), "修改了二级菜单【"+pMenu.getName()+"】下的按钮【"+menu.getName()+"】！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (菜单删除)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result2 = new AjaxResult();
            //查询是否为父级菜单
            List<Menu> childrenList = mService.findChildren(id);
            if(childrenList.size()==0) {//不为父级菜单执行删除
                //获取被删除的菜单
                Menu dMenu = mService.findMenu(id);
                mService.deleteMenu(id);
                result2.setSuccess(true);
                if(dMenu.getParentId()==0) {
                    lService.addLog(role.getName(), currentUser.getUsername(), "删除了顶级菜单【"+dMenu.getName()+"】！");
                }else{
                    //获取所有的顶级菜单
                    List<Menu> mList = mService.findTopList();
                    StringBuilder sb = new StringBuilder("");
                    for(Menu m:mList) {
                        sb.append(m.getId()).append(",");
                    }
                    if(sb.toString().contains(dMenu.getParentId().toString())) {
                        //获取该二级菜单的父菜单
                        Menu pMenu = mService.findMenu(dMenu.getParentId());
                        lService.addLog(role.getName(), currentUser.getUsername(), "删除了顶级菜单【"+pMenu.getName()+"】下的二级菜单【"+dMenu.getName()+"】！");
                    }else {
                        //获取该三级菜单的父菜单（二级菜单）
                        Menu pMenu = mService.findMenu(dMenu.getParentId());
                        lService.addLog(role.getName(), currentUser.getUsername(), "删除了二级菜单【"+pMenu.getName()+"】下的按钮【"+dMenu.getName()+"】！");
                    }
                }
            }else {
                result2.setData("无法删除父级菜单！");
                result2.setSuccess(false);
            }
        return result2;
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
    
    /**
     * 
     * @Description (获取图标名称，传给前端)
     * @param request
     * @return
     */
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
