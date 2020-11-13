package com.opstty.mapper;

import org.apache.hadoop.io.IntWritable;
import com.opstty.MostWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MostMapperTest {
    @Mock
    private Mapper.Context context;
    private MostMapper mapper;

    @Before
    public void setup() {
        this.mapper = new MostMapper();
    }

    @Test
    public void testMap() throws IOException, InterruptedException {
        IntWritable key = new IntWritable(1);
        IntWritable district = new IntWritable(Integer.parseInt("7"));
        IntWritable nbTree = new IntWritable(Integer.parseInt("5"));
        MostWritable value = new MostWritable(district,nbTree);
        
        Text v = new Text("7\t5");


        this.mapper.map(key, v, this.context);
        verify(this.context, times(1))
                .write(key, value);
    }
}