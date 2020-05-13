package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.User;
import Server.db.DBUtil1;

public class FriendsList {

	public String getFriends(User user) {

		String result = "";
		Connection connection = null;
		try {
			connection = DBUtil1.getConnection();
			String sql = "select Name,number from student1 where Name!=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getName());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result += rs.getString(1) + "`";
			}
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
		user.setFriendsList(result);
		return result;

	}

}
