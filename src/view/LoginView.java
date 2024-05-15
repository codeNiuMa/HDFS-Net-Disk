package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Dao.DataBase;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {

	private JPanel contentPane;
	private JTextField zhanghaotextField;
	private JPasswordField mimapasswordField;
	

	/**
	 * 		mainmainmainmain����main����mainmainmainmain
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
	 * 		�������
	 */
	public LoginView() {
		setResizable(false);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(760, 380, 400, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel UserNewLabel = new JLabel("\u8D26\u6237\u540D\uFF1A");
		UserNewLabel.setBounds(94, 57, 54, 15);
		contentPane.add(UserNewLabel);
		
		JLabel pswNewLabel = new JLabel("\u5BC6\u7801\uFF1A");
		pswNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pswNewLabel.setBounds(94, 91, 54, 15);
		contentPane.add(pswNewLabel);
		
		zhanghaotextField = new JTextField();
		zhanghaotextField.setBounds(158, 54, 115, 21);
		contentPane.add(zhanghaotextField);
		zhanghaotextField.setColumns(10);
		
		mimapasswordField = new JPasswordField();
		mimapasswordField.setBounds(158, 88, 115, 21);
		contentPane.add(mimapasswordField);
		//ע�ᰴť
		JButton zhuceNewButton = new JButton("\u6CE8\u518C");
		zhuceNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newRegister();
			}
		});
		zhuceNewButton.setBounds(84, 157, 93, 23);
		contentPane.add(zhuceNewButton);
		
		//��¼��ť
		JButton loginNewButton = new JButton("\u767B\u5F55");
		loginNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				LoginAvt(ae);
			}
		});
		loginNewButton.setBounds(220, 157, 93, 23);
		contentPane.add(loginNewButton);
	}



	protected void newRegister() {
		// TODO �Զ����ɵķ������
		new Register().setVisible(true); //���½���MainMenu
		this.setVisible(false);//�رձ�����
	}



	@SuppressWarnings("deprecation")
	protected void LoginAvt(ActionEvent ae) {
		// TODO �Զ����ɵķ������
		String username = zhanghaotextField.getText().toString();
		String password = mimapasswordField.getText().toString();
		//�ж��ַ����Ƿ�Ϊ��,�����������Ϊ��,����
		if(!zhanghaotextField.getText().isEmpty() && !mimapasswordField.getText().isEmpty()){ 
			//�������¼��ťʱ�����������ݿ⽨������
			DataBase dataBase = new DataBase();
			dataBase.getCon();
			boolean result = dataBase.adminLogin(zhanghaotextField.getText().trim(), mimapasswordField.getText().trim());
			if(result){
				//��¼�ɹ� 
				new MainMenu().setVisible(true); //���½���MainMenu
				this.setVisible(false);//�رձ�����
			}else{
				//��¼ʧ��
				JOptionPane.showMessageDialog(null, "�˺Ż������������", "tip", JOptionPane.ERROR_MESSAGE);
			}
		}else if(zhanghaotextField.getText().isEmpty()){
			JOptionPane.showMessageDialog(null,"�������û�����","",JOptionPane.WARNING_MESSAGE);
		}else if(mimapasswordField.getText().isEmpty())	{
			JOptionPane.showMessageDialog(null,"���������룡","warn",JOptionPane.WARNING_MESSAGE);
		}
	}
}
