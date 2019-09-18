package cn.gzsoft.service;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import cn.gzsoft.domain.User;

public interface UserService {

	String registe(User u) throws AddressException, MessagingException, SQLException;

	String checkRegiste(String codeString) throws SQLException;

	User login(String username, String passwordString) throws SQLException;

}
