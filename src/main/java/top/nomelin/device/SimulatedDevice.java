package top.nomelin.device;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class SimulatedDevice {
    private final String deviceId;
    private final List<Sensor> sensors;
    private final int bufferSize;
    private final String userId;

    private transient final Timer timer;
    private boolean isRunning;

    public SimulatedDevice(String deviceId, List<Sensor> sensors, int bufferSize, String userId) {
        this.deviceId = deviceId;
        this.sensors = sensors;
        this.bufferSize = bufferSize;
        this.userId = userId;
        this.isRunning = false;
        timer = new Timer();
        log.info("设备创建：{}", this);
    }

    private void startDataGeneration() {
        for (Sensor sensor : sensors) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Map<String, Object> data = sensor.generateData();
                    // 收集数据并发送
                }
            }, 0, sensor.getInterval());//间隔为毫秒
        }
    }

    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        startDataGeneration();
        log.info("设备 {} 已启动，用户 {}", deviceId, userId);
    }

    // 新增stop方法
    public void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        timer.cancel();
        timer.purge();
        log.info("设备 {} 已停止，用户 {}", deviceId, userId);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
