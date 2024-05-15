package Dao;

import java.sql.*;

public class DataBase {
	String driverName="com.mysql.jdbc.Driver";
	//首先要在数据库中新建一个叫 TEXT 的数据库
	String conStr = "jdbc:mysql://localhost:3306/test1";
	// 数据库连接用户名
	String dbUserName = "root"; 
	// 数据库连接密码
	String dbPassword = "123456"; 
	private static Connection con = null;
	public DataBase() {
		try {
			Class.forName(driverName);
			System.out.println("加载驱动成功");
		}  
		catch (Exception sqlEx) {
			sqlEx.printStackTrace();
			System.out.println("加载驱动失败");
		}
		
		try{
			con=DriverManager.getConnection(conStr,dbUserName,dbPassword);
			System.out.println("MYSQL连接成功！");
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.print("MYSQL连接失败！"); 
		}
		
	}

	public static Connection getCon() {
		return con;
	}
	/**
	 * 关闭数据库连接
	 * @throws SQLException 
	 *
	 */
	public void closeCon(Connection con) throws SQLException{
		if(con!= null) {
			con.close();
		}	
	}
	
	/**
	 * 管理员登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean adminLogin(String username, String password) {
		//String sql = "select name from user where name=? and password=?";
		String sql = String.format("select * from user where name='%s' and password=%s", username, password);
		boolean flag = false;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
