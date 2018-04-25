package com.somnus.blockchain.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.somnus.blockchain.service.AddorSearchService;
import com.somnus.blockchain.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddorSearchService addorSearchService;
	
	@RequestMapping("abc")
	private String test() {
		return "abc";
	}

    @RequestMapping("login")
    public boolean login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username+password);
        HttpSession session = request.getSession();
        if(userService.login(username,password)){
            session.setAttribute("user",username);
            System.out.println("success");
            return true;
        }
        else { System.out.println("wrongPass"); return false;}

    }

    @RequestMapping("register")
    public boolean register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String adress = request.getParameter("adr");
        System.out.println(username+password+adress);
        if(userService.Register(adress,username,password)){
            System.out.println("success");
            return true;
        }
        else { System.out.println("wrongRegister"); return false;}

    }

    @RequestMapping("online")
    public String online(HttpServletRequest request,HttpServletResponse response){
        String username = request.getSession().getAttribute("user").toString();
        if(username!=null){
            return username;
        }
        else return "未登录";
    }

    @RequestMapping("logout")
    public boolean logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        return true;
    }

    @RequestMapping("add")
    public boolean add(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String ID = request.getParameter("ID");
        String vio = request.getParameter("vio");
        BigInteger lowpoint =  new BigInteger(request.getParameter("lowpoint"));
        String admin = request.getSession().getAttribute("user").toString();
        if(addorSearchService.addMsg(username,ID,vio,lowpoint,admin)!=null){
            System.out.println("addSucess!");
            return true;
        }
        else return false;

    }

    @RequestMapping("search")
    public JSONArray search(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String name = request.getParameter("name");
        String ID = request.getParameter("ID");
        System.out.println(name+ID);
        int total = new Integer(String.valueOf(addorSearchService.returnTotal()));
        System.out.println(total);
        JSONArray js = new JSONArray();
        for(int i=0;i<total;i++){
            String username = addorSearchService.getuserName(BigInteger.valueOf(i));
            if(username.equals(name)) {
                JSONObject json = new JSONObject();
                json.put("vio", addorSearchService.getviolateRecord(BigInteger.valueOf(i)));
                json.put("lowpoint", addorSearchService.getlowPoint(BigInteger.valueOf(i)));
                json.put("admin", addorSearchService.getAdministrator(BigInteger.valueOf(i)));
                json.put("ID",addorSearchService.getID(BigInteger.valueOf(i)));
                js.add(json);
                System.out.println(json.toJSONString());
            }

        }
        return js;
    }

}