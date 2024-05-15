package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.fasterxml.jackson.core.sym.Name2;
import com.sun.*;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;

import javax.swing.border.BevelBorder;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.event.TreeSelectionEvent;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	JTree lefttree;
	JSplitPane splitPane;
	JScrollPane treescrollPane = new JScrollPane();
	private final String[] cols= {"����","����","����","��С"};
	
    static DefaultTreeModel newModel;
    private static Configuration conf ;
    private static String hdfsURL="hdfs://localhost:9000";
    static {
        conf = new Configuration();
        conf.set("fs.defaultFS",hdfsURL);
    }
    static DefaultMutableTreeNode temp;
    static DefaultMutableTreeNode Node;
    
    private static JTable table;
    private JTextField pathtextField;
    JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void inittree() {
		lefttree = new JTree();
		lefttree.setMinimumSize(new Dimension(78, 64));
		lefttree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				getPath(e);
				showText(e);
			}
		});
		try {
			Node=traverseFolder("/");
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		
		
        newModel=new DefaultTreeModel(Node);
        lefttree.setModel(newModel);
        treescrollPane.setViewportView(lefttree);
	}
	
	
	
	/**
	 * Create the frame.
	 */
	public MainMenu() {
		setResizable(false);
		setTitle("\u5341\u5EA6\u7F51\u76D8");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(560, 240, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setBorderPainted(false);
		setJMenuBar(menuBar);
		
		JMenu xuanxiangNewMenu = new JMenu("\u9009\u9879");
		menuBar.add(xuanxiangNewMenu);
		
		JMenuItem zhuxiaoNewMenuItem = new JMenuItem("\u6CE8\u9500");
		zhuxiaoNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zhuxiao(e);
			}
		});
		zhuxiaoNewMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		xuanxiangNewMenu.add(zhuxiaoNewMenuItem);
		
		JMenuItem gaimimaMenuItem = new JMenuItem("\u4FEE\u6539\u5BC6\u7801");
		gaimimaMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changepsw(e);
			}
		});
		xuanxiangNewMenu.add(gaimimaMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		splitPane = new JSplitPane();
		splitPane.setBounds(0, 0, 784, 537);
		contentPane.add(splitPane);
		
		
		treescrollPane.setBackground(Color.WHITE);
		splitPane.setLeftComponent(treescrollPane);
		
		inittree();
		
		
		
		
		
        
        JPanel rightpanel = new JPanel();
        splitPane.setRightComponent(rightpanel);
        rightpanel.setLayout(null);
        
        JPanel gongnengpanel = new JPanel();
        gongnengpanel.setBounds(0, 0, 578, 69);
        gongnengpanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        rightpanel.add(gongnengpanel);
        gongnengpanel.setLayout(null);
        
        JButton shangchuanNewButton = new JButton("\u4E0A \u4F20");
        shangchuanNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		upload(e);
        	}
        });
        shangchuanNewButton.setFont(new Font("΢���ź�", Font.PLAIN, 16));
        shangchuanNewButton.setBounds(46, 10, 93, 49);
        gongnengpanel.add(shangchuanNewButton);
        
        JButton xiazaiNewButton = new JButton("\u4E0B \u8F7D");
        xiazaiNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		download(e);
        	}
        });
        xiazaiNewButton.setFont(new Font("΢���ź�", Font.PLAIN, 16));
        xiazaiNewButton.setBounds(175, 10, 93, 49);
        gongnengpanel.add(xiazaiNewButton);
        
        JButton xinjianNewButton = new JButton("\u65B0 \u5EFA");
        xinjianNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		makeNewDir(e);
        	}
        });
        xinjianNewButton.setFont(new Font("΢���ź�", Font.PLAIN, 16));
        xinjianNewButton.setBounds(308, 10, 93, 49);
        gongnengpanel.add(xinjianNewButton);
        
        JButton shanchuNewButton = new JButton("\u5220 \u9664");
        shanchuNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		delete(e);
        	}
        });
        shanchuNewButton.setFont(new Font("΢���ź�", Font.PLAIN, 16));
        shanchuNewButton.setBounds(439, 10, 93, 49);
        gongnengpanel.add(shanchuNewButton);
        
        String[][] rows=new String[30][4];
        
        pathtextField = new JTextField("Hello! Your HDFS path is "+hdfsURL);
        pathtextField.setBounds(10, 502, 558, 33);
        pathtextField.setFont(new Font("Cascadia Code", Font.PLAIN, 15));
        rightpanel.add(pathtextField);
        pathtextField.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("\u6587\u4EF6\u5185\u5BB9\uFF1A");
        lblNewLabel.setBounds(10, 79, 98, 15);
        lblNewLabel.setFont(new Font("����", Font.PLAIN, 14));
        rightpanel.add(lblNewLabel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 104, 558, 388);
        rightpanel.add(scrollPane);
        
        textPane = new JTextPane();
        scrollPane.setViewportView(textPane);
        textPane.setFont(new Font("΢���ź�", Font.PLAIN, 16));
        textPane.setBackground(new Color(255, 250, 240));
        textPane.setBounds(0, 0, 558, 388);
        
        
		
        
		splitPane.setDividerLocation(200);
		
	}
	
	//ɾ���ļ�
	protected void delete(ActionEvent e) {
		// TODO �Զ����ɵķ������
		String message = "��������Ҫɾ�����ļ������ļ�������\n";
		try {
			List<String> hdfsDir = hadoopOpr.listRemoteDirAndFiles(conf, "/");
			for (int k = 0; k < hdfsDir.size(); k++) {
				message += hdfsDir.get(k) + "\n";
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String deleteName = pathtextField.getText();

		if (!deleteName.startsWith("hdfs")) {
			JOptionPane.showMessageDialog(null, "��ѡ����ЧĿ¼or�ļ���", "����", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				int result1 = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��"+deleteName+"???", "��ʾ", JOptionPane.OK_CANCEL_OPTION);
				if(result1==0) {		//0��ȷ��
					hadoopOpr.delDir(conf, deleteName);
					treescrollPane.getViewport().removeAll();//����get���������Ը�����
					inittree();
					JOptionPane.showMessageDialog(null, "ɾ��"+deleteName+"�ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("ɾ���ļ�/�ļ��гɹ�!");
	}


	//�������ļ���
	protected void makeNewDir(ActionEvent e) {
		// TODO �Զ����ɵķ������
		Path path = null;
		String nowpath = pathtextField.getText();
		
		
		try {
			FileSystem fs = FileSystem.get(conf);
			if(!nowpath.startsWith("Hel")) {
				path = new Path(nowpath);
				if(fs.isFile(path)) {
					JOptionPane.showMessageDialog(null, "��ѡ����ЧĿ¼��", "����", JOptionPane.ERROR_MESSAGE);
				}else {
					String remoteDir = JOptionPane.showInputDialog(null, "��Ŀ¼����");
					if (remoteDir.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Ŀ¼������Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE);
					} else {
						

						try {
							hadoopOpr.makeDir(conf, nowpath+"/"+remoteDir);
							treescrollPane.getViewport().removeAll();//����get���������Ը�����
							inittree();
							JOptionPane.showMessageDialog(null, "�½�Ŀ¼�ɹ���", "�½�", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					
				}
			}else {
				JOptionPane.showMessageDialog(null, "δѡ����Ч�ļ���", "���ص�ǰ�ļ�", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e2) {
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		}
		
		
		
		
		
	}


	//���ذ�ť����ʵ��
	protected void download(ActionEvent e) {
		// TODO �Զ����ɵķ������
		String message = "׼�����ص�ǰѡ���ļ���\n";
		String downfrom = pathtextField.getText();
		Path path = null;
		
		try {
			FileSystem fs = FileSystem.get(conf);
			if(!downfrom.startsWith("Hel")) {
				path = new Path(downfrom);
			}else {
				JOptionPane.showMessageDialog(null, "δѡ����Ч�ļ���", "���ص�ǰ�ļ�", JOptionPane.ERROR_MESSAGE);
			}
				
			
				if(fs.isFile(path)) {
					//��һ���ļ�
					int result1 = JOptionPane.showConfirmDialog(null, message+downfrom, "����", JOptionPane.OK_CANCEL_OPTION);
					if(result1 == 0) {  //0 is yes
						//windows
						JFileChooser jf = new JFileChooser();
						jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						jf.setDialogTitle("��ѡ��Ҫ�ϴ����ļ���...");
						int result2 = jf.showDialog(null, null);
						
						if (result2 == JFileChooser.CANCEL_OPTION) {			
							JOptionPane.showMessageDialog(null, "ȡ�����ļ�����", "����", JOptionPane.INFORMATION_MESSAGE);
						}else {
							try {
								String destPath = jf.getSelectedFile().getAbsolutePath() + "/";
								HDFSUtil.downloadFromHDFS(downfrom, destPath);
								System.out.println("���سɹ�!");
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}else {
						JOptionPane.showMessageDialog(null, "ȡ�����ļ�����", "����", JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "δѡ����Ч�ļ���", "���ص�ǰ�ļ�", JOptionPane.ERROR_MESSAGE);
				}
			
		} catch (HeadlessException e2) {
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		}
		
		
		
	}

	
	//�ϴ��ļ���ť
	protected void upload(ActionEvent e) {
		// TODO �Զ����ɵķ������
		System.out.println("upload");
		JFileChooser jf = new JFileChooser();
		jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jf.setDialogTitle("��ѡ���ļ�...");
		jf.showDialog(null, null);
		String srcPath = jf.getSelectedFile().getAbsolutePath() + "/";
		String destPath = pathtextField.getText();
		if (srcPath.isEmpty()) {
			System.out.println("�����ļ�·������Ϊ��!");
		} else {
			if (!destPath.isEmpty()) {
				try {
					HDFSUtil.uploadToHDFS(srcPath, destPath);
					treescrollPane.getViewport().removeAll();//����get���������Ը�����
					inittree();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println("HDFS·������Ϊ��!");
			}

		}
		pathtextField.setText("Upload Success!");
		System.out.println("�ϴ��ɹ�!");
	}


	//��ʾ�ļ�����
	protected void showText(TreeSelectionEvent e) {
		// TODO �Զ����ɵķ������
		TreePath path = e.getPath();
		String pathStr;
		if(path.toString().equals("[/]")) {
			pathStr = "/";
		}else {
			String name1 = path.toString().substring(path.toString().indexOf(","),path.toString().lastIndexOf("]"));
	        pathStr = name1.replace(",", "/").replace(" ", "");
		}
		
		String wholePath = hdfsURL+pathStr;
		
		
		
		try {
			FileSystem fs = FileSystem.get(conf);
			Path filepath = new Path(wholePath);
			
			if(!fs.isFile(filepath)) {
				ArrayList<String> arr1 = new ArrayList<String> ();;
//				textPane.setText("���Ǹ��ļ���");
				arr1 = hadoopOpr.lsDirStr(conf, wholePath);
				textPane.setText("��Ŀ¼�°�����\n");
				System.out.println(arr1.size());
				for (int i = 0; i < arr1.size(); i++) {
					textPane.setCaretPosition(textPane.getDocument().getLength());
					textPane.replaceSelection(arr1.get(i)+"\n");

				}
			}else {
				ArrayList<String> arr2 = new ArrayList<String> ();
				arr2 = hadoopOpr.view(conf, wholePath);
				textPane.setText("");
				for (int i = 0; i < arr2.size(); i++) {
					textPane.setCaretPosition(textPane.getDocument().getLength());
					textPane.replaceSelection(arr2.get(i)+"\n");
				}
			}
		}catch (Exception e1) {
			// TODO: handle exception
			System.out.println("exception");
		}
	}


	//�õ�ѡ�е��ļ�·����ʾ
	protected void getPath(TreeSelectionEvent e) {
		// TODO �Զ����ɵķ������
		TreePath path = e.getPath();
		String pathStr;
		if(path.toString().equals("[/]")) {
			pathStr = "/";
		}else {
			String name1 = path.toString().substring(path.toString().indexOf(","),path.toString().lastIndexOf("]"));
	        pathStr = name1.replace(",", "/").replace(" ", "");
		}
		pathtextField.setText(hdfsURL+pathStr);
	}

	
	//�޸�����
	protected void changepsw(ActionEvent e) {
		// TODO �Զ����ɵķ������
		new ChangePsw().setVisible(true);
		this.setVisible(false);
	}



	protected void zhuxiao(ActionEvent e) {
		// TODO �Զ����ɵķ������
		new LoginView().setVisible(true);
		this.setVisible(false);
	}



	//���ݹ����
	public static DefaultMutableTreeNode traverseFolder(String path) throws IOException {
		String nodeName;
		if(path.equals("/")) {
			nodeName="/";
		}else {
			nodeName = path.substring(path.lastIndexOf('/')+1);//ʵ�ֵ����ļ�����ʾ
		}
		
        DefaultMutableTreeNode fujiedian = new DefaultMutableTreeNode(nodeName);
        File file = new File(path);
        try {
            FileSystem fs = FileSystem.get(conf);
            Path dirPath = new Path(path);
            FileStatus[] files = fs.listStatus(dirPath);
            if (files.length == 0) {
                if (file.isDirectory()) {// ����ǿ��ļ���
                    return new DefaultMutableTreeNode(path);
                }
            } else {
                for (FileStatus file2 : files) {
                    if (file2.isDirectory()) {
                        // ��Ŀ����¼�Ļ������ɽڵ㣬����ӵĽڵ�
                    	String fileName = file2.getPath().toString();
                        System.out.println("Dir: "+file2.getPath().toString().substring(fileName.lastIndexOf('/')+1, fileName.length()));
                        fujiedian.add(traverseFolder(fileName.substring(21, fileName.toString().length())));
                    } else {
                        // ���ļ��Ļ�ֱ�����ɽڵ㣬���Ѹýڵ�ӵ���Ӧ���ڵ���
                    	String fileName = file2.getPath().toString();
                        System.out.println("file: "+fileName.substring(fileName.lastIndexOf('/')+1, fileName.length()));
                        
                        String name = new StringBuffer(fileName.substring(fileName.lastIndexOf('/')+1, fileName.length())).toString();
                        temp = new DefaultMutableTreeNode(name);
                        fujiedian.add(temp);
                    }
                }
            }
            return fujiedian;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fujiedian;
    }
}
