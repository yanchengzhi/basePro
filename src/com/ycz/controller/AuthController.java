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
 * @Description TODO(权限控制器)
 * @author Administrator
 * @Date 2020年4月7日 下午8:59:01
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
     * @Description (获取所有的菜单信息)
     * @return
     */
    @ResponseBody
    @RequestMapping("getAllMenu")
    public List<Menu> getAllMenu(){
        Map<String,Object> map = new HashMap<>();
        map.put("offset",0);
        map.put("pageSize",9999);//分页传入一个较大数字，查询出所有菜单
        List <Menu> mList = mService.findList(map);
        return mList;
    }
    
    /**
     * 
     * @Description (添加/编辑角色权限)
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
             //先删除原角色的所有权限
             aService.deleteByRoleId(roleId);
             //然后再添加权限
             //截取字符串，去掉最后一个字符逗号
             ids=ids.substring(0,ids.length()-1);
             //分割字符串
             String []strs = ids.split(",");
             for(String s:strs) {
                 Authority auth = new Authority();
                 auth.setRoleId(roleId);
                 auth.setMenuId(Long.parseLong(s));
                 //添加
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
     * @Description (获取角色已有权限)
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
