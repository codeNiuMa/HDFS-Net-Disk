package Dao;

import java.sql.*;

public class DataBase {
	String driverName="com.mysql.jdbc.Driver";
	//����Ҫ�����ݿ����½�һ���� TEXT �����ݿ�
	String conStr = "jdbc:mysql://localhost:3306/test1";
	// ���ݿ������û���
	String dbUserName = "root"; 
	// ���ݿ���������
	String dbPassword = "123456"; 
	private static Connection con = null;
	public DataBase() {
		try {
			Class.forName(driverName);
			System.out.println("���������ɹ�");
		}  
		catch (Exception sqlEx) {
			sqlEx.printStackTrace();
			System.out.println("��������ʧ��");
		}
		
		try{
			con=DriverManager.getConnection(conStr,dbUserName,dbPassword);
			System.out.println("MYSQL���ӳɹ���");
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.print("MYSQL����ʧ�ܣ�"); 
		}
		
	}

	public static Connection getCon() {
		return con;
	}
	/**
	 * �ر����ݿ�����
	 * @throws SQLException 
	 *
	 */
	public void closeCon(Connection con) throws SQLException{
		if(con!= null) {
			con.close();
		}	
	}
	
	/**
	 * ����Ա��¼
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
