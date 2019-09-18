package cn.gzsoft.daoImpl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import cn.gzsoft.dao.UserDao;
import cn.gzsoft.domain.User;
import cn.gzsoft.utils.MyC3P0Utils;

public class UserDaoImpl implements UserDao {

	@Override
	public void registe(User u) throws SQLException {
          QueryRunner qRunner = new QueryRunner(MyC3P0Utils.getDataSource());
          String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
          qRunner.update(sql, u.getUid(),u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),u.getTelephone(),u.getBirthday(),u.getSex(),u.getState(),u.getCode());          
	}

	@Override
	public int checkRegiste(String codeString) throws SQLException {
         QueryRunner qrRunner = new QueryRunner(MyC3P0Utils.getDataSource());
         String sql = "update user set state=1,code=null where code=?";
         return qrRunner.update(sql, codeString);
	}

	@Override
	public User login(String username, String passwordString) throws SQLException {
		QueryRunner qRunner = new QueryRunner(MyC3P0Utils.getDataSource());		
		return qRunner.query("select * from user where username=? and password=?", new BeanHandler<User>(User.class),username,passwordString);
	}

}
