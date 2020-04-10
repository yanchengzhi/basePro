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
 * @Description TODO(继承适配器，用作非法路径拦截器)
 * @author Administrator
 * @Date 2020年4月10日 下午3:41:31
 * @version 1.0.0
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private MenuService mService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //获取用户请求地址
        String uri = request.getRequestURI();
        //获取应用路径
        String path = request.getSession().getServletContext().getContextPath();
        
        //判断当前用户是否需要进行权限验证
        List<Menu> menus = mService.queryAll();//获取所有的菜单
        Set<String> uriSet = new HashSet<>();
        for(Menu m:menus) {
            if(m.getUrl()!=null && !"".equals(m.getUrl())) {
              uriSet.add(path+"/"+m.getUrl());  
            }
        }
        if(uriSet.contains(uri)) {
            //如果用户请求地址包含在所有路径的范围内，进行权限验证
            //获取存在会话域中的用户绑定权限
            Set<String> authSet = (Set<String>) request.getSession().getAttribute("uriSet");
            if(authSet.contains(uri)) {//如果用户请求地址在绑定的权限地址范围内
                return true;//继续执行
            }else {//否则进行拦截
                response.sendRedirect(path+"/WEB-INF/errors/404.jsp");
                return false;
            }
        }else {
         return true;   
        }    
    }
    
    

}
