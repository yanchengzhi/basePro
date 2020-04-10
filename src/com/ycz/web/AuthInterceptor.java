package com.ycz.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ycz.pojo.Menu;
import com.ycz.service.MenuService;

/**
 * 
 * @ClassName AuthInterceptor
 * @Description TODO(�̳��������������Ƿ�·��������)
 * @author Administrator
 * @Date 2020��4��10�� ����3:41:31
 * @version 1.0.0
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private MenuService mService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //��ȡ�û������ַ
        String uri = request.getRequestURI();
        //��ȡӦ��·��
        String path = request.getSession().getServletContext().getContextPath();
        
        //�жϵ�ǰ�û��Ƿ���Ҫ����Ȩ����֤
        List<Menu> menus = mService.queryAll();//��ȡ���еĲ˵�
        Set<String> uriSet = new HashSet<>();
        for(Menu m:menus) {
            if(m.getUrl()!=null && !"".equals(m.getUrl())) {
              uriSet.add(path+"/"+m.getUrl());  
            }
        }
        if(uriSet.contains(uri)) {
            //����û������ַ����������·���ķ�Χ�ڣ�����Ȩ����֤
            //��ȡ���ڻỰ���е��û���Ȩ��
            Set<String> authSet = (Set<String>) request.getSession().getAttribute("uriSet");
            if(authSet.contains(uri)) {//����û������ַ�ڰ󶨵�Ȩ�޵�ַ��Χ��
                return true;//����ִ��
            }else {//�����������
                response.sendRedirect(path+"/WEB-INF/errors/404.jsp");
                return false;
            }
        }else {
         return true;   
        }    
    }
    
    

}
