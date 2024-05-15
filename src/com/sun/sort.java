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


public class sort {
	
	//map 将输入中的value复制到输出数据的key 上，并直接输出
	public static class Map extends Mapper<Object,Text,IntWritable,IntWritable>{
		
		private static IntWritable data=new IntWritable();//实现map函数
		
			public void map(Object key,Text value,Context context)
					throws IOException, InterruptedException{
			String line=value.toString();
			data.set(Integer.parseInt(line));
			context.write(data, new IntWritable(1));
			}
	}
	
	public static class Reduce extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable>{
		private static IntWritable linenum = new IntWritable(1);
		//实现reduce函数
		public void reduce(IntWritable key,Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException{
			
			for( IntWritable val:values){
				context.write(linenum, key);
				linenum = new IntWritable(linenum.get()+1);
			}
			
		}
	}

	
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = new Job(conf, "sort");
		job.setJarByClass(sort.class);
		
		job.setMapperClass(Map.class);
		//job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		String inputfile = "hdfs://localhost:9000/user/mydir1/input/sort.txt";
		FileInputFormat.addInputPath(job, new Path(inputfile));
		
		String outputfile = "hdfs://localhost:9000/user/mydir1/outputsort";
		FileOutputFormat.setOutputPath(job, new Path(outputfile));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	

}
