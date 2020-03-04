package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/Data")
    @ResponseBody
    public  String getDate(){
        return  alphaService.find();
    }

    @RequestMapping("/http")
    public  void  http(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        //获取请求数据
        System.out.println(httpServletRequest.getMethod()); //获取请求方式
        System.out.println(httpServletRequest.getServletPath()); //获取请求的路径
        Enumeration<String> enumeration =  httpServletRequest.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = httpServletRequest.getHeader(name);
            System.out.println(name + ": " + value);
        } //获取请求行，请求行里面有很多的数据,以key-value的方式进行来封装

        System.out.println(httpServletRequest.getParameter("code"));

        //返回响应数据
        httpServletResponse.setContentType("text/html;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
            writer.write("<h1> hello <h1>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }

    }

    //Get请求
    //两种获得浏览器请求参数的数据

    // /students？current=1&limit=20
    @RequestMapping(path = "/students" , method = RequestMethod.GET)
    @ResponseBody
    public  String getStudents(
        @RequestParam(name = "current",required = false,defaultValue = "1") int current,
        @RequestParam(name = "limit",required = false,defaultValue = "20") int limit
        //required 可以不传这个参数,取默认值
    ){
        System.out.println(current);
        System.out.println(limit);
        return  "stdents";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public  String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return  "a student";
    }


    // POST请求
    //为什么提交数据不用get请求
    //(1)数据在地址栏上会暴露出来
    //(2)地址栏可输入的字数有限，如果数据过多,就无法传完

    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return  "success";
    }

    //响应HTML数据
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",12);
        mav.setViewName("/demo/view");
        //这里的view指的是view.html
        return  mav;
    }

    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public  String getSchool(Model model){
        model.addAttribute("name","北京大学");
        model.addAttribute("age", 20);
        return  "/demo/view";
        //返回的是view的路径
    }

    //响应json数据(异步请求)
    //Java对象 -> json字符串 ->js对象

    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        HashMap<String, Object> emp = new HashMap<>();
        emp.put("name","诗彤");
        emp.put("age",20);
        emp.put("salary",10000.00);
        return  emp;
    }

    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List< Map<String, Object>> getEmps(){
        List< Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name","诗彤");
        emp.put("age",20);
        emp.put("salary",10000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","孙锐");
        emp.put("age",20);
        emp.put("salary",10000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","佳威");
        emp.put("age",20);
        emp.put("salary",10000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","志鹏");
        emp.put("age",20);
        emp.put("salary",10000.00);
        list.add(emp);
        return  list;
    }

    //cookie相关示例
    @RequestMapping(path = "/cookie/set",method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        //创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        //设置Cookie的生效范围
        cookie.setPath("/community/alpha");
        //设置cookie的生存时间，默认为关闭游览器或者经过一定的时间后,cookie会消失
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);

        return "set Cookie";

    }

    @RequestMapping(path = "/cookie/get",method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code){
        System.out.println(code);
        return  "get cookie";
    }

    //session示例


    @RequestMapping(path = "/session/set",method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("id",1);
        session.setAttribute("name","2");
        return "set session";
    }

    @RequestMapping(path = "/session/get",method = RequestMethod.GET)
    @ResponseBody
    public String sessionget(HttpSession session){
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }


}
