package top.nomelin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.nomelin.model.Result;
import top.nomelin.model.SensorConfig;
import top.nomelin.model.dto.DeviceDTO;
import top.nomelin.service.DeviceService;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public Result createDevice(@RequestBody DeviceDTO deviceDTO) {
        return Result.success(deviceService.createDevice(deviceDTO));
    }

    @PostMapping("/{deviceId}/sensors")
    public Result addSensor(@PathVariable String deviceId, @RequestBody SensorConfig sensorConfig) {
        return Result.success(deviceService.addSensorToDevice(deviceId, sensorConfig));
    }

    @GetMapping
    public Result getAllDevices() {
        return Result.success(deviceService.getAllDevices());
    }

    @GetMapping("/{deviceId}")
    public Result getDevice(@PathVariable String deviceId) {
        return Result.success(deviceService.getDevice(deviceId));
    }

    @GetMapping("/{deviceId}/sensors")
    public Result getSensors(@PathVariable String deviceId) {
        return Result.success(deviceService.getSensors(deviceId));
    }

    @PutMapping("/{deviceId}")
    public Result updateDevice(@PathVariable String deviceId, @RequestBody DeviceDTO deviceDTO) {
        return Result.success(deviceService.updateDevice(deviceId, deviceDTO));
    }

    @PutMapping("/{deviceId}/sensors/{sensorId}")
    public Result updateSensor(@PathVariable String deviceId, @PathVariable String sensorId,
                               @RequestBody SensorConfig sensorConfig) {
        return Result.success(deviceService.updateSensor(deviceId, sensorId, sensorConfig));
    }

    @PostMapping("/{deviceId}/start")
    public Result startDevice(@PathVariable String deviceId) {
        deviceService.startDevice(deviceId);
        return Result.success();
    }

    @PostMapping("/{deviceId}/stop")
    public Result stopDevice(@PathVariable String deviceId) {
        deviceService.stopDevice(deviceId);
        return Result.success();
    }
}