package com.opstty.reducer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SortReducerTest {
    @Mock
    private Reducer.Context context;
    private SortReducer reducer;

    @Before
    public void setup() {
        this.reducer = new SortReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        LongWritable key = new LongWritable(1);
        Text value = new Text("0;1.0");
        Iterable<Text> values = Arrays.asList(value);

        this.reducer.reduce(key, values, this.context);
        verify(this.context, times(1))
            .write(new Text("0"), new FloatWritable(1));
    }
}