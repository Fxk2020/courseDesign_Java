package DB;

import java.sql.*;

public class DBConnection {
	//加载驱动
	private static final String DBDRIVER="com.mysql.cj.jdbc.Driver";
	//数据库的名字sqltest
	private static final String DBURL="jdbc:mysql://localhost:3306/sqltest?&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	//用户名root
	private static final String DBUSER="root";
	//密码Fxk199959
	private static final String DBPASSWORD="Fxk199959";
	static {
		try {
			Class.forName(DBDRIVER);
			System.out.println("加载驱动成功");
		} catch (ClassNotFoundException e) {

			System.out.println("加载驱动失败");
		}
	}
	public static Connection getConnection() {
		Connection conn=null;
		try {
			conn=DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
			System.out.println("连接成功");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("发生连接异常");
		}
		return conn;
	}
	
	public static void  close(Connection conn) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("关闭数据库连接发生异常");
			}
			
		}
		
	}
	public static void close(Statement stmt) {
		if (stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("关闭数据库操作资源发生异常");
			}
			
		}
	}
	public static void close(ResultSet rs) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("关闭结果集发生异常");
			}
			
		}
		
	}

}
