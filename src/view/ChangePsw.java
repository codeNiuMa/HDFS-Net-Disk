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
import java.awt.SystemColor;
import java.awt.Toolkit;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChangePsw.class.getResource("/pic/tubiao.jpg")));
		setResizable(false);
		setTitle("�޸�����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel userIDLabel = new JLabel("�˻�����");
		userIDLabel.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		userIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userIDLabel.setBounds(70, 66, 80, 15);
		contentPane.add(userIDLabel);
		
		JLabel yuanmimaLabel = new JLabel("ԭ���룺");
		yuanmimaLabel.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		yuanmimaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		yuanmimaLabel.setBounds(70, 106, 80, 15);
		contentPane.add(yuanmimaLabel);
		
		JLabel lblNewLabel = new JLabel("�����룺");
		lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(70, 146, 80, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ȷ�����룺");
		lblNewLabel_1.setFont(new Font("΢���ź�", Font.PLAIN, 13));
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
		
		JButton querenButton = new JButton("ȷ���޸�");
		querenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				changeInfo(e1);
			}
		});
		querenButton.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		querenButton.setBounds(253, 240, 93, 45);
		contentPane.add(querenButton);
		
		JButton fanhuiNewButton = new JButton("ȡ  ��");
		fanhuiNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fanhui(e);
			}
		});
		fanhuiNewButton.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		fanhuiNewButton.setBounds(96, 240, 93, 45);
		contentPane.add(fanhuiNewButton);
	}

	protected void changeInfo(ActionEvent e1) {
		// TODO �Զ����ɵķ������
		if(useridtextField.getText().equals("")||yuanmimatextField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "�˺Ż�ԭ���벻��Ϊ��", "����",JOptionPane.ERROR_MESSAGE);
		}
		else if(newpasswordField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "�����������룡", "����",JOptionPane.ERROR_MESSAGE);
		}
		else if(!newpasswordField.getText().equals(yesnewpasswordField.getText())) {
			JOptionPane.showMessageDialog(null, "����һ���������벻�������������룡", "����",JOptionPane.ERROR_MESSAGE);
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
		            sql = "SELECT * FROM user";//����user��
		            ResultSet rs = stmt.executeQuery(sql);
		            int pd=0;
		            while(rs.next()){
		            	String name  = rs.getString("name");//�õ���name���е�ֵ
		                String password = rs.getString("password");//�õ���password���е�ֵ
		                if(useridtextField.getText().equals(String.valueOf(name))) {
		                	pd=1;//�ҵ����������
		                	if(useridtextField.getText().equals(name)&&yuanmimatextField.getText().equals(password)) {
		                		 try{
		                	            Class.forName(JDBC_DRIVER);
		                	            con = DriverManager.getConnection(DB_URL,USER,PASS);
		                	            stmt = con.createStatement();
		                	            String sql1;
		                	            sql1="UPDATE user SET password=? WHERE name=?";//��user�����޸�����
		                	            ps=con.prepareStatement(sql1);//�޸�����Ԥ����
		                	            ps.setString(1, yesnewpasswordField.getText().toString());
		                	            ps.setString(2, useridtextField.getText());
		                	        	ps.executeUpdate();//ִ���޸�����
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
		                		 JOptionPane.showMessageDialog(null, "�����޸ĳɹ��������µ�¼��", "��ʾ",JOptionPane.INFORMATION_MESSAGE );
		                		 new LoginView().setVisible(true);
		                		 this.dispose();
		                	}else{
		                		JOptionPane.showMessageDialog(null, "ԭ��������������������룡", "����",JOptionPane.ERROR_MESSAGE);
		                	}
		                	break;
		                }
		                
		            }
		            if(pd==0) {
		            	JOptionPane.showMessageDialog(null, "���˻��������ڣ�����������", "����",JOptionPane.ERROR_MESSAGE);
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
		// TODO �Զ����ɵķ������
		//new MainMenu().setVisible(true);
		this.dispose();
	}
}
