package com.opstty.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
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
public class HighestReducerTest {
    @Mock
    private Reducer.Context context;
    private HighestReducer reducer;

    @Before
    public void setup() {
        this.reducer = new HighestReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        Text key = new Text("pomifera");
        FloatWritable value = new FloatWritable(13);
        Iterable<FloatWritable> values = Arrays.asList(value);

        this.reducer.reduce(key, values, this.context);
        verify(this.context).write(new Text("pomifera"), new FloatWritable(13));
    }
}