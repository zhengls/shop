package cn.gzsoft.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
	
	public void checkRegiste(HttpServletRequest request,HttpServletResponse response) throws SQLException
	{
		String codeString = request.getParameter("code");
		 UserService us = new UserServiceImpl();		
		 String msgString = us.checkRegiste(codeString);
		 request.setAttribute("msg", msgString);
		
	}

}
