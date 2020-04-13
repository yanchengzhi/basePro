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

import com.ycz.pojo.Account;
import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Order;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.AccountService;
import com.ycz.service.LogService;
import com.ycz.service.OrderService;
import com.ycz.service.RoleService;
import com.ycz.util.MenuUtil;
/**
 * 
 * @ClassName AccountController
 * @Description TODO(客户控制器)
 * @author Administrator
 * @Date 2020年4月12日 下午3:04:39
 * @version 1.0.0
 */
@Controller
@RequestMapping("/account/")
public class AccountController {
    
    @Autowired
    private AccountService acService;
    
    @Autowired
    private RoleService rService;
    
    @Autowired
    private LogService lService;
    
    @Autowired
    private OrderService oService;
    
    /**
     * 
     * @Description (跳往登录界面)
     * @return
     */
    @RequestMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("/home/user/login");
        return mav;
    }
    
    /**
     * 
     * @Description (跳往用户中心首页)
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        ModelAndView mav = new ModelAndView();
        if(account==null) {
            mav.setViewName("/home/user/login");
        }else {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("accountId", account.getId());
            map.put("offset",0);
            map.put("pageSize",9999);
            List<Order> oList = oService.findList(map);
            mav.addObject("orderList",oList);
            mav.setViewName("/home/user/index");
        }
        return mav;
    }
    
    /**
     * 
     * @Description (跳往注册界面)
     * @return
     */
    @RequestMapping("registe")
    public ModelAndView registe() {
        ModelAndView mav = new ModelAndView("/home/user/registe");
        return mav;
    }
    
    
    /**
     * 
     * @Description (跳转到客户页面)
     * @param request
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("account/list");
        String mid = request.getParameter("mid");
        // 获取角色对应的所有菜单
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        // 获取三级菜单
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        mav.addObject("thirdMenus", thirdMenus);
        return mav;
    }
    
    /**
     * 
     * @Description (分页查询)
     * @param name
     * @param realName
     * @param address
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String, Object> listData(
            @RequestParam(name = "name", required = false) String name, 
            @RequestParam(name="realName",required = false) String realName,
            @RequestParam(name="address",required = false) String address,Page page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("realName", realName);
        map.put("address",address);
        map.put("pageSize", page.getRows());
        map.put("offset", page.getOffset());
        List<Account> findList = acService.findList(map);
        ret.put("rows", findList);
        // 获取总记录条数
        int total = acService.getTotal(map);
        ret.put("total", total);
        return ret;
    }
    
    /**
     * 
     * @Description (添加客户)
     * @param account
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(Account account, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            // 查询客户登录名称是否重复
            Account acco = acService.queryByName(account.getName());
            if (acco == null) {
                // 不重复则可以添加
                acService.add(account);
                result.setSuccess(true);
                // 添加日志
                lService.addLog(role.getName(), currentUser.getUsername(),
                        "添加了新客户【" + account.getName() + "】！");
            } else {// 否则提示用户
                result.setData("该客户已存在！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("添加失败！");
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Account account, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            //查询用户名是否已存在
            Account acco = acService.queryByName(account.getName());
            if(acco==null) {
                acService.edit(account);
                result.setSuccess(true);
                lService.addLog(role.getName(), currentUser.getUsername(), "修改了客户【" + account.getName() + "】的信息！");
            }else {
               result.setData("用户名已被占用！");
               result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("修改失败！");
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id, String name,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result2 = new AjaxResult();
        try {
            acService.delete(id);
            result2.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "删除了客户【" + name + "】！");
        } catch (Exception e) {
            e.printStackTrace();
            result2.setData("无法删除！该客户下存在订单信息，请先删除订单！");
            result2.setSuccess(false);
        }
        return result2;
    }
    
    @ResponseBody
    @RequestMapping("getUserAddress")
    public AjaxResult getUserAddress(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
            //判断客户是否登录
            Account account = (Account)request.getSession().getAttribute("account");
            if(account==null) {
                result.setData("用户未登陆，请先登录！");
                result.setSuccess(false);
            }else {
                result.setData(account);
                result.setSuccess(true);
            }
        return result;
    }
    
    /**
     * 
     * @Description (客户注册账号)
     * @param account
     * @return
     */
    @ResponseBody
    @RequestMapping("registeDo")
    public AjaxResult registeDo(Account account) {
        AjaxResult result = new AjaxResult();
        //查询用户名是否被注册
        Account acco = acService.queryByName(account.getName());
        if(acco==null) {//可以注册
            acService.add(account);
            result.setSuccess(true);
        }else {//用户名被占用
            result.setData("用户名被占用！");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (登录验证)
     * @param account
     * @return
     */
    @ResponseBody
    @RequestMapping("loginDo")
    public AjaxResult loginDo(Account account,HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        Account acco = acService.validateAccount(account);
        if(acco==null) {//验证不通过
            //进一步判断
            Account acco2 = acService.queryByName(account.getName());
            if(acco2==null) {//用户尚未注册
                result.setData("该用户尚未注册，请先进行注册！");
                result.setSuccess(false);
            }else {//密码错误
                result.setData("密码错误，请重新输入！");
                result.setSuccess(false); 
            }
        }else {//验证通过
            //登录成功，存到session域中
            request.getSession().setAttribute("account", acco);
            result.setSuccess(true);
        }
        return result;
    }
    
    /**
     * 
     * @Description (修改配送信息)
     * @param account
     * @return
     */
    @ResponseBody
    @RequestMapping("updateInfo")
    public AjaxResult updateInfo(Account account,HttpSession session) {
        //获取当前用户
        Account acco = (Account) session.getAttribute("account");   
        AjaxResult result = new AjaxResult();
        try {
            acco.setRealName(account.getRealName());
            acco.setPassword(account.getPassword());
            acco.setPhone(account.getPhone());
            acco.setAddress(account.getAddress());
            acService.edit(acco);
            result.setSuccess(true);
            lService.addLog("客户", acco.getName(), "修改了自己的基本信息！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
