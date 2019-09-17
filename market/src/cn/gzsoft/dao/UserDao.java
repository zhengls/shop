package cn.gzsoft.dao;

import java.sql.SQLException;

import cn.gzsoft.domain.User;

public interface UserDao {

	void registe(User u) throws SQLException;

	int checkRegiste(String codeString) throws SQLException;

}
