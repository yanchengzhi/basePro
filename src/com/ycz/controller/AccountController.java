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
import com.ycz.pojo.Food;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.AccountService;
import com.ycz.service.LogService;
import com.ycz.service.RoleService;
import com.ycz.util.MenuUtil;
/**
 * 
 * @ClassName AccountController
 * @Description TODO(�ͻ�������)
 * @author Administrator
 * @Date 2020��4��12�� ����3:04:39
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
    
    /**
     * 
     * @Description (��ת���ͻ�ҳ��)
     * @param request
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("account/list");
        String mid = request.getParameter("mid");
        // ��ȡ��ɫ��Ӧ�����в˵�
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        // ��ȡ�����˵�
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        mav.addObject("thirdMenus", thirdMenus);
        return mav;
    }
    
    /**
     * 
     * @Description (��ҳ��ѯ)
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
        // ��ȡ�ܼ�¼����
        int total = acService.getTotal(map);
        ret.put("total", total);
        return ret;
    }
    
    /**
     * 
     * @Description (��ӿͻ�)
     * @param account
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(Account account, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            // ��ѯ�ͻ���¼�����Ƿ��ظ�
            Account acco = acService.queryByName(account.getName());
            if (acco == null) {
                // ���ظ���������
                acService.add(account);
                result.setSuccess(true);
                // �����־
                lService.addLog(role.getName(), currentUser.getUsername(),
                        "������¿ͻ���" + account.getName() + "����");
            } else {// ������ʾ�û�
                result.setData("�ÿͻ��Ѵ��ڣ�");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("���ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Account account, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            //��ѯ�û����Ƿ��Ѵ���
            Account acco = acService.queryByName(account.getName());
            if(acco==null) {
                acService.edit(account);
                result.setSuccess(true);
                lService.addLog(role.getName(), currentUser.getUsername(), "�޸��˿ͻ���" + account.getName() + "������Ϣ��");
            }else {
               result.setData("�û����ѱ�ռ�ã�");
               result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("�޸�ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id, String name,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result2 = new AjaxResult();
        try {
            acService.delete(id);
            result2.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "ɾ���˿ͻ���" + name + "����");
        } catch (Exception e) {
            e.printStackTrace();
            result2.setData("�޷�ɾ�����ÿͻ��´��ڶ�����Ϣ������ɾ��������");
            result2.setSuccess(false);
        }
        return result2;
    }

}
