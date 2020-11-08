package com.opstty.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.FloatWritable;

import java.io.IOException;

public class HighestMapper extends Mapper<Object, Text, Text, FloatWritable> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        String line[] = value.toString().split(";");
        if (!line[3].equals("ESPECE")) {
            Text species = new Text(line[3]);
            try {
                float tmp = Float.parseFloat(line[6]);
                FloatWritable height = new FloatWritable(tmp);
                context.write(species, height);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
    }
}
