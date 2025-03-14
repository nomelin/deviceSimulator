package top.nomelin.generate.impl;


import com.google.gson.Gson;
import top.nomelin.generate.DataGenerator;
import top.nomelin.generate.GeneratorParam;

import java.util.Objects;
import java.util.Random;

public class RandomDataGenerator implements DataGenerator {
    private final String name = "RandomDataGenerator";
    private transient final Random random = new Random();
    private final double min;
    private final double max;
    private final boolean isInteger;

    public RandomDataGenerator(double min, double max, boolean isInteger) {
        this.min = min;
        this.max = max;
        this.isInteger = isInteger;
    }

    public RandomDataGenerator(GeneratorParam param) {
        if (Objects.isNull(param) || param.getMin() == null || param.getMax() == null || param.getInteger() == null) {
            throw new IllegalArgumentException("[随机数],min,max,integer参数不能为空");
        }
        this.min = param.getMin();
        this.max = param.getMax();
        this.isInteger = param.getInteger();
    }

    @Override
    public Object generate() {
        double value = min + (max - min) * random.nextDouble();
        return isInteger ? (int) Math.round(value) : value;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

