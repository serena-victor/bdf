package com.opstty.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.verify;

import com.opstty.OldestWritable;

@RunWith(MockitoJUnitRunner.class)
public class OldestReducerTest {
    @Mock
    private Reducer.Context context;
    private OldestReducer reducer;

    @Before
    public void setup() {
        this.reducer = new OldestReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        LongWritable key = new LongWritable(1);
        OldestWritable value1 = new OldestWritable(new IntWritable(1), new LongWritable(10));
        OldestWritable value2 = new OldestWritable(new IntWritable(2), new LongWritable(5));
        Iterable<OldestWritable> values = Arrays.asList(value1, value2);

        this.reducer.reduce(key, values, this.context);
        verify(this.context).write(new Text("The oldest tree can be find in district"), new IntWritable(2));
    }
}