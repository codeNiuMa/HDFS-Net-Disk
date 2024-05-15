package com.sun;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.hadoop.fs.FileStatus;

public class myHDFSFrm extends JFrame{
	private JTree leftTree;
	private JTable table;
	private String[] cols= {"名称","日期","类型","大小"};
	
	private void initTable() {
		String[][] rows=new String[10][4];
		table=new JTable(rows,cols);
	}
	private void initTree() {
		try {
		    List<FileStatus> dirs=HDFSUtil.listSubDir("/");
		    DefaultMutableTreeNode root=new DefaultMutableTreeNode("MyHDFS");
		    for(FileStatus dir:dirs) {
		    	root.add(new DefaultMutableTreeNode(dir.getPath().toString()));
		    }
			leftTree=new JTree(root);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
    public myHDFSFrm() {
    	JPanel jp=(JPanel)this.getContentPane();
    	jp.setLayout(new BorderLayout());
    	initTable();
    	initTree();
    	JScrollPane jsp_tree=new JScrollPane(leftTree);
    	JScrollPane jsp_table=new JScrollPane(table);
    	JSplitPane jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp_tree,jsp_table);
    	jsp.setDividerLocation(200);
    	jp.add(jsp);
    	this.setTitle("HDFS文件管理系统");
    	this.setSize(800,600);
    	this.setVisible(true);
    }
    public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		new myHDFSFrm();
	}
}

