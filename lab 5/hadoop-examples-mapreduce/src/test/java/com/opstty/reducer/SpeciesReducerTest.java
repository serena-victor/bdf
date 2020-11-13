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
public class SpeciesReducerTest {
    @Mock
    private Reducer.Context context;
    private SpeciesReducer reducer;

    @Before
    public void setup() {
        this.reducer = new SpeciesReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        Text key = new Text("key");
        NullWritable value = NullWritable.get();
        Iterable<NullWritable> values = Arrays.asList(value);

        this.reducer.reduce(key, values, this.context);
        verify(this.context).write(new Text("key"), NullWritable.get());
    }
}