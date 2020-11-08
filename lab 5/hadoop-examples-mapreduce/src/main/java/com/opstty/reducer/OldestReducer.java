package com.opstty.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import com.opstty.OldestWritable;

public class OldestReducer extends Reducer<LongWritable, OldestWritable, Text, IntWritable> {

    public void reduce(LongWritable key, Iterable<OldestWritable> values, Context context)
            throws IOException, InterruptedException {


        OldestWritable result = new OldestWritable();

        for (OldestWritable val : values) {

            if (val.getO2().get() < result.getO2().get()) {
                //cant assign val.getO1() because of reference
                result.setO1(new IntWritable(val.getO1().get()));
                result.setO2(new LongWritable(val.getO2().get()));
            }
        }

        context.write(new Text("The oldest tree can be find in district"), result.getO1());

 }
}
