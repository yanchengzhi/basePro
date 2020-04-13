package com.ycz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.Food;
import com.ycz.pojo.FoodCategory;
import com.ycz.service.FoodCategoryService;
/**
 * 
 * @ClassName IndexController
 * @Description TODO(前台控制器)
 * @author Administrator
 * @Date 2020年4月13日 下午4:37:49
 * @version 1.0.0
 */
@Controller
@RequestMapping("/index/")
public class IndexController {
    
    @Autowired
    private FoodCategoryService fcService;
    
    /**
     * 
     * @Description (跳往前台首页)
     * @return
     */
    @RequestMapping("index")
    private ModelAndView index() {
        ModelAndView mav = new ModelAndView("/home/index/index");
        return mav;
    }
    
    /**
     * 
     * @Description (查询菜品分类下的所有菜品)
     * @return
     */
    @ResponseBody
    @RequestMapping("getFoodList")
    public AjaxResult getFoodList() {
        AjaxResult result = new AjaxResult();
        try {
            List<FoodCategory> foodList = fcService.findAll();
            Map<String, List<Food>> content = new HashMap<>();
            for(FoodCategory fc:foodList) {
                content.put(fc.getName(),fc.getFoodList());
            }
            result.setData(content);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

}
