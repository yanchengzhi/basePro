package com.ycz.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.Account;
import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.Food;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Order;
import com.ycz.pojo.OrderItem;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.FoodService;
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
    
    @Autowired
    private FoodService fService;
    
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
    
    /**
     * 
     * @Description (�µ�)
     * @param ids
     * @param order
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("submitOrder")
    public AjaxResult submitOrder(String ids,Order order,HttpSession session) {
        //��ȡ��¼�Ŀͻ�
        Account account = (Account) session.getAttribute("account");
        AjaxResult result = new AjaxResult();
        try {
            if(ids.equals("")) {//�жϿͻ��Ƿ���
                result.setData("��ѡ���Ʒ�µ���");//û������ѿͻ�
                result.setSuccess(false);
            }else {
                if(ids.endsWith(";")) {//ȥ��ĩβ�ֺ�
                    ids=ids.substring(0,ids.length()-1);
                }
                String []strs = ids.split(";");//һ�ηָ��Ʒ��Ӧ��������һ��
                int productNum = 0;
                float money = 0;
                List<OrderItem> items = new ArrayList<>();
                for(String s:strs) {
                    //���ηָ��Ʒ�������ֿ�
                    String [] strs2 = s.split(",");
                    //��ѯID��Ӧ�Ĳ�Ʒ
                    Food food = fService.queryById(Long.parseLong(strs2[0]));
                    productNum+=Long.parseLong(strs2[1]);//��Ʒ������
                    money += food.getPrice() * Long.parseLong(strs2[1]);//�ܽ��
                    OrderItem item = new OrderItem();
                    item.setFoodId(food.getId());
                    item.setFoodName(food.getName());
                    item.setFoodImage(food.getImageUrl());
                    item.setPrice(food.getPrice());
                    item.setFoodNum(Integer.parseInt(strs2[1]));
                    item.setMoney(food.getPrice() * Long.parseLong(strs2[1]));
                    items.add(item);
                }
                order.setAccountId(account.getId());
                order.setProductNum(productNum);
                order.setMoney(money);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createTimeStr = sdf.format(new Date());
                order.setCreateTimeStr(createTimeStr);
                order.setStatus(0);
                oService.add(order);
                for(OrderItem oi:items) {
                    oi.setOrderId(order.getId());
                    oService.addItem(oi);
                    fService.updateSales(oi.getFoodId(),Long.valueOf(oi.getFoodNum()));
                }
                result.setSuccess(true);
                lService.addLog("�ͻ�", account.getName(), "�ѳɹ��µ���");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("�µ�ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }

}
