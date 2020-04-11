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
import com.ycz.pojo.FoodCategory;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Page;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.FoodCategoryService;
import com.ycz.service.LogService;
import com.ycz.service.RoleService;
import com.ycz.util.MenuUtil;

@Controller
@RequestMapping("/foodCategory/")
public class FoodCategoryController {

    @Autowired
    private FoodCategoryService fService;

    @Autowired
    private RoleService rService;

    @Autowired
    private LogService lService;

    /**
     * @Description (���������ҳ)
     * @param request
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("food_category/list");
        String mid = request.getParameter("mid");
        // ��ȡ��ɫ��Ӧ�����в˵�
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        // ��ȡ�����˵�
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        mav.addObject("thirdMenus", thirdMenus);
        return mav;
    }

    /**
     * @Description (��Ӳ�Ʒ���࣬����־���)
     * @param foodCategory
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(FoodCategory foodCategory, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            // ��ѯ�˵����������Ƿ��ظ�
            FoodCategory cate = fService.queryByName(foodCategory.getName());
            if (cate == null) {
                // ���ظ���������
                fService.add(foodCategory);
                result.setSuccess(true);
                // �����־
                lService.addLog(role.getName(), currentUser.getUsername(),
                        "������µĲ�Ʒ���ࡾ" + foodCategory.getName() + "����");
            } else {// ������ʾ�û�
                result.setData("�÷����Ѵ��ڣ�");
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
     * @Description (��ҳ��ѯ)
     * @param content
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String, Object> listData(@RequestParam(name = "name", required = false) String name, Page page) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("pageSize", page.getRows());
        map.put("offset", page.getOffset());
        List<FoodCategory> findList = fService.findList(map);
        ret.put("rows", findList);
        // ��ȡ�ܼ�¼����
        int total = fService.getTotal(map);
        ret.put("total", total);
        return ret;
    }

    /**
     * @Description (�޸Ĳ�Ʒ������Ϣ)
     * @param foodCategory
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(FoodCategory foodCategory, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        // ��ѯ���û��Ľ�ɫ
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            fService.edit(foodCategory);
            result.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "�޸��˲�Ʒ���ࡾ" + foodCategory.getName() + "����");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 
     * @Description (ɾ����Ʒ������Ϣ)
     * @param id
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
            fService.delete(id);
            result2.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "ɾ���˲�Ʒ���ࡾ" + name + "����");
        } catch (Exception e) {
            e.printStackTrace();
            result2.setData("�޷�ɾ�����÷����´��ڲ�Ʒ��Ϣ��");
            result2.setSuccess(false);
        }
        return result2;
    }

}
