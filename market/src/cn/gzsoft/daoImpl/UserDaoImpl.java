package cn.gzsoft.daoImpl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import cn.gzsoft.dao.UserDao;
import cn.gzsoft.domain.User;
import cn.gzsoft.utils.MyC3P0Utils;

public class UserDaoImpl implements UserDao {

	@Override
	public void registe(User u) throws SQLException {
          QueryRunner qRunner = new QueryRunner(MyC3P0Utils.getDataSource());
          String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
          qRunner.update(sql, u.getUid(),u.getUsername(),u.getPasswrod(),u.getName(),u.getEmail(),u.getTelephone(),u.getBirthday(),u.getSex(),u.getState(),u.getCode());          
	}

	@Override
	public int checkRegiste(String codeString) throws SQLException {
         QueryRunner qrRunner = new QueryRunner(MyC3P0Utils.getDataSource());
         String sql = "update user set state=1 and code=null where code=?";
         return qrRunner.update(sql, codeString);
	}

}
