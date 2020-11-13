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
public class DistrictsReducerTest {
    @Mock
    private Reducer.Context context;
    private DistrictsReducer reducer;

    @Before
    public void setup() {
        this.reducer = new DistrictsReducer();
    }

    @Test
    public void testReduce() throws IOException, InterruptedException {
        Text district = new Text("2");
        NullWritable nul = NullWritable.get();
        Iterable<NullWritable> values = Arrays.asList(nul);

        this.reducer.reduce(district, values, this.context);
        verify(this.context).write(new Text("2"), NullWritable.get());
    }
}