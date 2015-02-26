package Features;

import org.elasticsearch.index.mapper.Mapper;

import java.io.IOException;

/**
 * Created by PC-salon on 24/02/2015.
 */
public class MyMapper extends MapReduceBase implements Mapper {
    @Override
    public void map(Object key, Object value, OutputCollector output,
                    Reporter reporter) throws IOException {
        // create the MapWritable object
        MapWritable doc = new MapWritable();

        // write the result to the output collector
        // one can pass whatever value to the key; EsOutputFormat ignores it
        output.collect(NullWritable.get(), map);
    }
}