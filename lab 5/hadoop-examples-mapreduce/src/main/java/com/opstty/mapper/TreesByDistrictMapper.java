package com.opstty.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.IntWritable;
import java.io.IOException;

public class TreesByDistrictMapper extends Mapper<Object, Text, IntWritable, IntWritable> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        IntWritable one = new IntWritable(1);

        String line[] = value.toString().split(";");
        if (!line[1].equals("ARRONDISSEMENT")) {
            try {
                IntWritable district = new IntWritable(Integer.parseInt(line[1]));
                context.write(district, one);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }
}
