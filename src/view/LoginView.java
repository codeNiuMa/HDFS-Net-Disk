package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Dao.DataBase;

import java.awt.Color;
import javax.swing.JOptionPane;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class LoginView extends JFrame {

	private JPanel contentPane;
	private JTextField zhanghaotextField;
	private JPasswordField mimapasswordField;
	

	/**
	 * 		运行main函数
	 */	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}); 
	}
	
	

	/**
	 * 		创建框架
	 */
	public LoginView() {
		setType(Type.POPUP);
		setTitle("\u5341\u5EA6\u7F51\u76D8  V1.0 author: xxx");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginView.class.getResource("/pic/tubiao.jpg")));
		setResizable(false);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(760, 380, 400, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel UserNewLabel = new JLabel("账户名：");
		UserNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		UserNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		UserNewLabel.setBounds(81, 91, 67, 15);
		contentPane.add(UserNewLabel);
		
		JLabel pswNewLabel = new JLabel("密   码：");
		pswNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		pswNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pswNewLabel.setBounds(81, 132, 67, 15);
		contentPane.add(pswNewLabel);
		
		zhanghaotextField = new JTextField();
		zhanghaotextField.setBounds(158, 88, 130, 21);
		contentPane.add(zhanghaotextField);
		zhanghaotextField.setColumns(10);
		
		mimapasswordField = new JPasswordField();
		mimapasswordField.setBounds(158, 129, 130, 21);
		contentPane.add(mimapasswordField);
		//注册按钮
		JButton zhuceNewButton = new JButton("注 册");
		zhuceNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		zhuceNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newRegister();
			}
		});
		zhuceNewButton.setBounds(81, 185, 93, 35);
		contentPane.add(zhuceNewButton);
		
		// 登录按钮监听器
		JButton loginNewButton = new JButton("登 录");
		loginNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		loginNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				LoginAvt(ae);// 转入登录功能
			}
		});
		loginNewButton.setBounds(225, 185, 93, 35);
		contentPane.add(loginNewButton);
		
		JLabel softnameNewLabel = new JLabel("欢迎使用十度网盘 V1.0！");
		softnameNewLabel.setBackground(SystemColor.window);
		softnameNewLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		softnameNewLabel.setIcon(new ImageIcon(LoginView.class.getResource("/pic/tubiao50.jpg")));
		softnameNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		softnameNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		softnameNewLabel.setBounds(39, 22, 311, 53);
		contentPane.add(softnameNewLabel);
	}



	protected void newRegister() {
		// TODO 自动生成的方法存根
		new Register().setVisible(true); //打开新界面MainMenu
		this.setVisible(false);//关闭本界面
	}



	@SuppressWarnings("deprecation")
	protected void LoginAvt(ActionEvent ae) {
		//判断字符串是否为空,这里是如果不为空,继续
		if(!zhanghaotextField.getText().isEmpty() && !mimapasswordField.getText().isEmpty()){ 
			//当点击登录按钮时，首先与数据库建立连接
			DataBase dataBase = new DataBase();
			dataBase.getCon();
			boolean result = dataBase.adminLogin(zhanghaotextField.getText().trim(), mimapasswordField.getText().trim());
			if(result){
				//登录成功 
				new MainMenu().setVisible(true); //打开新界面MainMenu
//				this.setVisible(false);//关闭本界面
				this.dispose();
			}else{
				//登录失败
				JOptionPane.showMessageDialog(null, "账号或密码输入错误！", "警告", JOptionPane.ERROR_MESSAGE);
				mimapasswordField.setText("");//输错密码后清空密码
			}
		}else if(zhanghaotextField.getText().isEmpty()){
			JOptionPane.showMessageDialog(null,"请输入用户名！","提示",JOptionPane.WARNING_MESSAGE);
		}else if(mimapasswordField.getText().isEmpty())	{
			JOptionPane.showMessageDialog(null,"请输入密码！","提示",JOptionPane.WARNING_MESSAGE);
			
		}
	}
}
