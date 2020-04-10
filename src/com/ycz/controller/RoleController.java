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
 * @ClassName RoleController
 * @Description TODO(��ɫ������)
 * @author Administrator
 * @Date 2020��4��7�� ����5:24:04
 * @version 1.0.0
 */

@Controller
@RequestMapping("/role/")
public class RoleController {
    
    @Autowired
    private RoleService rService;
    
    @Autowired
    private MenuService mService;
    
    @Autowired
    private LogService lService;
    
    /**
     * 
     * @Description (������ɫ�б�ҳ��)
     * @param mav
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav,HttpServletRequest request) {
        String mid = request.getParameter("mid");
        //��ȡ��ɫ��Ӧ�����в˵�
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        //��ȡ�����˵�
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        mav.addObject("thirdMenus", thirdMenus);
        mav.setViewName("/role/list");
        return mav;
    }
    
    /**
     * 
     * @Description (��ӽ�ɫ)
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(Role role,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");//��ȡ��ǰ�û�
        Role role2 = rService.findRoleByRoleId(currentUser.getRoleId());//��ȡ��ǰ�û��Ľ�ɫ
        AjaxResult result = new AjaxResult();
        try {
            rService.add(role);
            result.setSuccess(true);
            lService.addLog(role2.getName(), currentUser.getUsername(), "Ϊϵͳ������½�ɫ��"+role.getName()+"����");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (��ɫ�޸�)
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Role role,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");//��ȡ��ǰ�û�
        Role role2 = rService.findRoleByRoleId(currentUser.getRoleId());//��ȡ��ǰ�û��Ľ�ɫ
        AjaxResult result = new AjaxResult();
        try {
            rService.edit(role);
            result.setSuccess(true);
            lService.addLog(role2.getName(), currentUser.getUsername(), "�޸���ϵͳ��ɫ��"+role.getName()+"����");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");//��ȡ��ǰ�û�
        Role role2 = rService.findRoleByRoleId(currentUser.getRoleId());//��ȡ��ǰ�û��Ľ�ɫ
        AjaxResult result2 = new AjaxResult();
        try {
            //��ȡ����ɾ���Ľ�ɫ
            Role role = rService.findRoleByRoleId(id);
            rService.deleteRole(id);
            result2.setSuccess(true);
            lService.addLog(role2.getName(), currentUser.getUsername(), "ɾ����ϵͳ��ɫ��"+role.getName()+"����");
        } catch (Exception e) {
            e.printStackTrace();
            result2.setData("�޷�ɾ�����ý�ɫ�´����û���Ȩ����Ϣ");
            result2.setSuccess(false);
        }
        return result2;
    }
    
    /**
     * 
     * @Description (��ҳ��ѯ)
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
        //��ȡ�ܼ�¼����
        int total = rService.getTotal(map);
        ret.put("total",total);
        return ret;
    }

}
