package top.nomelin.model;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Data
public class SimulatedDevice {
    private transient final Timer timer;
    private final RestTemplate restTemplate;
    private final Gson gson;

    private String deviceId;
    private List<Sensor> sensors;
    private int bufferSize;
    private String userId;
    private Integer interval;
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
        this.timer = new Timer();
    }

    private void startDataGeneration() {
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

        dataPoint.put(String.valueOf(timestamp), sensorValues);
        log.info("Generated data for device {}: {}", deviceId, dataPoint);
        dataBuffer.add(dataPoint);

        if (dataBuffer.size() >= bufferSize) {
            sendData();
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

            restTemplate.postForEntity("http://localhost:12345/uploadData", payload, String.class);
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
            timer.cancel();
        }
    }

}