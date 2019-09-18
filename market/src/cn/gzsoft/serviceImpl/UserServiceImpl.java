package cn.gzsoft.serviceImpl;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import cn.gzsoft.dao.UserDao;
import cn.gzsoft.daoImpl.UserDaoImpl;
import cn.gzsoft.domain.User;
import cn.gzsoft.service.UserService;
import cn.gzsoft.utils.MailUtils;
import cn.gzsoft.utils.UUIDUtils;

public class UserServiceImpl implements UserService {

	@Override
	public String registe(User u) throws SQLException {
		u.setUid(UUIDUtils.getId());
		u.setState(0);
		String code = UUIDUtils.getId();
		u.setCode(code);
		try {
			MailUtils.sendNetMail(u.getEmail(), "欢迎注册，请<a href=http://localhost:8080/market/UserServlet?m=checkRegiste&code="+u.getCode()+">点击激活</a>");
		} catch (AddressException e) {
			
			e.printStackTrace();
			return "亲，注册失败，邮件地址不正确！";
		} catch (MessagingException e) {
			
			e.printStackTrace();
			return "亲，注册失败，邮件发送失败！";
		}
		UserDao ud = new UserDaoImpl();
		ud.registe(u);
		return "注册成功，请登录邮箱进行激活！";
	}

	@Override
	public String checkRegiste(String codeString) throws SQLException {
		UserDao ud = new UserDaoImpl();
		int a = ud.checkRegiste(codeString);
		if(a==0)
		{
			return "激活失败，请不要重复激活或激活码错误！";
		}else  {
			return "激活成功，请<a href=http://localhost:8080/market/jsp/login.jsp>登录！</a>";
		}
	
	}
	@Override
	public User login(String username, String passwordString) throws SQLException {
		UserDao ud = new UserDaoImpl();		
		return ud.login(username,passwordString);
	}

}
