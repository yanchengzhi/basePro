package com.ycz.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @ClassName ServerStartupListener
 * @Description TODO(作为路径监听器使用，主要用于前端访问路径)
 * @author Administrator
 * @Date 2020年4月5日 下午5:58:25
 * @version 1.0.0
 */
public class ServerStartupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext context = arg0.getServletContext();//获取context上下文对象
        String path = context.getContextPath();//获取应用路径
        context.setAttribute("APP_PATH", path);//设置路径

    }

}
