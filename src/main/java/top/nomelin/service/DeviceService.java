package top.nomelin.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.nomelin.model.Sensor;
import top.nomelin.model.SensorConfig;
import top.nomelin.model.SimulatedDevice;
import top.nomelin.model.dto.DeviceDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class DeviceService {
    private final Map<String, SimulatedDevice> devices = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate;
    private final Gson gson;

    @Autowired
    public DeviceService(RestTemplate restTemplate, Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    public SimulatedDevice createDevice(DeviceDTO dto) {
        SimulatedDevice device = new SimulatedDevice(
                dto.getDeviceId(),
                new ArrayList<>(),
                dto.getBufferSize(),
                dto.getUserId(),
                dto.getInterval(),
                restTemplate,
                gson
        );
        devices.put(dto.getDeviceId(), device);
        log.info("Created device:{}", device);
        return device;
    }

    public Sensor addSensorToDevice(String deviceId, SensorConfig config) {
        SimulatedDevice device = getDevice(deviceId);
        device.stop();
        Sensor sensor = config.toSensor(gson);
        device.getSensors().add(sensor);
        log.info("Added sensor {} to device {}", sensor, device);
        return sensor;
    }

    public void removeSensorFromDevice(String deviceId, String sensorId) {
        SimulatedDevice device = getDevice(deviceId);
        device.stop();
        List<Sensor> sensors = device.getSensors();

        Sensor target = sensors.stream()
                .filter(s -> s.getSensorId().equals(sensorId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found"));

        sensors.remove(target);
        log.info("Removed sensor {} from device {}", target, device);
    }

    public SimulatedDevice updateDevice(String deviceId, DeviceDTO dto) {
        SimulatedDevice device = getDevice(deviceId);
        device.stop();
        if (dto.getBufferSize() > 0) {
            device.setBufferSize(dto.getBufferSize());
        }
        if (dto.getInterval() != null) {
            device.setInterval(dto.getInterval());
        }
        if (dto.getUserId() != null) {
            device.setUserId(dto.getUserId());
        }
        if(dto.getDeviceId() != null){
            device.setDeviceId(dto.getDeviceId());
            //更改存储的key值
            devices.remove(deviceId);
            devices.put(dto.getDeviceId(), device);
        }
        log.info("Updated device:{}", device);
        return device;
    }

    public Sensor updateSensor(String deviceId, String sensorId, SensorConfig config) {
        SimulatedDevice device = getDevice(deviceId);
        device.stop();
        List<Sensor> sensors = device.getSensors();

        Sensor target = sensors.stream()
                .filter(s -> s.getSensorId().equals(sensorId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found"));

        Sensor newSensor = config.toSensor(gson);
        sensors.set(sensors.indexOf(target), newSensor);
        log.info("Updated sensor {} of device {}", newSensor, device);
        return newSensor;
    }

    public void startDevice(String deviceId) {
        SimulatedDevice device = getDevice(deviceId);
        log.info("Starting device:{}", device);
        device.start();
    }

    public void stopDevice(String deviceId) {
        SimulatedDevice device = getDevice(deviceId);
        log.info("Stopping device:{}", device);
        device.stop();
    }

    public SimulatedDevice getDevice(String deviceId) {
        SimulatedDevice device = devices.get(deviceId);
        if (device == null) {
            throw new IllegalArgumentException("Device not found");
        }
        return device;
    }

    public void deleteDevice(String deviceId) {
        SimulatedDevice device = getDevice(deviceId);
        device.stop();
        devices.remove(deviceId);
        log.info("Deleting device:{}", device);
    }

    public List<SimulatedDevice> getAllDevices() {
        return new ArrayList<>(devices.values());
    }

    public List<Sensor> getSensors(String deviceId) {
        return getDevice(deviceId).getSensors();
    }
}
