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
     * @Description (跳往订单页面)
     * @param request
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("order/list");
        String mid = request.getParameter("mid");
        // 获取角色对应的所有菜单
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        // 获取三级菜单
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        mav.addObject("thirdMenus", thirdMenus);
        //查询所有订单状态
        return mav;
    }
    
    /**
     * 
     * @Description (分页查询)
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
        // 获取总记录条数
        int total = oService.getTotal(map);
        ret.put("total", total);
        return ret;
    }
    
    /**
     * 
     * @Description (订单修改)
     * @param order
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Order order, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
              oService.edit(order);  
              result.setSuccess(true);
              lService.addLog(role.getName(), currentUser.getUsername(), "修改了【" + order.getReceiveName() + "】的订单信息信息！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("修改失败！");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (订单删除)
     * @param id
     * @param receiveName
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id, String receiveName,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // 查询该用户的角色
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result2 = new AjaxResult();
        try {
            oService.delete(id);
            result2.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "删除了【" + receiveName + "】的订单！");
        } catch (Exception e) {
            e.printStackTrace();
            result2.setData("无法删除！该订单下存在子订单信息，请先删除相关菜品信息！");
            result2.setSuccess(false);
        }
        return result2;
    }
    
    /**
     * 
     * @Description (下单)
     * @param ids
     * @param order
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("submitOrder")
    public AjaxResult submitOrder(String ids,Order order,HttpSession session) {
        //获取登录的客户
        Account account = (Account) session.getAttribute("account");
        AjaxResult result = new AjaxResult();
        try {
            if(ids.equals("")) {//判断客户是否点菜
                result.setData("请选择菜品下单！");//没点菜提醒客户
                result.setSuccess(false);
            }else {
                if(ids.endsWith(";")) {//去掉末尾分号
                    ids=ids.substring(0,ids.length()-1);
                }
                String []strs = ids.split(";");//一次分割，商品对应数量放在一起
                int productNum = 0;
                float money = 0;
                List<OrderItem> items = new ArrayList<>();
                for(String s:strs) {
                    //二次分割将菜品和数量分开
                    String [] strs2 = s.split(",");
                    //查询ID对应的菜品
                    Food food = fService.queryById(Long.parseLong(strs2[0]));
                    productNum+=Long.parseLong(strs2[1]);//商品总数量
                    money += food.getPrice() * Long.parseLong(strs2[1]);//总金额
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
                lService.addLog("客户", account.getName(), "已成功下单！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("下单失败！");
            result.setSuccess(false);
        }
        return result;
    }

}
