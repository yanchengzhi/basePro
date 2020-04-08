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
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.RoleService;
import com.ycz.service.UserService;

/**
 * 
 * @ClassName UserController
 * @Description TODO(用户控制器)
 * @author Administrator
 * @Date 2020年4月8日 下午8:19:25
 * @version 1.0.0
 */
@Controller
@RequestMapping("/user/")
public class UserController {
    
    @Autowired
    private UserService uService;
    
    @Autowired
    private RoleService rService;
    
    /**
     * 
     * @Description (跳转到用户页面)
     * @param mav
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav) {
        //查询所有角色
        List<Role> rList = rService.getAllRole();
        mav.addObject("roleList",rList);
        mav.setViewName("/user/list");
        return mav;
    }
    
    /**
     * 
     * @Description (分页查询用户)
     * @param username
     * @param roleId
     * @param sex
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String, Object> listData(
            @RequestParam(name="username",required= false ) String username,
            @RequestParam(name="roleId",required = false) Long roleId,
            @RequestParam(name="sex",required = false) Integer sex,
            Page page
            ) {
        Map<String, Object> ret = new HashMap<String,Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("username",username);
        map.put("roleId",roleId);
        map.put("sex",sex);
        map.put("pageSize", page.getRows());
        map.put("offset",page.getOffset());
        List<User> findList = uService.findList(map);
        ret.put("rows",findList);
        //获取总记录条数
        int total = uService.getTotal(map);
        ret.put("total",total);
        return ret;
    }
    
    /**
     * 
     * @Description (添加用户)
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("addUser")
    public AjaxResult addUser(User user) {
        AjaxResult result = new AjaxResult();
        try {
            User u = uService.queryUserByName(user.getUsername());
            if(u==null) {//说明用户不存在，可以添加
                uService.addUser(user);
                result.setSuccess(true);
            }else {//用户存在则不允许添加
                result.setData("该用户已存在！");
                result.setSuccess(false);
            }           
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("用户添加失败！");
            result.setSuccess(false);
        }
        return result;
    }
    
    

}
