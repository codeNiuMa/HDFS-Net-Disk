package com.sun;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class HDFSUtil {
	private static String hdfsURL="hdfs://localhost:9000";
	private static Configuration conf ;
	static {
	    conf = new Configuration();
		conf.set("fs.defaultFS",hdfsURL);
	}
	public static void downloadFromHDFS(String remoteFile,String localFile) throws Exception {   //��Hadoop�����ļ���Windows����
		FileSystem fs = FileSystem.get(conf);
		Path remotePath = new Path(remoteFile);
	    Path localPath = new Path(localFile);
        fs.copyToLocalFile(remotePath, localPath);
        fs.close();
	}
	public static void uploadToHDFS(String localfile,String remotefile) throws Exception {   //�ϴ�Windows�����ļ���Hadoop
		FileSystem fs = FileSystem.get(conf);
		Path remotePath = new Path(remotefile);
	    Path localPath = new Path(localfile);
        fs.copyFromLocalFile(localPath, remotePath);
        fs.close();
	}	
	/**
     * ����ָ���ļ����������ļ�����Ϣ
     */
	public static List<FileStatus> listFiles(String dir)  throws Exception{
		List<FileStatus> files=new ArrayList<FileStatus>();
		FileSystem fs = FileSystem.get(conf);
		Path dirPath = new Path(dir);
		RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(dirPath,false);
		while(remoteIterator.hasNext()) {
			files.add(remoteIterator.next());
		}
		return files;
	}
	
	public static List<String> listFilesStr(String dir)  throws Exception{
		List<String> files=new ArrayList<String>();
		FileSystem fs = FileSystem.get(conf);
		Path dirPath = new Path(dir);
		RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(dirPath,true);
		while(remoteIterator.hasNext()) {
			files.add(remoteIterator.next().getPath().toString());
		}
		return files;
	}
	/**
     * ����ָ���ļ������������ļ��е���Ϣ
     */
	public static List<FileStatus> listSubDir(String dir)  throws Exception{	
		List<FileStatus> dirs=new ArrayList<FileStatus>();
		FileSystem fs = FileSystem.get(conf);
		Path dirPath = new Path(dir);
		FileStatus[] files=fs.listStatus(dirPath);
		if(files!=null && files.length>0) {
			for(int i=0;i<files.length;i++) {
				FileStatus file=files[i];
				if(file.isDirectory())
					dirs.add(file);
			}
		}
		return dirs;
	}

}
