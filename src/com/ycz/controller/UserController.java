package com.ycz.controller;

import java.io.File;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.LogService;
import com.ycz.service.MenuService;
import com.ycz.service.RoleService;
import com.ycz.service.UserService;
import com.ycz.util.MenuUtil;

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
    
    @Autowired
    private MenuService mService;
    
    @Autowired
    private LogService lService;
    
    /**
     * 
     * @Description (跳转到用户页面)
     * @param mav
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav,HttpServletRequest request) {
        String mid = request.getParameter("mid");
        //获取角色对应的所有菜单
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        List<Role> roleList = rService.getAllRole();
        mav.addObject("roleList",roleList);
        mav.addObject("thirdMenus", thirdMenus);
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
    public AjaxResult addUser(User user,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        AjaxResult result = new AjaxResult();
        try {
            User u = uService.queryUserByName(user.getUsername());
            if(u==null) {//说明用户不存在，可以添加
                uService.addUser(user);
                result.setSuccess(true);
                Role role = rService.findRoleByRoleId(currentUser.getRoleId());
                lService.addLog(role.getName(), currentUser.getUsername(), "添加了一条用户名为【" + user.getUsername() +"】的新记录！");
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
    
    /**
     * 
     * @Description (修改用户)
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("editUser")
    public AjaxResult editUser(User user,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        AjaxResult result = new AjaxResult();
        try {
               uService.editUser(user);
               result.setSuccess(true);
               Role role = rService.findRoleByRoleId(currentUser.getRoleId());
               lService.addLog(role.getName(), currentUser.getUsername(), "修改了用户【" + user.getUsername() +"】的信息！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("用户添加失败！");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (批量删除用户)
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteUsers")
    public AjaxResult deleteUsers(String ids,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result2 = new AjaxResult();
        try {
            ids=ids.substring(0,ids.length()-1);//先去掉尾部逗号
            //查询出被删除的用户
            List <User> uList = uService.selectUser(ids);
            //然后执行删除操作
            uService.deleteUser(ids);
            result2.setSuccess(true);
            for(User u:uList) {
                lService.addLog(role.getName(), currentUser.getUsername(), "删除了用户【" + u.getUsername() +"】的信息！"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            result2.setSuccess(false);
        }
        return result2;
    }
    
    /**
     * 
     * @Description (图片上传)
     * @param photo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("uploadPhoto")
    public AjaxResult uploadPhoto(MultipartFile photo,HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        //先判断图片是否为空
        if(photo==null) {
            result.setData("请选择要上传的图片！");
            result.setSuccess(false);
        }else {//再判断图片大小是否超过10M
            if(photo.getSize()>1024*1024*10) {
                result.setData("图片大小超过10M！");
                result.setSuccess(false);
            }else {
                //获取文件后缀名（格式）
                String suffix = photo.getOriginalFilename().substring(
                        photo.getOriginalFilename().lastIndexOf(".")+1);
                //判断是否为图片
                if(!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
                    result.setData("请选择jpg,jpeg,gif,png的图片格式文件！");
                    result.setSuccess(false);
                }else {
                    //获取图片保存路径
                    String path = request.getServletContext().getRealPath("/") + "/static/upload/images/";
                    File file = new File(path);
                    //判断目录是否存在
                    if(!file.exists()) {
                        file.mkdirs();
                    }
                    //使用时间戳命名文件，避免重复
                    String fileName = new Date().getTime()+"."+suffix;
                    try {
                        //保存文件
                        photo.transferTo(new File(path+fileName));
                        //获取文件绝对路径
                        String name = request.getServletContext().getContextPath() + "/static/upload/images/" + fileName;
                        result.setData(name);
                        result.setSuccess(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.setData("文件保存失败！");
                        result.setSuccess(false);
                    } 
                }
            }
            
        }          
        return result;
    }
    
    
    

}
