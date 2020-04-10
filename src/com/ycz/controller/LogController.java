package com.ycz.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.Log;
import com.ycz.pojo.Menu;
import com.ycz.pojo.Page;
import com.ycz.service.LogService;
import com.ycz.service.MenuService;
import com.ycz.util.MenuUtil;

/**
 * 
 * @ClassName LogController
 * @Description TODO(日志类控制器)
 * @author Administrator
 * @Date 2020年4月10日 下午7:10:07
 * @version 1.0.0
 */
@Controller
@RequestMapping("/log/")
public class LogController {
    
    @Autowired
    private LogService lService;
    
    @Autowired
    private MenuService mService;
    
    /**
     * 
     * @Description (跳转到日志页面)
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav,HttpServletRequest request) {
        String mid = request.getParameter("mid");
        //获取角色对应的所有菜单
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        //获取三级菜单
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        //获取顶级菜单
        List<Menu> topList = mService.findTopList();
        mav.addObject("topList",topList);
        mav.addObject("thirdMenus", thirdMenus);
        mav.setViewName("log/list");
        return mav;
    }
    
    /**
     * 
     * @Description (日志分页查询)
     * @param content
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("listData")
    public Map<String, Object> listData(
            @RequestParam(name="content",required= false ) String content,
            Page page
            ) {
        Map<String, Object> ret = new HashMap<String,Object>();
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("content",content);
        map.put("pageSize", page.getRows());
        map.put("offset",page.getOffset());
        List<Log> findList = lService.findLogList(map);
        ret.put("rows",findList);
        //获取总记录条数
        int total = lService.getTotal(map);
        ret.put("total",total);
        return ret;
    }
    
    /**
     * 
     * @Description (日志添加)
     * @param log
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public AjaxResult add(Log log) {
        AjaxResult result = new AjaxResult();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTimeStr = sdf.format(new Date());
            log.setCreateTimeStr(createTimeStr);
            lService.add(log);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("deleteLog")
    public AjaxResult deleteLog(String ids) {
        AjaxResult result2 = new AjaxResult();
        try {
            ids=ids.substring(0,ids.length()-1);//先去掉尾部逗号
            //然后执行删除操作
            lService.deleteLog(ids);
            result2.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result2.setSuccess(false);
        }
        return result2;
    }

}
