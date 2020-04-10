package com.ycz.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.Menu;
import com.ycz.pojo.User;
import com.ycz.util.MenuUtil;

/**
 * 
 * @ClassName LoginInterceptor
 * @Description TODO(��¼������)
 * @author Administrator
 * @Date 2020��4��5�� ����11:10:52
 * @version 1.0.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        //�ж��û��Ƿ��¼
        HttpSession session = arg0.getSession();//��ȡ�Ự����
        User user = (User) session.getAttribute("currentUser");//�ӻỰ���л�ȡ�û�����
        if(user==null) {//Ϊ����δ��¼
            String path = session.getServletContext().getContextPath();//��ȡӦ��·��
            arg1.sendRedirect(path + "/system/login");//�ض��򵽵�¼����
            return false;//����false����
        }else {//�������ִ��
            return true;
        }      
    }

}
