package top.nomelin.generate.impl;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import top.nomelin.generate.DataGenerator;
import top.nomelin.generate.GeneratorParam;

import java.util.Objects;
import java.util.Random;

public class SineWaveGenerator implements DataGenerator {
    @Getter
    private final String name = "SineWaveGenerator";
    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private final Random random = new Random();
    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private Gson gson;
    @Getter
    private final double amplitude;//振幅
    @Getter
    private final double frequency;//频率
    @Getter
    private final double phase;//相位
    @Getter
    private final double offset;//偏移量

    //小幅度随机扰动的最大幅度，会为生成的数值增加一个随机扰动r属于[-smallRandomDisturbance, smallRandomDisturbance]
    @Getter
    private final double smallRandomDisturbance;
    @Getter
    private final long startTime = System.currentTimeMillis();

    public SineWaveGenerator(double amplitude, double frequency, double phase, double offset, double smallRandomDisturbance) {
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.phase = phase;
        this.offset = offset;
        this.smallRandomDisturbance = smallRandomDisturbance;
    }

    public SineWaveGenerator(GeneratorParam param,Gson gson) {
        if (param.getMax() < param.getMin()) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        if(Objects.isNull(param.getFrequency())||Objects.isNull(param.getPhase())||Objects.isNull(param.getSmallRandomDisturbance())){
            throw new IllegalArgumentException("[正弦波]频率、相位、小幅度随机扰动不能为null");
        }
        this.amplitude = (param.getMax() - param.getMin()) / 2;
        this.frequency = param.getFrequency();
        this.phase = param.getPhase();
        this.offset = (param.getMax() + param.getMin()) / 2;
        this.smallRandomDisturbance = param.getSmallRandomDisturbance();
        this.gson = gson;
    }

    @Override
    public Object generate() {
        double randomDisturbance = random.nextDouble() * 2 * smallRandomDisturbance - smallRandomDisturbance;
        double t = (System.currentTimeMillis() - startTime) / 1000.0;
        return amplitude * Math.sin(2 * Math.PI * frequency * t + phase) + offset + randomDisturbance;
    }

    @Override
    public String toString() {
        return "SineWaveGenerator{" +
                "name='" + name + '\'' +
                ", amplitude=" + amplitude +
                ", frequency=" + frequency +
                ", phase=" + phase +
                ", offset=" + offset +
                ", smallRandomDisturbance=" + smallRandomDisturbance +
                ", startTime=" + startTime +
                '}';
    }
}
