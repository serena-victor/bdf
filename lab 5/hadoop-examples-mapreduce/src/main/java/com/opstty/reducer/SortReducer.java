package com.opstty.reducer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SortReducer extends Reducer<LongWritable, Text, Text, FloatWritable> {

    public void reduce(LongWritable key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        HashMap<String, Float> hmap = new HashMap<>();

        for (Text val : values) {
            String line[] = val.toString().split(";");
            String id = line[line.length - 1];
            try {
                float height = Float.parseFloat(line[0]);
                hmap.put(id, height);

            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }
        }

        Map<String, Float> sorted = sortByValues(hmap);

        for (String k : sorted.keySet()) {
            Text keyout = new Text(k);
            FloatWritable valueout = new FloatWritable(sorted.get(k));
            context.write(keyout, valueout);
        }

    }

    public static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

}
