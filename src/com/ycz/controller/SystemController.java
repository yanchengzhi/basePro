package com.ycz.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.Authority;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Role;
import com.ycz.pojo.User;
import com.ycz.service.AuthorityService;
import com.ycz.service.LogService;
import com.ycz.service.MenuService;
import com.ycz.service.RoleService;
import com.ycz.service.UserService;
import com.ycz.util.CpachaUtil;
import com.ycz.util.MenuUtil;

/**
 * @ClassName SystemController
 * @Description TODO(ϵͳ������)
 * @author Administrator
 * @Date 2020��4��5�� ����5:50:30
 * @version 1.0.0
 */

@Controller
@RequestMapping("/system/")
public class SystemController {
    
    @Autowired
    private UserService uService;
    
    @Autowired
    private RoleService rService;
    
    @Autowired
    private AuthorityService aService;
    
    @Autowired
    private MenuService mService;
    
    @Autowired
    private LogService lService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        //ȡ���������е��û��˵�
        List<Menu> mList = (List<Menu>) request.getSession().getAttribute("mList");
        ModelAndView mav = new ModelAndView("system/index");
        //��ȡ�����˵�
        List<Menu> topList = MenuUtil.getAllTopMenus(mList);
        //��ȡ�����˵�
        List<Menu> secList = MenuUtil.getAllSecondMenus(mList);
        mav.addObject("topMenus",topList);
        mav.addObject("secondMenus",secList);
        return mav;
    }
    
    /**
     * 
     * @Description (�û��˳�)
     * @param request
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();//��ȡ������
        session.invalidate();//������ʧЧ
        return "redirect:login";
    }
    

    /**
     * 
     * @Description (��ת����¼����)
     * @param mav
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("system/login");
        return mav;
    }
    
    /**
     * 
     * @Description (����������֤��)
     * @param codeLen �ַ�����
     * @param width �������
     * @param height �����߶�
     * @param cpachaType ��֤���ַ���
     * @param request
     * @param response
     */
    @RequestMapping(value = "getCpacha",method = RequestMethod.GET)
    public void getCpacha(
            @RequestParam(name ="len",required = false,defaultValue = "4")Integer codeLen,
            @RequestParam(name = "width",required = false,defaultValue = "100")Integer width,
            @RequestParam(name = "height",required = false,defaultValue = "30")Integer height,
            @RequestParam(name = "type",required = true,defaultValue = "loginCpacha")String cpachaType,
            HttpServletRequest request,HttpServletResponse response) {
        //��������
        CpachaUtil cUtil = new CpachaUtil(codeLen,width,height);
        //���ȡ�ַ�������֤���ַ���
        String code = cUtil.generatorVCode();
        //�浽�Ự����
        request.getSession().setAttribute(cpachaType, code);
        BufferedImage image = cUtil.generatorRotateVCodeImage(code, true);
        try {
            //��ͼƬ����ʽд������
            ImageIO.write(image,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 
     * @Description (��¼��֤)
     * @param user ǰ�˷��صķ�װ����
     * @param cpacha ǰ�˴���������֤���ַ���
     * @return
     */
    @ResponseBody
    @RequestMapping("loginDo")
    public AjaxResult loginDo(User user,String cpacha,HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User dbUser = uService.queryUser(user);         
            if(dbUser!=null) {//��Ϊ��˵����֤ͨ��
                //��֤����
                //��ȡҳ��������ɵ���֤��             
                Object loginCpacha = request.getSession().getAttribute("loginCpacha");
                if(loginCpacha==null) {
                    result.setData("�Ự��ʱ����ˢ��ҳ�棡");
                    result.setSuccess(false);
                }else {
                    if(cpacha.equalsIgnoreCase(loginCpacha.toString())) {
                        //��֤��ͨ���������û���¼
                        request.getSession().setAttribute("currentUser", dbUser);//�浽�Ự����
                        //��ѯ���û�ӵ�еĽ�ɫ
                        Role role = rService.findRoleByRoleId(dbUser.getRoleId());
                        //��ѯ�ý�ɫӵ�е�Ȩ��
                        List<Authority> authList = aService.findListByRoleId(role.getId());
                        //��ȡȨ�޶�Ӧ�Ĳ˵�ID
                        StringBuilder sb = new StringBuilder("");
                        for(Authority auth:authList) {
                            sb.append(auth.getMenuId()).append(",");
                        }
                        //ȥ�����һ������
                        String ids = sb.toString().substring(0,sb.toString().length()-1);
                        List <Menu> mList = mService.findMenuList(ids);
                        Set<String> uriSet = new HashSet<>();
                        for(Menu m:mList) {
                            if(m.getUrl()!=null && !"".equals(m.getUrl())) {              
                                uriSet.add(request.getSession().getServletContext().getContextPath()+"/"+m.getUrl());
                            }
                        }                      
                        //����ɫ�Ͳ˵���Ϣ�浽�Ự���к��û���
                        request.getSession().setAttribute("role", role);
                        request.getSession().setAttribute("mList", mList);
                        //���û��ĺϷ�url�浽session����
                        request.getSession().setAttribute("uriSet", uriSet);
                        lService.addLog(role.getName(), dbUser.getUsername(), "��½ϵͳ�ɹ���");
                        result.setSuccess(true);
                    }else {
                        result.setData("��֤�����");
                        result.setSuccess(false);
                    }
                }
            }else {//��֤��ͨ������ʾ�û���Ϣ
                //��ѯ�û����Ƿ����
                User loginUser = uService.queryUserByName(user.getUsername());
                if(loginUser==null) {                  
                    result.setData("�û��������ڣ�");
                }else {
                    result.setData("�������");
                }
                result.setSuccess(false);
            }
        return result;
    }
    
    /**
     * 
     * @Description (���������޸�ҳ��)
     * @param mav
     * @return
     */
    @RequestMapping("resetPassword")
    public ModelAndView resetPassword(ModelAndView mav) {
        mav.setViewName("system/edit_password");
        return mav;
    }
    
    @ResponseBody
    @RequestMapping("resetPasswordDo")
    public AjaxResult resetPasswordDo(String password,HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        try {
            //��ȡ��ǰ�û�
            User currentUser = (User) request.getSession().getAttribute("currentUser");
            Role role = rService.findRoleByRoleId(currentUser.getRoleId());
            currentUser.setPassword(password);
            uService.resetPass(currentUser);
            result.setSuccess(true);
            lService.addLog(role.getName(), currentUser.getUsername(), "�޸������룡");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
