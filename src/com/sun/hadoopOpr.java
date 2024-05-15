package com.sun;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;
import java.io.*;

public class hadoopOpr {

	// ======================================================================

	/* ��ȡ�ļ����� */
	public static ArrayList<String> view(Configuration conf, String remoteFilePath) {
		ArrayList<String> arr = new ArrayList<String> ();
		Path remotePath = new Path(remoteFilePath);
		try (FileSystem fs = FileSystem.get(conf);
				FSDataInputStream in = fs.open(remotePath);
				BufferedReader d = new BufferedReader(new InputStreamReader(in));) {
			String line;
			int count = 0;
			while ((line = d.readLine()) != null) {
				arr.add(line);
//				System.out.println(line);
			}
			return arr;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return arr;
	}// view����

	/* ����Ŀ¼ */
	public static void makeDir(Configuration conf, String remoteDir) {
		try {
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			fs.mkdirs(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* ɾ��Ŀ¼ */
	public static void delDir(Configuration conf, String remoteDir) {
		try {
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			fs.delete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ����Ŀ¼���ļ�������Ŀ¼������Ŀ¼��
	public static void lsFile(Configuration conf, String remoteDir) {
		try {
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			/* �ݹ��ȡĿ¼�µ������ļ� */
			RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(dirPath, true);
			/* ���ÿ���ļ�����Ϣ */
			while (remoteIterator.hasNext()) {
				FileStatus s = remoteIterator.next();
				System.out.println("·��: " + s.getPath().toString());
				System.out.println("Ȩ��: " + s.getPermission().toString());
				System.out.println("��С: " + s.getLen());
				/* ���ص���ʱ���,ת��Ϊʱ�����ڸ�ʽ */
				Long timeStamp = s.getModificationTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = format.format(timeStamp);
				System.out.println("ʱ��: " + date);
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// �г��ļ�
		public static List<String> listRemoteDirAndFiles(Configuration conf, String remoteDir) throws Exception {
			List<String> remoteDirList = new ArrayList<>();
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			/*�ݹ��ȡĿ¼�µ������ļ�*/
			RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(dirPath, true);
			/*���ÿ���ļ�����Ϣ*/
			while (remoteIterator.hasNext()) {
				FileStatus s = remoteIterator.next();
				String myPath = s.getPath().toString().substring(21);
				remoteDirList.add(myPath);
			}
			fs.close();
			return remoteDirList;
		}

	public static void lsDir(Configuration conf, String remoteDir) {
		
		try {
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			/* �ݹ��ȡĿ¼�µ������ļ��� */
			RemoteIterator<LocatedFileStatus> remoteIterator = fs.listLocatedStatus(dirPath);
			/* ���ÿ���ļ�����Ϣ */
			while (remoteIterator.hasNext()) {
				LocatedFileStatus d = remoteIterator.next();
				if (d.isDirectory()) 
					System.out.println(d.getPath());
					
				/*
				 * FileStatus s = remoteIterator.next(); System.out.println("·��: " +
				 * s.getPath().toString()); System.out.println("Ȩ��: " +
				 * s.getPermission().toString()); System.out.println("��С: " + s.getLen());
				 * ���ص���ʱ���,ת��Ϊʱ�����ڸ�ʽ Long timeStamp = s.getModificationTime(); SimpleDateFormat
				 * format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String date =
				 * format.format(timeStamp); System.out.println("ʱ��: " + date);
				 * System.out.println();
				 */
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<String> lsDirStr(Configuration conf, String remoteDir) {
		ArrayList<String> arr = new ArrayList<String> ();
		try {
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			/* �ݹ��ȡĿ¼�µ������ļ��� */
			RemoteIterator<LocatedFileStatus> remoteIterator = fs.listLocatedStatus(dirPath);
			/* ���ÿ���ļ�����Ϣ */
			while (remoteIterator.hasNext()) {
				FileStatus d = remoteIterator.next();
				
					String name = d.getPath().toString();
					arr.add(name.substring(21));
				
			System.out.println("====================");
			System.out.println(d.getPath());
			}
			return arr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return arr;
	}

	/* �����ļ���ָ��·�� ��·���Ѵ��ڣ�����и��� */
	public static void copyFromLocalFile(Configuration conf, String localFilePath, String remoteFilePath) {

		Path localPath = new Path(localFilePath);
		Path remotePath = new Path(remoteFilePath);

		try (FileSystem fs = FileSystem.get(conf)) {
			/* fs.copyFromLocalFile ��һ��������ʾ�Ƿ�ɾ��Դ�ļ����ڶ���������ʾ�Ƿ񸲸� */
			fs.copyFromLocalFile(false, true, localPath, remotePath);// api
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ׷���ļ�����
	public static void appendToFile(Configuration conf, String localFilePath, String remoteFilePath) {
		Path remotePath = new Path(remoteFilePath);
		try {
			FileSystem fs = FileSystem.get(conf);
			FileInputStream in = new FileInputStream(localFilePath);
			FSDataOutputStream out = fs.append(remotePath);
			byte[] data = new byte[1024];
			int read = -1;
			while ((read = in.read(data)) > 0) {
				out.write(data, 0, read);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*�����ļ������� �жϱ���·���Ƿ��Ѵ��ڣ����Ѵ��ڣ����Զ�����������*/
    public static void copyToLocal(Configuration conf, String remoteFilePath,String localFilePath) {
        Path remotePath = new Path(remoteFilePath);
        try (FileSystem fs = FileSystem.get(conf)) {
            File f = new File(localFilePath);
            /* ����ļ������ڣ��Զ�������(���ļ���������� _0, _1 ...) */
            if (f.exists()) {
                System.out.println(localFilePath + " �Ѵ���.");
                Integer i = Integer.valueOf(0);
                while (true) {
                    f = new File(localFilePath + "_" + i.toString());
                    if (!f.exists()) {
                        localFilePath = localFilePath + "_" + i.toString();
                        break;
                    } else {
                        i++;
                        continue;
                    }
                }
                System.out.println("����������Ϊ: " + localFilePath);
            }
            // �����ļ�������
            Path localPath = new Path(localFilePath);
            fs.copyToLocalFile(remotePath, localPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	/* ������ */
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		String localFilePath = "D:/hello2.txt";
		String remoteFilePath = "/user/hadoop/1.txt";// HDFS·��

		try {

			System.out.println("��ȡ�ļ�: " + remoteFilePath);

			//hadoopOpr.view(conf, remoteFilePath);

			System.out.println("����" + remoteFilePath + "���");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// main����

	// ======================================================================
}
