package com.sun;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class repeat {
	
	//map�������е�value���Ƶ�������ݵ�key�ϣ���ֱ�����
	public static class Map extends Mapper<Object,Text,Text,Text>{
		private static Text line=new Text();//ÿ1������
		
		//ʵ��map����
		public void map(Object key, Text value, Context context)
				throws IOException,InterruptedException{
		line=value;
		context.write(line, new Text(""));
		}
	}
	
	//reduce�������е�key���Ƶ�������ݵ�key�ϣ���ֱ�����
	public static class Reduce extends Reducer<Text,Text,Text,Text>{
	//ʵ��reduce����
		public void reduce(Text key,Iterable<Text> values,Context context)
				throws IOException, InterruptedException{
		context.write(key,new Text(""));
		}
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Filter");
		job.setJarByClass(repeat.class);
		
		//����Map��Reduce������
		job.setMapperClass(Map.class);
		//job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		
		//�������
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		//�������Ŀ¼
		String inputfile = "hdfs://localhost:9000/user/mydir1/input/repeat.txt";
		FileInputFormat.addInputPath(job, new Path(inputfile));
		String outputfile = "hdfs://localhost:9000/user/mydir1/outputfilter";
		FileOutputFormat.setOutputPath(job, new Path(outputfile));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
