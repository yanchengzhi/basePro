package com.ycz.controller;

import java.io.File;
import java.util.ArrayList;
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
 * @ClassName MenuController
 * @Description TODO(�˵����������)
 * @author Administrator
 * @Date 2020��4��5�� ����11:51:07
 * @version 1.0.0
 */

@Controller
@RequestMapping("/menu/")
public class MenuController {
    
    @Autowired
    private MenuService mService;
    
    @Autowired
    private RoleService rService;
    
    @Autowired
    private LogService lService;
    
    
    /**
     * 
     * @Description (��ת���˵���ҳ)
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
        //��ȡ�����˵�
        List<Menu> topList = mService.findTopList();
        mav.addObject("topList",topList);
        mav.addObject("thirdMenus", thirdMenus);
        mav.setViewName("menu/list");
        return mav;
    }
    
    /**
     * 
     * @Description (�˵����)
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(Menu menu,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            if(menu.getParentId()==null) {
                menu.setParentId(0L);
            }
            mService.add(menu);
            result.setSuccess(true);
            if(menu.getParentId()==0) {
                lService.addLog(role.getName(), currentUser.getUsername(), "Ϊϵͳ����˶����˵���"+menu.getName()+"����");
            }else{
                //��ȡ���еĶ����˵�
                List<Menu> mList = mService.findTopList();
                StringBuilder sb = new StringBuilder("");
                for(Menu m:mList) {
                    sb.append(m.getId()).append(",");
                }
                if(sb.toString().contains(menu.getParentId().toString())) {
                    //��ȡ�ö����˵��ĸ��˵�
                    Menu pMenu = mService.findMenu(menu.getParentId());
                    lService.addLog(role.getName(), currentUser.getUsername(), "Ϊ�����˵���"+pMenu.getName()+"������˶����˵���"+menu.getName()+"����");
                }else {
                    //��ȡ�������˵��ĸ��˵��������˵���
                    Menu pMenu = mService.findMenu(menu.getParentId());
                    lService.addLog(role.getName(), currentUser.getUsername(), "Ϊ�����˵���"+pMenu.getName()+"������˰�ť��"+menu.getName()+"����");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (�˵��޸�)
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("edit")
    public AjaxResult edit(Menu menu,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result = new AjaxResult();
        try {
            if(menu.getParentId()==null) {
                menu.setParentId(0L);
            }
            mService.edit(menu);
            result.setSuccess(true);
            if(menu.getParentId()==0) {
                lService.addLog(role.getName(), currentUser.getUsername(), "�޸��˶����˵���"+menu.getName()+"����");
            }else{
                //��ȡ���еĶ����˵�
                List<Menu> mList = mService.findTopList();
                StringBuilder sb = new StringBuilder("");
                for(Menu m:mList) {
                    sb.append(m.getId()).append(",");
                }
                if(sb.toString().contains(menu.getParentId().toString())) {
                    //��ȡ�ö����˵��ĸ��˵�
                    Menu pMenu = mService.findMenu(menu.getParentId());
                    lService.addLog(role.getName(), currentUser.getUsername(), "�޸��˶����˵���"+pMenu.getName()+"���µĶ����˵���"+menu.getName()+"����");
                }else {
                    //��ȡ�������˵��ĸ��˵��������˵���
                    Menu pMenu = mService.findMenu(menu.getParentId());
                    lService.addLog(role.getName(), currentUser.getUsername(), "�޸��˶����˵���"+pMenu.getName()+"���µİ�ť��"+menu.getName()+"����");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 
     * @Description (�˵�ɾ��)
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(Long id,HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Role role = rService.findRoleByRoleId(currentUser.getRoleId());
        AjaxResult result2 = new AjaxResult();
            //��ѯ�Ƿ�Ϊ�����˵�
            List<Menu> childrenList = mService.findChildren(id);
            if(childrenList.size()==0) {//��Ϊ�����˵�ִ��ɾ��
                //��ȡ��ɾ���Ĳ˵�
                Menu dMenu = mService.findMenu(id);
                mService.deleteMenu(id);
                result2.setSuccess(true);
                if(dMenu.getParentId()==0) {
                    lService.addLog(role.getName(), currentUser.getUsername(), "ɾ���˶����˵���"+dMenu.getName()+"����");
                }else{
                    //��ȡ���еĶ����˵�
                    List<Menu> mList = mService.findTopList();
                    StringBuilder sb = new StringBuilder("");
                    for(Menu m:mList) {
                        sb.append(m.getId()).append(",");
                    }
                    if(sb.toString().contains(dMenu.getParentId().toString())) {
                        //��ȡ�ö����˵��ĸ��˵�
                        Menu pMenu = mService.findMenu(dMenu.getParentId());
                        lService.addLog(role.getName(), currentUser.getUsername(), "ɾ���˶����˵���"+pMenu.getName()+"���µĶ����˵���"+dMenu.getName()+"����");
                    }else {
                        //��ȡ�������˵��ĸ��˵��������˵���
                        Menu pMenu = mService.findMenu(dMenu.getParentId());
                        lService.addLog(role.getName(), currentUser.getUsername(), "ɾ���˶����˵���"+pMenu.getName()+"���µİ�ť��"+dMenu.getName()+"����");
                    }
                }
            }else {
                result2.setData("�޷�ɾ�������˵���");
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
        List<Menu> findList = mService.findList(map);
        ret.put("rows",findList);
        //��ȡ�ܼ�¼����
        int total = mService.getTotal(map);
        ret.put("total",total);
        return ret;
    }
    
    /**
     * 
     * @Description (��ȡͼ�����ƣ�����ǰ��)
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("getIcons")
    public AjaxResult getIcons(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        try {
            //��ȡӦ�õľ���·��
            String path = request.getServletContext().getRealPath("/");
            //����ͼ���ļ��н����ļ���
            File file = new File(path+"/static/easyui/css/icons");
            if(!file.exists()) {
                result.setData("Ŀ¼�����ڣ�");
                return result;
            }
            File [] files = file.listFiles();
            List <String> icons = new ArrayList<>();
            for(File f:files) {
              if(f!=null && f.getName().contains(".png")) {
                  String str = f.getName().substring(0,f.getName().indexOf(".")).replace("_", "-");//��ȡ�ַ���
                  icons.add("icon-"+str);
              } 
            }
            result.setData(icons);
            result.setSuccess(true);
        }catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
