package com.ycz.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ycz.pojo.AjaxResult;
import com.ycz.pojo.User;
import com.ycz.service.UserService;
import com.ycz.util.CpachaUtil;

/**
 * @ClassName SystemController
 * @Description TODO(系统控制类)
 * @author Administrator
 * @Date 2020年4月5日 下午5:50:30
 * @version 1.0.0
 */

@Controller
@RequestMapping("/system/")
public class SystemController {
    
    @Autowired
    private UserService uService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("system/index");
        mav.addObject("name", "鄢承志");
        return mav;
    }

    /**
     * 
     * @Description (跳转到登录界面)
     * @param mav
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("system/login");
        return mav;
    }
    
    /**
     * 
     * @Description (用来生成验证码)
     * @param codeLen 字符长度
     * @param width 画布宽度
     * @param height 画布高度
     * @param cpachaType 验证码字符串
     * @param request
     * @param response
     */
    @RequestMapping(value = "getCpacha",method = RequestMethod.GET)
    public void getCpacha(
            @RequestParam(name ="len",required = false,defaultValue = "4")Integer codeLen,
            @RequestParam(name = "width",required = false,defaultValue = "100")Integer width,
            @RequestParam(name = "height",required = false,defaultValue = "30")Integer height,
            @RequestParam(name = "type",required = true,defaultValue = "loginCpacha")String cpachaType,
            HttpServletRequest request,HttpServletResponse response) {
        //创建对象
        CpachaUtil cUtil = new CpachaUtil(codeLen,width,height);
        //随机取字符生成验证码字符串
        String code = cUtil.generatorVCode();
        //存到会话域中
        request.getSession().setAttribute(cpachaType, code);
        BufferedImage image = cUtil.generatorRotateVCodeImage(code, true);
        try {
            //以图片的形式写入流中
            ImageIO.write(image,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 
     * @Description (登录验证)
     * @param user 前端返回的封装对象
     * @param cpacha 前端传过来的验证码字符串
     * @return
     */
    @ResponseBody
    @RequestMapping("loginDo")
    public AjaxResult loginDo(User user,String cpacha,HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        User dbUser = uService.queryUser(user);         
            if(dbUser!=null) {//不为空说明验证通过
                //验证码检查
                //获取页面随机生成的验证码             
                Object loginCpacha = request.getSession().getAttribute("loginCpacha");
                if(loginCpacha==null) {
                    result.setData("会话超时，请刷新页面！");
                    result.setSuccess(false);
                }else {
                    if(cpacha.equalsIgnoreCase(loginCpacha.toString())) {
                        //验证码通过后允许用户登录
                        request.getSession().setAttribute("currentUser", dbUser);//存到会话域中
                        result.setSuccess(true);
                    }else {
                        result.setData("验证码错误！");
                        result.setSuccess(false);
                    }
                }
            }else {//验证不通过，提示用户信息
                //查询用户名是否存在
                User loginUser = uService.queryUserByName(user.getUsername());
                if(loginUser==null) {                  
                    result.setData("用户名不存在！");
                }else {
                    result.setData("密码错误！");
                }
                result.setSuccess(false);
            }
        return result;
    }

}
