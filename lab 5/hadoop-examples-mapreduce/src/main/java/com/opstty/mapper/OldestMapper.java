package com.opstty.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;

import com.opstty.OldestWritable;

public class OldestMapper extends Mapper<Object, Text, LongWritable, OldestWritable> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        LongWritable keyy = new LongWritable(1);

        String line[] = value.toString().split(";");
        if (!line[1].equals("ARRONDISSEMENT")) {
            try {
                IntWritable district = new IntWritable(Integer.parseInt(line[1]));
                LongWritable age = new LongWritable(Long.parseLong(line[5]));

                OldestWritable valuee = new OldestWritable(district, age);
                context.write(keyy, valuee);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }
}
