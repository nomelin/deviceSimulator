package top.nomelin.generate.impl;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import top.nomelin.generate.DataGenerator;
import top.nomelin.generate.GeneratorParam;

import java.util.Objects;
import java.util.Random;

public class RandomDataGenerator implements DataGenerator {
    @Getter
    private final String name = "RandomDataGenerator";
    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private final Random random = new Random();

    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private Gson gson;
    @Getter
    private final double min;
    @Getter
    private final double max;
    @Getter
    private final boolean isInteger;

    public RandomDataGenerator(double min, double max, boolean isInteger) {
        this.min = min;
        this.max = max;
        this.isInteger = isInteger;
    }

    public RandomDataGenerator(GeneratorParam param, Gson gson) {
        if (Objects.isNull(param) || param.getMin() == null || param.getMax() == null || param.getIsInteger() == null) {
            throw new IllegalArgumentException("[随机数],min,max,integer参数不能为空");
        }
        this.min = param.getMin();
        this.max = param.getMax();
        this.isInteger = param.getIsInteger();
        this.gson = gson;
    }

    @Override
    public Object generate() {
        double value = min + (max - min) * random.nextDouble();
        return isInteger ? (int) Math.round(value) : value;
    }

    @Override
    public String toString() {
        return "RandomDataGenerator{" +
                "name='" + name + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", isInteger=" + isInteger +
                '}';
    }
}

