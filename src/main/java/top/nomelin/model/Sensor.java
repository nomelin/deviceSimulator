package top.nomelin.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
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

    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private Gson gson;

    public Sensor(String sensorId, DataGenerator dataGenerator, Gson gson) {
        this.sensorId = sensorId;
        this.dataGenerator = dataGenerator;
        this.gson = gson;
        log.info("传感器创建: " + this);
    }

    public Map<String, Object> generateData() {
        return Collections.singletonMap(sensorId, dataGenerator.generate());
    }


    @Override
    public String toString() {
        return "Sensor{" +
                "sensorId='" + sensorId + '\'' +
                ", dataGenerator=" + dataGenerator +
                '}';
    }
}
