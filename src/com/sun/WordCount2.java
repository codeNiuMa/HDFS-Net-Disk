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

public class WordCount2 {
	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	
	public void map(Object key, Text lineText, Context context) throws IOException, InterruptedException{
		StringTokenizer itrStringTokenizer = new StringTokenizer(lineText.toString(),",| |.|:");
		
		while (itrStringTokenizer.hasMoreTokens()) {
			String wordContent = itrStringTokenizer.nextToken();
			word.set(wordContent);
			context.write(word, one);
		}
	}
	}
	
	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		private IntWritable result = new IntWritable();
		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException{
			int sum = 0;
			for(IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = new Job(conf, "word count");
		job.setJarByClass(WordCount.class);
		
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		String inputfile = "hdfs://localhost:9000/user/mydir1/input/test.txt";
		FileInputFormat.addInputPath(job, new Path(inputfile));
		
		String outputfile = "hdfs://localhost:9000/user/mydir1/output";
		FileOutputFormat.setOutputPath(job, new Path(outputfile));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}

}
