package com.ycz.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @ClassName ServerStartupListener
 * @Description TODO(��Ϊ·��������ʹ�ã���Ҫ����ǰ�˷���·��)
 * @author Administrator
 * @Date 2020��4��5�� ����5:58:25
 * @version 1.0.0
 */
public class ServerStartupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext context = arg0.getServletContext();//��ȡcontext�����Ķ���
        String path = context.getContextPath();//��ȡӦ��·��
        context.setAttribute("APP_PATH", path);//����·��

    }

}
