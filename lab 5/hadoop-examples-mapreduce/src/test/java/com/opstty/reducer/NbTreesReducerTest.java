package com.opstty.reducer;

import org.apache.hadoop.io.IntWritable;
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

@RunWith(MockitoJUnitRunner.class)
public class NbTreesReducerTest {
    @Mock
    private Reducer.Context context;
    private NbTreesReducer reducer;

    @Before
    public void setup() {
        this.reducer = new NbTreesReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        Text key = new Text("key");
        IntWritable value1 = new IntWritable(13);
        IntWritable value2 = new IntWritable(2);
        Iterable<IntWritable> values = Arrays.asList(value1, value2);

        this.reducer.reduce(key, values, this.context);
        verify(this.context).write(new Text("key"), new IntWritable(15));
    }
}