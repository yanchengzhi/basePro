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
import com.ycz.pojo.FoodCategory;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.FoodCategoryService;
import com.ycz.service.LogService;
import com.ycz.service.RoleService;
import com.ycz.util.MenuUtil;

@Controller
@RequestMapping("/foodCategory/")
public class FoodCategoryController {

    @Autowired
    private FoodCategoryService fService;

    @Autowired
    private RoleService rService;

    @Autowired
    private LogService lService;

    /**
     * @Description (分类管理主页)
     * @param request
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("food_category/list");
        String mid = request.getParameter("mid");
        // 获取角色对应的所有菜单
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        // 获取三级菜单
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        mav.addObject("thirdMenus", thirdMenus);
        return mav;
    }

    /**
     * @Description (添加菜品分类，含日志监控)
     * @param foodCategory
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(FoodCategory foodCategory, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            // 查询菜单分类名称是否重复
            FoodCategory cate = fService.queryByName(foodCategory.getName());
            if (cate == null) {
                // 不重复则可以添加
                fService.add(foodCategory);
                result.setSuccess(true);
                // 添加日志
                lService.addLog(role.getName(), currentUser.getUsername(),
                        "添加了新的菜品分类【" + foodCategory.getName() + "】！");
            } else {// 否则提示用户
                result.setData("该分类已存在！");
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
     * @Description (分页查询)
     * @param content
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String, Object> listData(@RequestParam(name = "name", required = false) String name, Page page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("pageSize", page.getRows());
        map.put("offset", page.getOffset());
        List<FoodCategory> findList = fService.findList(map);
        ret.put("rows", findList);
        // 获取总记录条数
        int total = fService.getTotal(map);
        ret.put("total", total);
        return ret;
    }

    /**
     * @Description (修改菜品分类信息)
     * @param foodCategory
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(FoodCategory foodCategory, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            fService.edit(foodCategory);
            result.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "修改了菜品分类【" + foodCategory.getName() + "】！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 
     * @Description (删除菜品分类信息)
     * @param id
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
            fService.delete(id);
            result2.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "删除了菜品分类【" + name + "】！");
        } catch (Exception e) {
            e.printStackTrace();
            result2.setData("无法删除，该分类下存在菜品信息！");
            result2.setSuccess(false);
        }
        return result2;
    }

}
