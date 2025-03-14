package top.nomelin.model;


import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.nomelin.generate.DataGenerator;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Data
public class Sensor {
    private final String sensorId;
    private DataGenerator dataGenerator;

    public Sensor(String sensorId, DataGenerator dataGenerator) {
        this.sensorId = sensorId;
        this.dataGenerator = dataGenerator;
        log.info("传感器创建: " + this);
    }

    public Map<String, Object> generateData() {
        return Collections.singletonMap(sensorId, dataGenerator.generate());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
