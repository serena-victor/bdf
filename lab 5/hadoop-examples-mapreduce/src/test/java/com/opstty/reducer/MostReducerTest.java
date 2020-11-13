package com.opstty.reducer;

import com.opstty.MostWritable;
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
public class MostReducerTest {
    @Mock
    private Reducer.Context context;
    private MostReducer reducer;

    @Before
    public void setup() {
        this.reducer = new MostReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        IntWritable key = new IntWritable(1);
        MostWritable value1 = new MostWritable(new IntWritable(2), new IntWritable(3));
        MostWritable value2 = new MostWritable(new IntWritable(4), new IntWritable(5));
        Iterable<MostWritable> values = Arrays.asList(value1, value2);

        this.reducer.reduce(key, values, this.context);
        verify(this.context).write(new Text("district containing the most trees: "), new IntWritable(4));
    }
}