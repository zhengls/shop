package cn.gzsoft.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gzsoft.domain.User;
import cn.gzsoft.service.UserService;
import cn.gzsoft.serviceImpl.UserServiceImpl;
import cn.gzsoft.utils.MyBaseServlet;
import cn.gzsoft.utils.MyBeanUtilsPlus;


@WebServlet("/UserServlet")
public class UserServlet extends MyBaseServlet {
	
	public String registe(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Map<String, String[]> parameterMap = request.getParameterMap();
	    User u = MyBeanUtilsPlus.populate(User.class, parameterMap);	    
	    UserService us = new UserServiceImpl();	    
	    String msg = us.registe(u);	    
	    request.setAttribute("msg", msg);	    
		return "/jsp/info.jsp";
	}
	
	public String checkRegiste(HttpServletRequest request,HttpServletResponse response) throws SQLException
	{
		String codeString = request.getParameter("code");
		 UserService us = new UserServiceImpl();		
		 String msgString = us.checkRegiste(codeString);
		 request.setAttribute("msg", msgString);
		 return "/jsp/info.jsp";
	}
	
	public String login(HttpServletRequest request,HttpServletResponse response) throws SQLException, UnsupportedEncodingException 
	{
		String yzm = request.getParameter("yzm");
		String username = request.getParameter("username");
		String passwordString = request.getParameter("password");
		String msg="";
		if(request.getSession().getAttribute("code")==null||!yzm.equalsIgnoreCase(request.getSession().getAttribute("code").toString()))
		{
			msg = "验证码输入错误！";
			request.setAttribute("msg", msg);
			return "jsp/login.jsp";
		}
		UserService us = new UserServiceImpl();
		User u = us.login(username, passwordString);
		if(u==null)
		{
			msg="用户名或密码错误！";
			request.setAttribute("msg", msg);
			return "jsp/login.jsp";
		}
		if(u.getState()==0)
		{
			msg="该用户尚未激活，请登录邮箱进行激活！";
			request.setAttribute("msg", msg);
			return "jsp/login.jsp";
		}
		Cookie cookie = new Cookie("remUName",URLEncoder.encode(username,"utf-8"));
		cookie.setPath("/");
		String okString = request.getParameter("remUName");
	    if(okString==null)
	    {
	    	cookie.setMaxAge(0);
	    }
	    else if(okString.equalsIgnoreCase("ok"))
		{
			cookie.setMaxAge(60*60);
		}
		else 
		{
			cookie.setMaxAge(0);
		}
		response.addCookie(cookie);
		request.getSession().setAttribute("user", u);		
		return "/jsp/index.jsp";
	}
	public String exit(HttpServletRequest request,HttpServletResponse response)
	{
		request.getSession().invalidate();
		return "/jsp/index.jsp";
	}

}
