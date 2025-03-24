package top.nomelin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Data
public class SimulatedDevice {
    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private transient final RestTemplate restTemplate;
    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private transient final Gson gson;
    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private transient Timer timer;
    private String deviceId;
    private List<Sensor> sensors;
    private int bufferSize;
    private String userId;
    private Integer interval;
    @JsonProperty("isRunning")
    private boolean isRunning;
    private List<Map<String, Object>> dataBuffer = new ArrayList<>();

    public SimulatedDevice(String deviceId, List<Sensor> sensors, int bufferSize,
                           String userId, Integer interval, RestTemplate restTemplate, Gson gson) {
        this.deviceId = deviceId;
        this.sensors = sensors;
        this.bufferSize = bufferSize;
        this.userId = userId;
        this.interval = interval == null ? 1000 : interval;
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    private void startDataGeneration() {
        timer = new Timer(); // 创建新的定时器
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isRunning) {
                    generateData();
                }
            }
        }, 0, interval);
    }

    private void generateData() {
        long timestamp = System.currentTimeMillis();
        Map<String, Object> dataPoint = new HashMap<>();
        Map<String, Object> sensorValues = new HashMap<>();

        for (Sensor sensor : sensors) {
            sensorValues.putAll(sensor.generateData());
        }

        dataPoint.put(String.valueOf(timestamp), List.of(sensorValues));
        //例如：{1742020370260=[{d1=66.69847904026506, d2=99.00262427164728}]}
//        log.info("Generated data for device {}: {}", deviceId, dataPoint);
        dataBuffer.add(dataPoint);

        if (dataBuffer.size() >= bufferSize) {
            sendData();
//            log.info("Data buffer for device {} is full to capacity: {}, sending data", deviceId, bufferSize);
        }
    }

    private void sendData() {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("userId", userId);
            payload.put("id", deviceId);

            Map<String, Object> data = new HashMap<>();
            for (Map<String, Object> point : dataBuffer) {
                data.putAll(point);
            }
            payload.put("data", data);
            log.info("Sending data for device {}: {}", deviceId, payload);
            ResponseEntity<String> response =
                    restTemplate.postForEntity("http://localhost:34567/connect/uploadData", payload, String.class);
            log.info("Response from server: {}", response.getBody());
            dataBuffer.clear();
        } catch (Exception e) {
            log.error("Failed to send data for device {}: {}", deviceId, e.getMessage());
        }
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            startDataGeneration();
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            if (timer != null) {
                timer.cancel();
            }
        }
    }


    @Override
    public String toString() {
        return "SimulatedDevice{" +
                "deviceId='" + deviceId + '\'' +
                ", sensors=" + sensors +
                ", bufferSize=" + bufferSize +
                ", userId='" + userId + '\'' +
                ", interval=" + interval +
                ", isRunning=" + isRunning +
                ", dataBuffer=" + dataBuffer +
                '}';
    }
}