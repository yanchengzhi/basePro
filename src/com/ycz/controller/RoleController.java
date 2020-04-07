package com.ycz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ycz.service.RoleService;

/**
 * 
 * @ClassName RoleController
 * @Description TODO(角色控制器)
 * @author Administrator
 * @Date 2020年4月7日 下午5:24:04
 * @version 1.0.0
 */

@Controller
@RequestMapping("/role/")
public class RoleController {
    
    @Autowired
    private RoleService rService;
    
    /**
     * 
     * @Description (跳往角色列表页面)
     * @param mav
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav) {
        mav.setViewName("/role/list");
        return mav;
    }
    
    /**
     * 
     * @Description (添加角色)
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(Role role) {
        AjaxResult result = new AjaxResult();
        try {
            rService.add(role);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (角色修改)
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Role role) {
        AjaxResult result = new AjaxResult();
        try {
            rService.edit(role);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id) {
        AjaxResult result2 = new AjaxResult();
        try {
            rService.deleteRole(id);
            result2.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
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
        List<Menu> findList = rService.findList(map);
        ret.put("rows",findList);
        //获取总记录条数
        int total = rService.getTotal(map);
        ret.put("total",total);
        return ret;
    }

}
