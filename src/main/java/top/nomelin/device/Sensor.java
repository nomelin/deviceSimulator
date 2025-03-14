package top.nomelin.device;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import top.nomelin.generate.DataGenerator;

import java.util.Collections;
import java.util.Map;

@Slf4j
public class Sensor {
    private final String sensorId;
    private int interval;//采集间隔，单位为毫秒
    private DataGenerator dataGenerator;

    public Sensor(String sensorId, DataGenerator dataGenerator, int interval) {
        this.sensorId = sensorId;
        this.dataGenerator = dataGenerator;
        this.interval = interval;
        log.info("传感器创建: " + this);
    }

    public Map<String, Object> generateData() {
        return Collections.singletonMap(sensorId, dataGenerator.generate());
    }

    public DataGenerator getDataGenerator() {
        return dataGenerator;
    }

    public void setDataGenerator(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public String getSensorId() {
        return sensorId;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
