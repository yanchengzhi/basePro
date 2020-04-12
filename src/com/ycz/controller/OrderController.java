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
import com.ycz.pojo.Order;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.LogService;
import com.ycz.service.OrderService;
import com.ycz.service.RoleService;
import com.ycz.util.MenuUtil;

@Controller
@RequestMapping("/order/")
public class OrderController {
    
    @Autowired
    private OrderService oService;
    
    @Autowired
    private RoleService rService;
    
    @Autowired
    private LogService lService;
    
    /**
     * 
     * @Description (��������ҳ��)
     * @param request
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("order/list");
        String mid = request.getParameter("mid");
        // ��ȡ��ɫ��Ӧ�����в˵�
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        // ��ȡ�����˵�
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        mav.addObject("thirdMenus", thirdMenus);
        //��ѯ���ж���״̬
        return mav;
    }
    
    /**
     * 
     * @Description (��ҳ��ѯ)
     * @param name
     * @param status
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String, Object> listData(
            @RequestParam(name = "name", required = false) String name, 
            @RequestParam(name="status",required = false) Integer status,Page page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("status", status);
        map.put("pageSize", page.getRows());
        map.put("offset", page.getOffset());
        List<Order> findList = oService.findList(map);
        ret.put("rows", findList);
        // ��ȡ�ܼ�¼����
        int total = oService.getTotal(map);
        ret.put("total", total);
        return ret;
    }
    
    /**
     * 
     * @Description (�����޸�)
     * @param order
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Order order, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
              oService.edit(order);  
              result.setSuccess(true);
              lService.addLog(role.getName(), currentUser.getUsername(), "�޸��ˡ�" + order.getReceiveName() + "���Ķ�����Ϣ��Ϣ��");
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("�޸�ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (����ɾ��)
     * @param id
     * @param receiveName
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id, String receiveName,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result2 = new AjaxResult();
        try {
            oService.delete(id);
            result2.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "ɾ���ˡ�" + receiveName + "���Ķ�����");
        } catch (Exception e) {
            e.printStackTrace();
            result2.setData("�޷�ɾ�����ö����´����Ӷ�����Ϣ������ɾ����ز�Ʒ��Ϣ��");
            result2.setSuccess(false);
        }
        return result2;
    }

}
