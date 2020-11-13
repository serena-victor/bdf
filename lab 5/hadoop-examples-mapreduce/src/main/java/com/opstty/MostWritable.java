package com.opstty;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

public class MostWritable implements Writable {
    private IntWritable o1;
    private IntWritable o2;

    public MostWritable() {
        this.o1 = new IntWritable(-1);
        this.o2 = new IntWritable(-1);
    }
    public MostWritable(IntWritable o1, IntWritable o2) {
        this.o1 = o1;
        this.o2 = o2;
    }
    
    public IntWritable getO1() {        return this.o1;    }
    public void setO1(IntWritable o1) {        this.o1 = new IntWritable(o1.get());    }
    public IntWritable getO2() {         return this.o2;    }
    public void setO2(IntWritable o2) {        this.o2 = new IntWritable(o2.get());    }
    
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


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MostWritable)) {
            return false;
        }
        
        MostWritable mostWritable = (MostWritable) o;
        return Objects.equals(o1, mostWritable.o1) && Objects.equals(o2, mostWritable.o2);
    }
}
