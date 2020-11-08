package com.opstty.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

public class SpeciesMapper extends Mapper<Object, Text, Text, NullWritable> {

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        if(!line.contains("ESPECE")){
            Text species = new Text(line.split(";")[3
            ]);
            //only need the district number to write null value
            context.write(species, NullWritable.get());
        }
    }
}
