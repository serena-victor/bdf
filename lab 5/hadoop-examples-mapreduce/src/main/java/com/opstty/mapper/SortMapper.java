package com.opstty.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

import java.io.IOException;

public class SortMapper extends Mapper<Object, Text, LongWritable, Text> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        String line[] = value.toString().split(";");
        if (!line[6].equals("HAUTEUR")) {
            LongWritable customKey = new LongWritable(1);
            try {
                Text customValue = new Text(line[6]+";"+line[11]);
                context.write(customKey, customValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
    }
}
