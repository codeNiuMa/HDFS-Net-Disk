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
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.SystemColor;

public class Register extends JFrame {

	JPanel contentPane;
	JTextField userztextField;
	JLabel userNewLabel = new JLabel("�˻�����");	//�û�����ǩ
	JLabel mimazNewLabel = new JLabel("�� �룺");	//�����ǩ
	JLabel mimazagaNewLabel = new JLabel("ȷ�����룺");  //ȷ�������ǩ
	JButton yeszNewButton = new JButton("ȷ��ע��");
	JButton fanhuiNewButton = new JButton("ȡ ��");
	
	
	
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Register.class.getResource("/pic/tubiao.jpg")));
		setResizable(false);
		setTitle("���û�ע��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(760, 380, 400, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		userNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		
		
		userNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userNewLabel.setBounds(80, 50, 66, 15);
		contentPane.add(userNewLabel);
		mimazNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		
		
		mimazNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mimazNewLabel.setBounds(80, 100, 66, 15);
		contentPane.add(mimazNewLabel);
		mimazagaNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		
		
		mimazagaNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		mimazagaNewLabel.setBounds(67, 150, 79, 15);
		contentPane.add(mimazagaNewLabel);
		
		userztextField = new JTextField();
		userztextField.setBounds(169, 48, 148, 21);
		contentPane.add(userztextField);
		userztextField.setColumns(10);
		yeszNewButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		
		
		yeszNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistryInfo(e);
			}
		});
		yeszNewButton.setBounds(67, 206, 101, 36);
		contentPane.add(yeszNewButton);
		fanhuiNewButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		
		
		fanhuiNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quitRegister();
			}
		});
		fanhuiNewButton.setBounds(224, 206, 101, 36);
		contentPane.add(fanhuiNewButton);
		
		zhucepasswordField = new JPasswordField();
		zhucepasswordField.setBounds(169, 98, 148, 21);
		contentPane.add(zhucepasswordField);
		
		zagapasswordField = new JPasswordField();
		zagapasswordField.setBounds(169, 148, 148, 21);
		contentPane.add(zagapasswordField);
	}

	protected void RegistryInfo(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==yeszNewButton) {
			if(userztextField.getText().length()==0) {
				JOptionPane.showMessageDialog(null, "�˺Ų���Ϊ��", "����",JOptionPane.ERROR_MESSAGE);
			}
			else if(zhucepasswordField.getText().length()==0||zagapasswordField.getText().length()==0) {
				JOptionPane.showMessageDialog(null, "���벻��Ϊ�գ�", "����",JOptionPane.ERROR_MESSAGE);
			}
			else if(!zhucepasswordField.getText().equals(zhucepasswordField.getText())) {
				JOptionPane.showMessageDialog(null, "����һ���������벻�������������룡", "����",JOptionPane.ERROR_MESSAGE);
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
			                // ͨ���ֶμ����˻���
			               String name = rs.getString("name");
			               if(name.equals(userztextField.getText())) {
			                	JOptionPane.showMessageDialog(null, "���˻����Ѵ��ڣ�", "����",JOptionPane.ERROR_MESSAGE);
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
				            	JOptionPane.showMessageDialog(null, "��ӭ "+userztextField.getText()+" ����ʮ�����̣�", "ע��ɹ�",JOptionPane.INFORMATION_MESSAGE );
				            	new LoginView().setVisible(true);
//				    			this.setVisible(false);
				    			this.dispose();
			            	}
			            }
			            // ��ɺ�ر�
			            rs.close();
			            stmt.close();
			            con.close();
			      }catch(SQLException se){
			            // ���� JDBC ����
			            se.printStackTrace();
			        }catch(Exception ae){
			            // ���� Class.forName ����
			            ae.printStackTrace();
			        }finally{
			            // �ر���Դ
			            try{
			                if(stmt!=null) stmt.close();
			            }catch(SQLException se2){
			            }// ʲô������
			            try{
			                if(con!=null) con.close();
			            }catch(SQLException se){
			                se.printStackTrace();
			            }
			        }
			}
		}
	}
	
	

	//�˳���ť
	protected void quitRegister() {
		// TODO �Զ����ɵķ������
		new LoginView().setVisible(true); //���½���MainMenu
		this.dispose();//�رձ�����
	}
}
