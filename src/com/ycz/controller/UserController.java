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
 * @Description TODO(�û�������)
 * @author Administrator
 * @Date 2020��4��8�� ����8:19:25
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
     * @Description (��ת���û�ҳ��)
     * @param mav
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav) {
        //��ѯ���н�ɫ
        List<Role> rList = rService.getAllRole();
        mav.addObject("roleList",rList);
        mav.setViewName("/user/list");
        return mav;
    }
    
    /**
     * 
     * @Description (��ҳ��ѯ�û�)
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
        //��ȡ�ܼ�¼����
        int total = uService.getTotal(map);
        ret.put("total",total);
        return ret;
    }
    
    /**
     * 
     * @Description (����û�)
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("addUser")
    public AjaxResult addUser(User user) {
        AjaxResult result = new AjaxResult();
        try {
            User u = uService.queryUserByName(user.getUsername());
            if(u==null) {//˵���û������ڣ��������
                uService.addUser(user);
                result.setSuccess(true);
            }else {//�û��������������
                result.setData("���û��Ѵ��ڣ�");
                result.setSuccess(false);
            }           
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("�û����ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }
    
    

}
