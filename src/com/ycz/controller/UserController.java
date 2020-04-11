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
    
    @Autowired
    private MenuService mService;
    
    @Autowired
    private LogService lService;
    
    /**
     * 
     * @Description (��ת���û�ҳ��)
     * @param mav
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav,HttpServletRequest request) {
        String mid = request.getParameter("mid");
        //��ȡ��ɫ��Ӧ�����в˵�
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
    public AjaxResult addUser(User user,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        AjaxResult result = new AjaxResult();
        try {
            User u = uService.queryUserByName(user.getUsername());
            if(u==null) {//˵���û������ڣ��������
                uService.addUser(user);
                result.setSuccess(true);
                Role role = rService.findRoleByRoleId(currentUser.getRoleId());
                lService.addLog(role.getName(), currentUser.getUsername(), "�����һ���û���Ϊ��" + user.getUsername() +"�����¼�¼��");
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
    
    /**
     * 
     * @Description (�޸��û�)
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
               lService.addLog(role.getName(), currentUser.getUsername(), "�޸����û���" + user.getUsername() +"������Ϣ��");
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("�û����ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (����ɾ���û�)
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
            ids=ids.substring(0,ids.length()-1);//��ȥ��β������
            //��ѯ����ɾ�����û�
            List <User> uList = uService.selectUser(ids);
            //Ȼ��ִ��ɾ������
            uService.deleteUser(ids);
            result2.setSuccess(true);
            for(User u:uList) {
                lService.addLog(role.getName(), currentUser.getUsername(), "ɾ�����û���" + u.getUsername() +"������Ϣ��"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            result2.setSuccess(false);
        }
        return result2;
    }
    
    /**
     * 
     * @Description (ͼƬ�ϴ�)
     * @param photo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("uploadPhoto")
    public AjaxResult uploadPhoto(MultipartFile photo,HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        //���ж�ͼƬ�Ƿ�Ϊ��
        if(photo==null) {
            result.setData("��ѡ��Ҫ�ϴ���ͼƬ��");
            result.setSuccess(false);
        }else {//���ж�ͼƬ��С�Ƿ񳬹�10M
            if(photo.getSize()>1024*1024*10) {
                result.setData("ͼƬ��С����10M��");
                result.setSuccess(false);
            }else {
                //��ȡ�ļ���׺������ʽ��
                String suffix = photo.getOriginalFilename().substring(
                        photo.getOriginalFilename().lastIndexOf(".")+1);
                //�ж��Ƿ�ΪͼƬ
                if(!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
                    result.setData("��ѡ��jpg,jpeg,gif,png��ͼƬ��ʽ�ļ���");
                    result.setSuccess(false);
                }else {
                    //��ȡͼƬ����·��
                    String path = request.getServletContext().getRealPath("/") + "/static/upload/images/";
                    File file = new File(path);
                    //�ж�Ŀ¼�Ƿ����
                    if(!file.exists()) {
                        file.mkdirs();
                    }
                    //ʹ��ʱ��������ļ��������ظ�
                    String fileName = new Date().getTime()+"."+suffix;
                    try {
                        //�����ļ�
                        photo.transferTo(new File(path+fileName));
                        //��ȡ�ļ�����·��
                        String name = request.getServletContext().getContextPath() + "/static/upload/images/" + fileName;
                        result.setData(name);
                        result.setSuccess(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        result.setData("�ļ�����ʧ�ܣ�");
                        result.setSuccess(false);
                    } 
                }
            }
            
        }          
        return result;
    }
    
    
    

}
