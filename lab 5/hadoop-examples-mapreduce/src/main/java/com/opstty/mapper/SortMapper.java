package com.opstty.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;

public class SortMapper extends Mapper<Object, Text, LongWritable, Text> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        String line[] = value.toString().split(";");
        if (!line[6].equals("HAUTEUR")) {
            LongWritable customKey = new LongWritable(1);
            try {
                FloatWritable height = new FloatWritable(Float.parseFloat(line[6]));
                IntWritable id = new IntWritable(Integer.parseInt(line[11]));
                Text customValue = new Text(String.valueOf(id.get())+";"+String.valueOf(height.get()));
                context.write(customKey, customValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
    }
}
