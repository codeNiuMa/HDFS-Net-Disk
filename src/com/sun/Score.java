package com.sun;

//import
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.yarn.webapp.ForbiddenException;


public class Score {
	

	public static class Map extends Mapper<LongWritable,Text, Text, DoubleWritable>{
		
		private Text gender = new Text();
		private DoubleWritable score = new DoubleWritable();
		
		protected void map(LongWritable key, Text value, Context context) 
					throws IOException, InterruptedException{
			String lineData = value.toString();
			
			//ÇÐ·Ö
			String[] infos = lineData.split(",");
			
			this.gender.set(infos[2]);
			this.score.set(Double.parseDouble(infos[3]));
			context.write(this.gender, this.score);
		}//map
	}	
		public static class Reduce extends Reducer<Text, DoubleWritable, Text, Text>{
			private static Text staInfo = new Text();
			protected void reduce(Text key, Iterable<DoubleWritable> values, Context context) 
					throws IOException, InterruptedException{
				double sumScore = 0;
				double count =0;
				double max = -1;
				double min = 100;
				for(DoubleWritable score : values){
					count += 1;
					sumScore += score.get();
					if(score.get() > max)
						max = score.get();
					if(score.get() < min)
						min = score.get();
				}
				staInfo.set( (sumScore / count) + " " + max + " " + min);
				
				context.write(key, staInfo);
			}//reduce
			
			
		}
		
		
		public static void main(String[] args) throws Exception{
			Configuration conf = new Configuration();
			Job job = new Job(conf, "score");
			job.setJarByClass(Score.class);
			
			job.setMapperClass(Map.class);
			//job.setCombinerClass(Reduce.class);
			job.setReducerClass(Reduce.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(DoubleWritable.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			String inputfile = "hdfs://localhost:9000/user/mydir1/input/score.txt";
			FileInputFormat.addInputPath(job, new Path(inputfile));
			
			String outputfile = "hdfs://localhost:9000/user/mydir1/outscore";
			FileOutputFormat.setOutputPath(job, new Path(outputfile));
			
			System.exit(job.waitForCompletion(true) ? 0 : 1);
			
		}

	}
