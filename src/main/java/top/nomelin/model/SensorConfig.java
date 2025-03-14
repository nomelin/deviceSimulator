package top.nomelin.model;

import lombok.Data;
import top.nomelin.generate.DataGenerator;
import top.nomelin.generate.GeneratorParam;
import top.nomelin.generate.impl.RandomDataGenerator;
import top.nomelin.generate.impl.SineWaveGenerator;

@Data
public class SensorConfig {
    private String sensorId;
    private Double minValue;
    private Double maxValue;
    private Boolean isInteger;

    private Double frequency;
    private Double phase;
    private Double smallRandomDisturbance;
    private String generationType;

    public SensorConfig() {
    }

    public SensorConfig(String sensorId, double minValue, double maxValue, boolean isInteger, String generationType, int interval) {
        this.sensorId = sensorId;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.isInteger = isInteger;
        this.generationType = generationType;
    }

    public GeneratorParam toGeneratorParam() {
        GeneratorParam param = new GeneratorParam();
        param.setMin(minValue);
        param.setMax(maxValue);
        param.setIsInteger(isInteger);
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
        return new Sensor(sensorId, generator);
    }
}

