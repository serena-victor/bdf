package com.opstty.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.IntWritable;
import java.io.IOException;

import com.opstty.MostWritable;

public class MostMapper extends Mapper<Object, Text, IntWritable, MostWritable> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        IntWritable one = new IntWritable(1);
        String line[] = value.toString().split("\t");
        try {
            IntWritable district = new IntWritable(Integer.parseInt(line[0]));
            IntWritable nbTree = new IntWritable(Integer.parseInt(line[1]));
            MostWritable v = new MostWritable(district,nbTree);
            context.write(one, v);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
