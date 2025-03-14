package top.nomelin.gui;

import top.nomelin.device.Sensor;
import top.nomelin.generate.DataGenerator;
import top.nomelin.generate.GeneratorParam;
import top.nomelin.generate.impl.RandomDataGenerator;
import top.nomelin.generate.impl.SineWaveGenerator;

public class SensorConfig {
    private String sensorId;
    private Double minValue;
    private Double maxValue;
    private Boolean isInteger;

    private Double frequency;
    private Double phase;
    private Double smallRandomDisturbance;
    private String generationType;
    private int interval;

    public SensorConfig() {
    }

    public SensorConfig(String sensorId, double minValue, double maxValue, boolean isInteger, String generationType, int interval) {
        this.sensorId = sensorId;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.isInteger = isInteger;
        this.generationType = generationType;
        this.interval = interval;
    }

    public GeneratorParam toGeneratorParam() {
        GeneratorParam param = new GeneratorParam();
        param.setMin(minValue);
        param.setMax(maxValue);
        param.setInteger(isInteger);
        param.setFrequency(frequency);
        param.setPhase(phase);
        param.setSmallRandomDisturbance(smallRandomDisturbance);
        return param;
    }


    // toSensor() 方法用于转换为设备使用的Sensor对象
    public Sensor toSensor() {
        DataGenerator generator;
        GeneratorParam param = toGeneratorParam();
        generator = switch (generationType) {
            case "随机数" -> new RandomDataGenerator(param);
            case "正弦波(带噪声)" -> new SineWaveGenerator(param);
            default -> throw new IllegalArgumentException("不支持的生成类型: " + generationType);
        };
        return new Sensor(sensorId, generator, interval);
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isInteger() {
        return isInteger;
    }

    public String getGenerationType() {
        return generationType;
    }

    public void setGenerationType(String generationType) {
        this.generationType = generationType;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Boolean getInteger() {
        return isInteger;
    }

    public void setInteger(boolean integer) {
        isInteger = integer;
    }

    public void setInteger(Boolean integer) {
        isInteger = integer;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public Double getPhase() {
        return phase;
    }

    public void setPhase(Double phase) {
        this.phase = phase;
    }

    public Double getSmallRandomDisturbance() {
        return smallRandomDisturbance;
    }

    public void setSmallRandomDisturbance(Double smallRandomDisturbance) {
        this.smallRandomDisturbance = smallRandomDisturbance;
    }
}
