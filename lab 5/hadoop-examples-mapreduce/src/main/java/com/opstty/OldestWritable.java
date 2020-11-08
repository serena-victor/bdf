package com.opstty;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

public class OldestWritable implements Writable {
    private IntWritable o1;
    private LongWritable o2;

    public OldestWritable() {
        this.o1 = new IntWritable(-1);
        this.o2 = new LongWritable(Long.MAX_VALUE);
    }
    public OldestWritable(IntWritable o1, LongWritable o2) {
        this.o1 = o1;
        this.o2 = o2;
    }
    
    public IntWritable getO1() {        return this.o1;    }
    public void setO1(IntWritable o1) {        this.o1 = o1;    }
    public LongWritable getO2() {         return this.o2;    }
    public void setO2(LongWritable o2) {        this.o2 = o2;    }
    
    @Override
    public void readFields(DataInput in) throws IOException {
        o1.readFields(in);
        o2.readFields(in);
    }
    @Override
    public void write(DataOutput out) throws IOException {
        o1.write(out);
        o2.write(out);
    }
    @Override
    public String toString() {
        return o1.toString() + ";" + o2.toString();
    }
}
