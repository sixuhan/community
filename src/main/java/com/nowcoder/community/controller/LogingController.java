package com.nowcoder.community.controller;


import com.google.code.kaptcha.Producer;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
public class LogingController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(LogingController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Producer KaptchaProducer;

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public  String  getRegisterPage(){

        return "/site/register";
    }

    @RequestMapping(path = "/login",method = RequestMethod.GET)
    public  String  getLoginPage(){

        return "/site/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public  String register(Model model, User user){
        Map<String, Object> map = userService.register(user);
        if(map == null || map.isEmpty()){
            model.addAttribute("msg","注册成功,我们已经向您的邮箱发送了一封激活邮件,请尽快激活！");
            model.addAttribute("target","/index");
            return  "/site/operate-result";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "/site/register";
        }
    }

    //http://localhost:8080/community/activation/101/code
    @RequestMapping(path = "/activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId,@PathVariable("code") String code){
        int result = userService.activation(userId, code);
        if(result == ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功,账号已经可以使用！");
            model.addAttribute("target","/login");

        }else if(result == ACTIVATION_REPEAT){
            model.addAttribute("msg","无效操作,该账号已激活");
            model.addAttribute("target","/index");
        }else {
            model.addAttribute("msg","注册失败,无效的激活码！");
            model.addAttribute("target","/index");
        }
        return "/site/operate-result";
    }

    //生成验证码
    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        //生成验证码
        String text = KaptchaProducer.createText();
        BufferedImage image = KaptchaProducer.createImage(text);

        //将验证码存入session
        session.setAttribute("kaptcha",text);

        //将图片输入给浏览器
        response.setContentType("/image/png");
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {

            //流由spring来进行管理
            logger.error("验证码响应失败" + e.getMessage());
        }
    }
}
