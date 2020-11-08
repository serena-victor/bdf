package com.opstty.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

import com.opstty.MostWritable;

public class MostReducer extends Reducer<IntWritable, MostWritable, Text, IntWritable> {

    public void reduce(IntWritable key, Iterable<MostWritable> values, Context context) throws IOException, InterruptedException {

        MostWritable result = new MostWritable();

        for(MostWritable val: values){
            if(val.getO2().get() >result.getO2().get()){
                result.setO2(val.getO2());
                result.setO1(val.getO1());
            }                  
        }

        context.write(new Text("district containing the most trees: "), result.getO1());

    }
}
