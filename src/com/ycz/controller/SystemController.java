package com.ycz.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.User;
import com.ycz.service.UserService;
import com.ycz.util.CpachaUtil;

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

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("system/index");
        mav.addObject("name", "۳��־");
        return mav;
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

}
