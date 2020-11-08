package com.opstty.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TreesByDistrictReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

    public void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

                int sum=0;
        for (IntWritable val : values) {
            sum+=val.get();
        }
        IntWritable result = new IntWritable(sum);

        context.write(key,result);

 }
}
