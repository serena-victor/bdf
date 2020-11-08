package com.opstty.job;

import com.opstty.MostWritable;
import com.opstty.mapper.MostMapper;
import com.opstty.mapper.TreesByDistrictMapper;
import com.opstty.reducer.MostReducer;
import com.opstty.reducer.TreesByDistrictReducer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MostTrees {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: most <in> [<in>...] <out>");
            System.exit(2);
        }

        Path tmp = new Path("test/");
        conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", ";");

        Job job = Job.getInstance(conf, "Trees by district");

        job.setJarByClass(MostTrees.class);
        job.setMapperClass(TreesByDistrictMapper.class);
        job.setReducerClass(TreesByDistrictReducer.class);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job, tmp);
        job.waitForCompletion(true);

        Configuration conf2 = new Configuration();
        Job job2 = Job.getInstance(conf2, "District with the most trees");
        job2.setJarByClass(MostTrees.class);
        job2.setMapperClass(MostMapper.class);
        job2.setReducerClass(MostReducer.class);

        job2.setMapOutputKeyClass(IntWritable.class);
        job2.setMapOutputValueClass(MostWritable.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job2, tmp);
        FileOutputFormat.setOutputPath(job2, new Path(otherArgs[otherArgs.length - 1]));

        job2.waitForCompletion(true);
        
        FileSystem fs = FileSystem.get(conf2);
        fs.delete(tmp,true);
        System.exit(1);
    }
}
