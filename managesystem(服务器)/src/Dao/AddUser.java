package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.User;
import Server.db.DBUtil1;

public class AddUser {
	public boolean addString(User user) throws Exception {
		Connection connection = null;
		boolean b = false;
		try {
			connection = DBUtil1.getConnection();
			String sql = "insert into user (name,studentNumber,password)" + "value (?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getNumber());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
			b = true;
			ps.close();
		} catch (SQLException e) {
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

	public void addString2(String name, int studentNumber, String password) throws Exception {
		Connection connection = null;
		try {
			connection = DBUtil1.getConnection();
			String sql = "insert into user (name,studentNumber,password)" + "value (?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, studentNumber);
			ps.setString(3, password);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {

			}
		}
	}

}