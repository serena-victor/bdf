package com.opstty.mapper;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import com.opstty.OldestWritable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OldestMapperTest {
    @Mock
    private Mapper.Context context;
    private OldestMapper mapper;

    @Before
    public void setup() {
        this.mapper = new OldestMapper();
    }

    @Test
    public void testMap() throws IOException, InterruptedException {
        String value = "(48.857140829, 2.29533455314);7;Maclura;pomifera;Moraceae;1935;13.0;;Quai Branly, avenue de La Motte-Piquet, avenue de la Bourdonnais, avenue de Suffren;Oranger des Osages;;6;Parc du Champs de Mars";
        this.mapper.map(null, new Text(value), this.context);
        verify(this.context, times(1))
                .write(new LongWritable(1), new OldestWritable(new IntWritable(Integer.parseInt("7")),
                        new LongWritable(Long.parseLong("1935"))));
    }
}