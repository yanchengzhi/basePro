package com.ycz.controller;

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
import com.ycz.pojo.Food;
import com.ycz.pojo.FoodCategory;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.FoodCategoryService;
import com.ycz.service.FoodService;
import com.ycz.service.LogService;
import com.ycz.service.RoleService;
import com.ycz.util.MenuUtil;

/**
 * 
 * @ClassName FoodController
 * @Description TODO(菜品控制器)
 * @author Administrator
 * @Date 2020年4月11日 下午6:12:00
 * @version 1.0.0
 */
@Controller
@RequestMapping("/food/")
public class FoodController {
    
    @Autowired
    private FoodService foodService;
    
    @Autowired
    private FoodCategoryService fService;
    
    @Autowired
    private RoleService rService;
    
    @Autowired
    private LogService lService;
    
    /**
     * 
     * @Description (跳往菜品信息主页)
     * @param request
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("food/list");
        String mid = request.getParameter("mid");
        // 获取角色对应的所有菜单
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        // 获取三级菜单
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        mav.addObject("thirdMenus", thirdMenus);
        Map<String, Object>map = new HashMap<String, Object>();
        map.put("offset", 0);
        map.put("pageSize",9999);
        //查询所有的菜品分类
        List<FoodCategory> cateList = fService.findList(map);
        mav.addObject("cateList",cateList);
        return mav;
    }
    
    /**
     * 
     * @Description (分页查询)
     * @param name
     * @param categoryId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String, Object> listData(
            @RequestParam(name = "name", required = false) String name, 
            @RequestParam(name="categoryId",required = false)Integer categoryId, Page page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("categoryId", categoryId);
        map.put("pageSize", page.getRows());
        map.put("offset", page.getOffset());
        List<Food> findList = foodService.findList(map);
        ret.put("rows", findList);
        // 获取总记录条数
        int total = foodService.getTotal(map);
        ret.put("total", total);
        return ret;
    }
    
    /**
     * 
     * @Description (添加菜品)
     * @param food
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(Food food, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            // 查询菜单分类名称是否重复
            Food oFood = foodService.queryByName(food.getName());
            if (oFood == null) {
                // 不重复则可以添加
                foodService.add(food);
                result.setSuccess(true);
                // 添加日志
                lService.addLog(role.getName(), currentUser.getUsername(),
                        "添加了新的菜品【" + food.getName() + "】！");
            } else {// 否则提示用户
                result.setData("该菜品已存在！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("添加失败！");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (修改菜品)
     * @param food
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Food food, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            foodService.edit(food);
            result.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "修改了菜品【" + food.getName() + "】！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (修改菜品)
     * @param id
     * @param name
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id, String name,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result2 = new AjaxResult();
        try {
            foodService.delete(id);
            result2.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "删除了菜品【" + name + "】！");
        } catch (Exception e) {
            e.printStackTrace();
            result2.setData("删除失败！");
            result2.setSuccess(false);
        }
        return result2;
    }

}
