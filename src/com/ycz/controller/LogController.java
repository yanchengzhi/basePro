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
 * @Description TODO(��־�������)
 * @author Administrator
 * @Date 2020��4��10�� ����7:10:07
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
     * @Description (��ת����־ҳ��)
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(ModelAndView mav,HttpServletRequest request) {
        String mid = request.getParameter("mid");
        //��ȡ��ɫ��Ӧ�����в˵�
        List<Menu> RoleMenus = (List<Menu>) request.getSession().getAttribute("mList");
        //��ȡ�����˵�
        List<Menu> thirdMenus = MenuUtil.getAllThirdMenus(RoleMenus, Long.parseLong(mid));
        //��ȡ�����˵�
        List<Menu> topList = mService.findTopList();
        mav.addObject("topList",topList);
        mav.addObject("thirdMenus", thirdMenus);
        mav.setViewName("log/list");
        return mav;
    }
    
    /**
     * 
     * @Description (��־��ҳ��ѯ)
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
        //��ȡ�ܼ�¼����
        int total = lService.getTotal(map);
        ret.put("total",total);
        return ret;
    }
    
    /**
     * 
     * @Description (��־���)
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
            ids=ids.substring(0,ids.length()-1);//��ȥ��β������
            //Ȼ��ִ��ɾ������
            lService.deleteLog(ids);
            result2.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result2.setSuccess(false);
        }
        return result2;
    }

}
