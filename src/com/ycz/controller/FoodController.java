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
import com.ycz.pojo.Food;
import com.ycz.pojo.FoodCategory;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.FoodCategoryService;
import com.ycz.service.FoodService;
import com.ycz.service.LogService;
import com.ycz.service.RoleService;
import com.ycz.util.MenuUtil;

/**
 * 
 * @ClassName FoodController
 * @Description TODO(��Ʒ������)
 * @author Administrator
 * @Date 2020��4��11�� ����6:12:00
 * @version 1.0.0
 */
@Controller
@RequestMapping("/food/")
public class FoodController {
    
    @Autowired
    private FoodService foodService;
    
    @Autowired
    private FoodCategoryService fService;
    
    @Autowired
    private RoleService rService;
    
    @Autowired
    private LogService lService;
    
    /**
     * 
     * @Description (������Ʒ��Ϣ��ҳ)
     * @param request
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("food/list");
        String mid = request.getParameter("mid");
        // ��ȡ��ɫ��Ӧ�����в˵�
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        // ��ȡ�����˵�
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        mav.addObject("thirdMenus", thirdMenus);
        Map<String, Object>map = new HashMap<String, Object>();
        map.put("offset", 0);
        map.put("pageSize",9999);
        //��ѯ���еĲ�Ʒ����
        List<FoodCategory> cateList = fService.findList(map);
        mav.addObject("cateList",cateList);
        return mav;
    }
    
    /**
     * 
     * @Description (��ҳ��ѯ)
     * @param name
     * @param categoryId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String, Object> listData(
            @RequestParam(name = "name", required = false) String name, 
            @RequestParam(name="categoryId",required = false)Integer categoryId, Page page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("categoryId", categoryId);
        map.put("pageSize", page.getRows());
        map.put("offset", page.getOffset());
        List<Food> findList = foodService.findList(map);
        ret.put("rows", findList);
        // ��ȡ�ܼ�¼����
        int total = foodService.getTotal(map);
        ret.put("total", total);
        return ret;
    }
    
    /**
     * 
     * @Description (��Ӳ�Ʒ)
     * @param food
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(Food food, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            // ��ѯ�˵����������Ƿ��ظ�
            Food oFood = foodService.queryByName(food.getName());
            if (oFood == null) {
                // ���ظ���������
                foodService.add(food);
                result.setSuccess(true);
                // �����־
                lService.addLog(role.getName(), currentUser.getUsername(),
                        "������µĲ�Ʒ��" + food.getName() + "����");
            } else {// ������ʾ�û�
                result.setData("�ò�Ʒ�Ѵ��ڣ�");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("���ʧ�ܣ�");
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (�޸Ĳ�Ʒ)
     * @param food
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Food food, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            foodService.edit(food);
            result.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "�޸��˲�Ʒ��" + food.getName() + "����");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (�޸Ĳ�Ʒ)
     * @param id
     * @param name
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id, String name,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result2 = new AjaxResult();
        try {
            foodService.delete(id);
            result2.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "ɾ���˲�Ʒ��" + name + "����");
        } catch (Exception e) {
            e.printStackTrace();
            result2.setData("ɾ��ʧ�ܣ�");
            result2.setSuccess(false);
        }
        return result2;
    }

}
