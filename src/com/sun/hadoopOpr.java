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

	/* 读取文件内容 */
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
	}// view函数

	/* 创建目录 */
	public static void makeDir(Configuration conf, String remoteDir) {
		try {
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			fs.mkdirs(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* 删除目录 */
	public static void delDir(Configuration conf, String remoteDir) {
		try {
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			fs.delete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 查找目录下文件或者子目录（遍历目录）
	public static void lsFile(Configuration conf, String remoteDir) {
		try {
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			/* 递归获取目录下的所有文件 */
			RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(dirPath, true);
			/* 输出每个文件的信息 */
			while (remoteIterator.hasNext()) {
				FileStatus s = remoteIterator.next();
				System.out.println("路径: " + s.getPath().toString());
				System.out.println("权限: " + s.getPermission().toString());
				System.out.println("大小: " + s.getLen());
				/* 返回的是时间戳,转化为时间日期格式 */
				Long timeStamp = s.getModificationTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = format.format(timeStamp);
				System.out.println("时间: " + date);
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 列出文件
		public static List<String> listRemoteDirAndFiles(Configuration conf, String remoteDir) throws Exception {
			List<String> remoteDirList = new ArrayList<>();
			FileSystem fs = FileSystem.get(conf);
			Path dirPath = new Path(remoteDir);
			/*递归获取目录下的所有文件*/
			RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(dirPath, true);
			/*输出每个文件的信息*/
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
			/* 递归获取目录下的所有文件夹 */
			RemoteIterator<LocatedFileStatus> remoteIterator = fs.listLocatedStatus(dirPath);
			/* 输出每个文件的信息 */
			while (remoteIterator.hasNext()) {
				LocatedFileStatus d = remoteIterator.next();
				if (d.isDirectory()) 
					System.out.println(d.getPath());
					
				/*
				 * FileStatus s = remoteIterator.next(); System.out.println("路径: " +
				 * s.getPath().toString()); System.out.println("权限: " +
				 * s.getPermission().toString()); System.out.println("大小: " + s.getLen());
				 * 返回的是时间戳,转化为时间日期格式 Long timeStamp = s.getModificationTime(); SimpleDateFormat
				 * format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String date =
				 * format.format(timeStamp); System.out.println("时间: " + date);
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
			/* 递归获取目录下的所有文件夹 */
			RemoteIterator<LocatedFileStatus> remoteIterator = fs.listLocatedStatus(dirPath);
			/* 输出每个文件的信息 */
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

	/* 复制文件到指定路径 若路径已存在，则进行覆盖 */
	public static void copyFromLocalFile(Configuration conf, String localFilePath, String remoteFilePath) {

		Path localPath = new Path(localFilePath);
		Path remotePath = new Path(remoteFilePath);

		try (FileSystem fs = FileSystem.get(conf)) {
			/* fs.copyFromLocalFile 第一个参数表示是否删除源文件，第二个参数表示是否覆盖 */
			fs.copyFromLocalFile(false, true, localPath, remotePath);// api
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 追加文件内容
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
	
	/*下载文件到本地 判断本地路径是否已存在，若已存在，则自动进行重命名*/
    public static void copyToLocal(Configuration conf, String remoteFilePath,String localFilePath) {
        Path remotePath = new Path(remoteFilePath);
        try (FileSystem fs = FileSystem.get(conf)) {
            File f = new File(localFilePath);
            /* 如果文件名存在，自动重命名(在文件名后面加上 _0, _1 ...) */
            if (f.exists()) {
                System.out.println(localFilePath + " 已存在.");
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
                System.out.println("将重新命名为: " + localFilePath);
            }
            // 下载文件到本地
            Path localPath = new Path(localFilePath);
            fs.copyToLocalFile(remotePath, localPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	/* 主函数 */
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		String localFilePath = "D:/hello2.txt";
		String remoteFilePath = "/user/hadoop/1.txt";// HDFS路径

		try {

			System.out.println("读取文件: " + remoteFilePath);

			//hadoopOpr.view(conf, remoteFilePath);

			System.out.println("下载" + remoteFilePath + "完成");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// main函数

	// ======================================================================
}
