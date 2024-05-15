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
	private final String[] cols= {"名称","日期","类型","大小"};
	
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
			// TODO 自动生成的 catch 块
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
        shangchuanNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        shangchuanNewButton.setBounds(46, 10, 93, 49);
        gongnengpanel.add(shangchuanNewButton);
        
        JButton xiazaiNewButton = new JButton("\u4E0B \u8F7D");
        xiazaiNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		download(e);
        	}
        });
        xiazaiNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        xiazaiNewButton.setBounds(175, 10, 93, 49);
        gongnengpanel.add(xiazaiNewButton);
        
        JButton xinjianNewButton = new JButton("\u65B0 \u5EFA");
        xinjianNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		makeNewDir(e);
        	}
        });
        xinjianNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        xinjianNewButton.setBounds(308, 10, 93, 49);
        gongnengpanel.add(xinjianNewButton);
        
        JButton shanchuNewButton = new JButton("\u5220 \u9664");
        shanchuNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		delete(e);
        	}
        });
        shanchuNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
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
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        rightpanel.add(lblNewLabel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 104, 558, 388);
        rightpanel.add(scrollPane);
        
        textPane = new JTextPane();
        scrollPane.setViewportView(textPane);
        textPane.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        textPane.setBackground(new Color(255, 250, 240));
        textPane.setBounds(0, 0, 558, 388);
        
        
		
        
		splitPane.setDividerLocation(200);
		
	}
	
	//删除文件
	protected void delete(ActionEvent e) {
		// TODO 自动生成的方法存根
		String message = "请输入需要删除的文件或者文件夹名：\n";
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
			JOptionPane.showMessageDialog(null, "请选择有效目录or文件！", "错误", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				int result1 = JOptionPane.showConfirmDialog(null, "确定要删除"+deleteName+"???", "提示", JOptionPane.OK_CANCEL_OPTION);
				if(result1==0) {		//0是确定
					hadoopOpr.delDir(conf, deleteName);
					treescrollPane.getViewport().removeAll();//加了get。。。可以更新了
					inittree();
					JOptionPane.showMessageDialog(null, "删除"+deleteName+"成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("删除文件/文件夹成功!");
	}


	//创建新文件夹
	protected void makeNewDir(ActionEvent e) {
		// TODO 自动生成的方法存根
		Path path = null;
		String nowpath = pathtextField.getText();
		
		
		try {
			FileSystem fs = FileSystem.get(conf);
			if(!nowpath.startsWith("Hel")) {
				path = new Path(nowpath);
				if(fs.isFile(path)) {
					JOptionPane.showMessageDialog(null, "请选择有效目录！", "错误", JOptionPane.ERROR_MESSAGE);
				}else {
					String remoteDir = JOptionPane.showInputDialog(null, "新目录名：");
					if (remoteDir.isEmpty()) {
						JOptionPane.showMessageDialog(null, "目录名不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
					} else {
						

						try {
							hadoopOpr.makeDir(conf, nowpath+"/"+remoteDir);
							treescrollPane.getViewport().removeAll();//加了get。。。可以更新了
							inittree();
							JOptionPane.showMessageDialog(null, "新建目录成功！", "新建", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					
				}
			}else {
				JOptionPane.showMessageDialog(null, "未选中有效文件！", "下载当前文件", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}
		
		
		
		
		
	}


	//下载按钮功能实现
	protected void download(ActionEvent e) {
		// TODO 自动生成的方法存根
		String message = "准备下载当前选中文件：\n";
		String downfrom = pathtextField.getText();
		Path path = null;
		
		try {
			FileSystem fs = FileSystem.get(conf);
			if(!downfrom.startsWith("Hel")) {
				path = new Path(downfrom);
			}else {
				JOptionPane.showMessageDialog(null, "未选中有效文件！", "下载当前文件", JOptionPane.ERROR_MESSAGE);
			}
				
			
				if(fs.isFile(path)) {
					//是一个文件
					int result1 = JOptionPane.showConfirmDialog(null, message+downfrom, "下载", JOptionPane.OK_CANCEL_OPTION);
					if(result1 == 0) {  //0 is yes
						//windows
						JFileChooser jf = new JFileChooser();
						jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						jf.setDialogTitle("请选择要上传的文件夹...");
						int result2 = jf.showDialog(null, null);
						
						if (result2 == JFileChooser.CANCEL_OPTION) {			
							JOptionPane.showMessageDialog(null, "取消了文件下载", "下载", JOptionPane.INFORMATION_MESSAGE);
						}else {
							try {
								String destPath = jf.getSelectedFile().getAbsolutePath() + "/";
								HDFSUtil.downloadFromHDFS(downfrom, destPath);
								System.out.println("下载成功!");
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}else {
						JOptionPane.showMessageDialog(null, "取消了文件下载", "下载", JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "未选中有效文件！", "下载当前文件", JOptionPane.ERROR_MESSAGE);
				}
			
		} catch (HeadlessException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}
		
		
		
	}

	
	//上传文件按钮
	protected void upload(ActionEvent e) {
		// TODO 自动生成的方法存根
		System.out.println("upload");
		JFileChooser jf = new JFileChooser();
		jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jf.setDialogTitle("请选择文件...");
		jf.showDialog(null, null);
		String srcPath = jf.getSelectedFile().getAbsolutePath() + "/";
		String destPath = pathtextField.getText();
		if (srcPath.isEmpty()) {
			System.out.println("本地文件路径不能为空!");
		} else {
			if (!destPath.isEmpty()) {
				try {
					HDFSUtil.uploadToHDFS(srcPath, destPath);
					treescrollPane.getViewport().removeAll();//加了get。。。可以更新了
					inittree();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println("HDFS路径不能为空!");
			}

		}
		pathtextField.setText("Upload Success!");
		System.out.println("上传成功!");
	}


	//显示文件内容
	protected void showText(TreeSelectionEvent e) {
		// TODO 自动生成的方法存根
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
//				textPane.setText("这是个文件夹");
				arr1 = hadoopOpr.lsDirStr(conf, wholePath);
				textPane.setText("此目录下包含：\n");
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


	//得到选中的文件路径显示
	protected void getPath(TreeSelectionEvent e) {
		// TODO 自动生成的方法存根
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

	
	//修改密码
	protected void changepsw(ActionEvent e) {
		// TODO 自动生成的方法存根
		new ChangePsw().setVisible(true);
		this.setVisible(false);
	}



	protected void zhuxiao(ActionEvent e) {
		// TODO 自动生成的方法存根
		new LoginView().setVisible(true);
		this.setVisible(false);
	}



	//树递归代码
	public static DefaultMutableTreeNode traverseFolder(String path) throws IOException {
		String nodeName;
		if(path.equals("/")) {
			nodeName="/";
		}else {
			nodeName = path.substring(path.lastIndexOf('/')+1);//实现单个文件名显示
		}
		
        DefaultMutableTreeNode fujiedian = new DefaultMutableTreeNode(nodeName);
        File file = new File(path);
        try {
            FileSystem fs = FileSystem.get(conf);
            Path dirPath = new Path(path);
            FileStatus[] files = fs.listStatus(dirPath);
            if (files.length == 0) {
                if (file.isDirectory()) {// 如果是空文件夹
                    return new DefaultMutableTreeNode(path);
                }
            } else {
                for (FileStatus file2 : files) {
                    if (file2.isDirectory()) {
                        // 是目里面录的话，生成节点，并添加的节点
                    	String fileName = file2.getPath().toString();
                        System.out.println("Dir: "+file2.getPath().toString().substring(fileName.lastIndexOf('/')+1, fileName.length()));
                        fujiedian.add(traverseFolder(fileName.substring(21, fileName.toString().length())));
                    } else {
                        // 是文件的话直接生成节点，并把该节点加到对应父节点上
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
