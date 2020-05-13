package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.User;
import Server.db.DBUtil1;

public class LoginDao {
	public boolean checkUser(User user) {
		boolean b = false;
		Connection connection = null;
		try {
			connection = DBUtil1.getConnection();
			String sql = "select * from student1 where Name=? and password=?";
			PreparedStatement ps = connection.prepareStatement(sql);

			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				b = true;
				System.out.println("µÇÂ¼³É¹¦£¡");
			} else {
				System.out.println("µÇÂ¼Ê§°Ü£¡");
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {

			}

		}
		return b;
	}
}
