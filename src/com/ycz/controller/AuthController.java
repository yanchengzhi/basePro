package com.ycz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.Authority;
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
    
    /**
     * 
     * @Description (��ȡ���еĲ˵���Ϣ)
     * @return
     */
    @ResponseBody
    @RequestMapping("getAllMenu")
    public List<Menu> getAllMenu(){
        Map<String,Object> map = new HashMap<>();
        map.put("offset",0);
        map.put("pageSize",9999);//��ҳ����һ���ϴ����֣���ѯ�����в˵�
        List <Menu> mList = mService.findList(map);
        return mList;
    }
    
    /**
     * 
     * @Description (���/�༭��ɫȨ��)
     * @param ids
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(
            @RequestParam(name="ids",required = true) String ids,
            @RequestParam(name="roleId",required = true) Long roleId
            ) {
         AjaxResult result = new AjaxResult();
         try {
             //��ɾ��ԭ��ɫ������Ȩ��
             aService.deleteByRoleId(roleId);
             //Ȼ�������Ȩ��
             //��ȡ�ַ�����ȥ�����һ���ַ�����
             ids=ids.substring(0,ids.length()-1);
             //�ָ��ַ���
             String []strs = ids.split(",");
             for(String s:strs) {
                 Authority auth = new Authority();
                 auth.setRoleId(roleId);
                 auth.setMenuId(Long.parseLong(s));
                 //���
                 aService.add(auth);
             }
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
         return result;
    }
    
    /**
     * 
     * @Description (��ȡ��ɫ����Ȩ��)
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("getRoleAuthority")
    public List<Authority> getRoleAuthority(Long roleId){
        List <Authority> aList = aService.findListByRoleId(roleId);
        return aList;
    }

}
