package com.ycz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycz.pojo.Menu;
import com.ycz.service.AuthorityService;
import com.ycz.service.MenuService;

/**
 * 
 * @ClassName AuthController
 * @Description TODO(Ȩ�޿�����)
 * @author Administrator
 * @Date 2020��4��7�� ����8:59:01
 * @version 1.0.0
 */
@Controller
@RequestMapping("/auth/")
public class AuthController {
    
    @Autowired
    private AuthorityService aService;
    
    @Autowired
    private MenuService mService;
    
    @ResponseBody
    @RequestMapping("getAllMenu")
    public List<Menu> getAllMenu(){
        Map<String,Object> map = new HashMap<>();
        map.put("offset",0);
        map.put("pageSize",9999);//��ҳ����һ���ϴ����֣���ѯ�����в˵�
        List <Menu> mList = mService.findList(map);
        return mList;
    }

}
