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
	 * 		����main����
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
		setType(Type.POPUP);
		setTitle("\u5341\u5EA6\u7F51\u76D8  V1.0 author: y22208082-szy");
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
		
		JLabel UserNewLabel = new JLabel("�˻�����");
		UserNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		UserNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		UserNewLabel.setBounds(81, 91, 67, 15);
		contentPane.add(UserNewLabel);
		
		JLabel pswNewLabel = new JLabel("��   �룺");
		pswNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 15));
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
		//ע�ᰴť
		JButton zhuceNewButton = new JButton("ע ��");
		zhuceNewButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		zhuceNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newRegister();
			}
		});
		zhuceNewButton.setBounds(81, 185, 93, 35);
		contentPane.add(zhuceNewButton);
		
		// ��¼��ť������
		JButton loginNewButton = new JButton("�� ¼");
		loginNewButton.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		loginNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				LoginAvt(ae);// ת���¼����
			}
		});
		loginNewButton.setBounds(225, 185, 93, 35);
		contentPane.add(loginNewButton);
		
		JLabel softnameNewLabel = new JLabel("��ӭʹ��ʮ������ V1.0��");
		softnameNewLabel.setBackground(SystemColor.window);
		softnameNewLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		softnameNewLabel.setIcon(new ImageIcon(LoginView.class.getResource("/pic/tubiao50.jpg")));
		softnameNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		softnameNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		softnameNewLabel.setBounds(39, 22, 311, 53);
		contentPane.add(softnameNewLabel);
	}



	protected void newRegister() {
		// TODO �Զ����ɵķ������
		new Register().setVisible(true); //���½���MainMenu
		this.setVisible(false);//�رձ�����
	}



	@SuppressWarnings("deprecation")
	protected void LoginAvt(ActionEvent ae) {
		//�ж��ַ����Ƿ�Ϊ��,�����������Ϊ��,����
		if(!zhanghaotextField.getText().isEmpty() && !mimapasswordField.getText().isEmpty()){ 
			//�������¼��ťʱ�����������ݿ⽨������
			DataBase dataBase = new DataBase();
			dataBase.getCon();
			boolean result = dataBase.adminLogin(zhanghaotextField.getText().trim(), mimapasswordField.getText().trim());
			if(result){
				//��¼�ɹ� 
				new MainMenu().setVisible(true); //���½���MainMenu
//				this.setVisible(false);//�رձ�����
				this.dispose();
			}else{
				//��¼ʧ��
				JOptionPane.showMessageDialog(null, "�˺Ż������������", "����", JOptionPane.ERROR_MESSAGE);
				mimapasswordField.setText("");//���������������
			}
		}else if(zhanghaotextField.getText().isEmpty()){
			JOptionPane.showMessageDialog(null,"�������û�����","��ʾ",JOptionPane.WARNING_MESSAGE);
		}else if(mimapasswordField.getText().isEmpty())	{
			JOptionPane.showMessageDialog(null,"���������룡","��ʾ",JOptionPane.WARNING_MESSAGE);
			
		}
	}
}
