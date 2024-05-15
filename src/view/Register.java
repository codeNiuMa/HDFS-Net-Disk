package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.xml.bind.v2.model.core.RegistryInfo;

import view.LoginView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Register extends JFrame {

	JPanel contentPane;
	JTextField userztextField;
	JLabel userNewLabel = new JLabel("账户名：");	//用户名标签
	JLabel mimazNewLabel = new JLabel("密码：");	//密码标签
	JLabel mimazagaNewLabel = new JLabel("确认密码：");  //确认密码标签
	JButton yeszNewButton = new JButton("确认注册");
	JButton fanhuiNewButton = new JButton("返回");
	
	
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/test1?characterEncoding=UTF-8";
	static final String USER = "root";
    static final String PASS = "123456";
    JPasswordField zhucepasswordField;
    JPasswordField zagapasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setResizable(false);
		setTitle("\u65B0\u7528\u6237\u6CE8\u518C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(760, 380, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		userNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userNewLabel.setBounds(87, 62, 54, 15);
		contentPane.add(userNewLabel);
		
		
		mimazNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mimazNewLabel.setBounds(87, 103, 54, 15);
		contentPane.add(mimazNewLabel);
		
		
		mimazagaNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mimazagaNewLabel.setBounds(75, 146, 66, 15);
		contentPane.add(mimazagaNewLabel);
		
		userztextField = new JTextField();
		userztextField.setBounds(151, 59, 129, 21);
		contentPane.add(userztextField);
		userztextField.setColumns(10);
		
		
		yeszNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistryInfo(e);
			}
		});
		yeszNewButton.setBounds(75, 202, 93, 23);
		contentPane.add(yeszNewButton);
		
		
		fanhuiNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quitRegister();
			}
		});
		fanhuiNewButton.setBounds(232, 202, 93, 23);
		contentPane.add(fanhuiNewButton);
		
		zhucepasswordField = new JPasswordField();
		zhucepasswordField.setBounds(151, 100, 129, 21);
		contentPane.add(zhucepasswordField);
		
		zagapasswordField = new JPasswordField();
		zagapasswordField.setBounds(151, 143, 129, 21);
		contentPane.add(zagapasswordField);
	}

	protected void RegistryInfo(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==yeszNewButton) {
			if(userztextField.getText().length()==0) {
				JOptionPane.showMessageDialog(null, "账号不能为空", "警告",JOptionPane.ERROR_MESSAGE);
			}
			else if(zhucepasswordField.getText().length()==0||zagapasswordField.getText().length()==0) {
				JOptionPane.showMessageDialog(null, "密码不能为空！", "警告",JOptionPane.ERROR_MESSAGE);
			}
			else if(!zhucepasswordField.getText().equals(zhucepasswordField.getText())) {
				JOptionPane.showMessageDialog(null, "与上一项输入密码不符，请重新输入！", "警告",JOptionPane.ERROR_MESSAGE);
			}
			else {
				Connection con = null;
			      Statement stmt = null;
			      PreparedStatement ps=null;
			      try {
			    	  Class.forName(JDBC_DRIVER);
			    	  con = DriverManager.getConnection(DB_URL,USER,PASS);
			    	  stmt = con.createStatement();
			    	  String sql;
			            sql = "SELECT * FROM user";
			            ResultSet rs = stmt.executeQuery(sql);
			            int pd=0;
			            int count = 1;
			            while(rs.next()){
			            	count++;
			                // 通过字段检索账户名
			               String name = rs.getString("name");
			               if(name.equals(userztextField.getText())) {
			                	JOptionPane.showMessageDialog(null, "此账户名已存在！", "警告",JOptionPane.ERROR_MESSAGE);
			                	pd=1;
			                	break;
			                }
			            }
			            if(pd==0){
			            	sql="INSERT INTO user VALUES(?,?,?)";
			            	ps=con.prepareStatement(sql);
			            	ps.setString(1, String.valueOf(count));
			            	ps.setString(2, userztextField.getText());
			            	ps.setString(3, zhucepasswordField.getText());
			            	if(ps.executeUpdate()!=0) {
				            	JOptionPane.showMessageDialog(null, "账号注册成功！", "提示",JOptionPane.INFORMATION_MESSAGE );
				            	new LoginView().setVisible(true);
				    			this.setVisible(false);
			            	}
			            }
			            // 完成后关闭
			            rs.close();
			            stmt.close();
			            con.close();
			      }catch(SQLException se){
			            // 处理 JDBC 错误
			            se.printStackTrace();
			        }catch(Exception ae){
			            // 处理 Class.forName 错误
			            ae.printStackTrace();
			        }finally{
			            // 关闭资源
			            try{
			                if(stmt!=null) stmt.close();
			            }catch(SQLException se2){
			            }// 什么都不做
			            try{
			                if(con!=null) con.close();
			            }catch(SQLException se){
			                se.printStackTrace();
			            }
			        }
			}
		}
	}
	
	

	//简简单单的退出按钮
	protected void quitRegister() {
		// TODO 自动生成的方法存根
		new LoginView().setVisible(true); //打开新界面MainMenu
		this.setVisible(false);//关闭本界面
	}
}
