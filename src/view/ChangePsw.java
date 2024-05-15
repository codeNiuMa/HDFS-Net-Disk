package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class ChangePsw extends JFrame {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/test1?characterEncoding=UTF-8";
	static final String USER = "root";
    static final String PASS = "123456";
	

	private JPanel contentPane;
	private JTextField useridtextField;
	private JTextField yuanmimatextField;
	private JPasswordField newpasswordField;
	private JPasswordField yesnewpasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePsw frame = new ChangePsw();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChangePsw() {
		setTitle("\u4FEE\u6539\u5BC6\u7801");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel userIDLabel = new JLabel("\u8D26\u6237\u540D\uFF1A");
		userIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userIDLabel.setBounds(96, 66, 54, 15);
		contentPane.add(userIDLabel);
		
		JLabel yuanmimaLabel = new JLabel("\u539F\u5BC6\u7801\uFF1A");
		yuanmimaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		yuanmimaLabel.setBounds(96, 106, 54, 15);
		contentPane.add(yuanmimaLabel);
		
		JLabel lblNewLabel = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(96, 146, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(70, 186, 80, 15);
		contentPane.add(lblNewLabel_1);
		
		useridtextField = new JTextField();
		useridtextField.setBounds(160, 63, 156, 21);
		contentPane.add(useridtextField);
		useridtextField.setColumns(10);
		
		yuanmimatextField = new JTextField();
		yuanmimatextField.setBounds(160, 103, 156, 21);
		contentPane.add(yuanmimatextField);
		yuanmimatextField.setColumns(10);
		
		newpasswordField = new JPasswordField();
		newpasswordField.setBounds(160, 143, 156, 21);
		contentPane.add(newpasswordField);
		
		yesnewpasswordField = new JPasswordField();
		yesnewpasswordField.setBounds(160, 183, 156, 21);
		contentPane.add(yesnewpasswordField);
		
		JButton querenButton = new JButton("\u786E\u8BA4\u4FEE\u6539");
		querenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				changeInfo(e1);
			}
		});
		querenButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		querenButton.setBounds(253, 240, 93, 45);
		contentPane.add(querenButton);
		
		JButton fanhuiNewButton = new JButton("\u8FD4 \u56DE");
		fanhuiNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fanhui(e);
			}
		});
		fanhuiNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		fanhuiNewButton.setBounds(96, 240, 93, 45);
		contentPane.add(fanhuiNewButton);
	}

	protected void changeInfo(ActionEvent e1) {
		// TODO 自动生成的方法存根
		if(useridtextField.getText().equals("")||yuanmimatextField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "账号或原密码不能为空", "警告",JOptionPane.ERROR_MESSAGE);
		}
		else if(newpasswordField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入新密码！", "警告",JOptionPane.ERROR_MESSAGE);
		}
		else if(!newpasswordField.getText().equals(yesnewpasswordField.getText())) {
			JOptionPane.showMessageDialog(null, "与上一项输入密码不符，请重新输入！", "警告",JOptionPane.ERROR_MESSAGE);
		}
		else {
			Connection con = null;
			Statement stmt = null;
			PreparedStatement ps=null;
			 try{
		            Class.forName(JDBC_DRIVER);
		            con = DriverManager.getConnection(DB_URL,USER,PASS);
		            stmt = con.createStatement();
		            String sql;
		            sql = "SELECT * FROM user";//搜索login表
		            ResultSet rs = stmt.executeQuery(sql);
		            int pd=0;
		            while(rs.next()){
		            	String name  = rs.getString("name");//得到“name”列的值
		                String password = rs.getString("password");//得到“password”列的值
		                if(useridtextField.getText().equals(String.valueOf(name))) {
		                	pd=1;//找到了这个数据
		                	if(useridtextField.getText().equals(name)&&yuanmimatextField.getText().equals(password)) {
		                		 try{
		                	            Class.forName(JDBC_DRIVER);
		                	            con = DriverManager.getConnection(DB_URL,USER,PASS);
		                	            stmt = con.createStatement();
		                	            String sql1;
		                	            System.out.println("zhixingle");
		                	            sql1="UPDATE user SET password=? WHERE name=?";//向user表里修改数据
		                	            System.out.println("zhixingwan");
		                	            ps=con.prepareStatement(sql1);//修改数据预处理
		                	            ps.setString(1, yesnewpasswordField.getText().toString());
		                	            ps.setString(2, useridtextField.getText());
		                	        	ps.executeUpdate();//执行修改数据
		                	            ps.close();
		                	            stmt.close();
		                	            con.close();
		                	        }catch(SQLException se){
		                	            se.printStackTrace();
		                	        }catch(Exception e){
		                	            e.printStackTrace();
		                	        }finally{
		                	            try{
		                	                if(stmt!=null) stmt.close();
		                	            }catch(SQLException se2){
		                	            }
		                	            try{
		                	                if(con!=null) con.close();
		                	            }catch(SQLException se){
		                	                se.printStackTrace();
		                	            }
		                	        }
		                		 JOptionPane.showMessageDialog(null, "密码修改成功，请重新登录！", "提示",JOptionPane.INFORMATION_MESSAGE );
		                		 new LoginView().setVisible(true);
		                		 this.dispose();
		                	}else{
		                		JOptionPane.showMessageDialog(null, "原密码输入错误，请重新输入！", "警告",JOptionPane.ERROR_MESSAGE);
		                	}
		                	break;
		                }
		                
		            }
		            if(pd==0) {
		            	JOptionPane.showMessageDialog(null, "此账户名不存在，请重新输入", "警告",JOptionPane.ERROR_MESSAGE);
		            }
		            rs.close();
		            stmt.close();
		            con.close();
		        }catch(SQLException se){
		            se.printStackTrace();
		        }catch(Exception e){
		            e.printStackTrace();
		        }finally{
		            try{
		                if(stmt!=null) stmt.close();
		            }catch(SQLException se2){
		            }
		            try{
		                if(con!=null) con.close();
		            }catch(SQLException se){
		                se.printStackTrace();
		            }
		        }
		}
		
		
	}

	protected void fanhui(ActionEvent e) {
		// TODO 自动生成的方法存根
		//new MainMenu().setVisible(true);
		this.dispose();
	}
}
