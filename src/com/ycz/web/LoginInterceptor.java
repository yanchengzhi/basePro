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
 * @Description TODO(登录拦截器)
 * @author Administrator
 * @Date 2020年4月5日 下午11:10:52
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
        //判断用户是否登录
        HttpSession session = arg0.getSession();//获取会话对象
        User user = (User) session.getAttribute("currentUser");//从会话域中获取用户对象
        if(user==null) {//为空则未登录
            String path = session.getServletContext().getContextPath();//获取应用路径
            arg1.sendRedirect(path + "/system/login");//重定向到登录界面
            return false;//返回false结束
        }else {//否则继续执行
            return true;
        }      
    }

}
