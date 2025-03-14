package top.nomelin.generate;

import top.nomelin.gui.SensorConfig;

/**
 * 不同的生成器只会从中取出自己需要的参数，因此很多参数可能并不会被每个生成器使用到。
 * 为了方便实现，这个逻辑可读性不好。
 */
public class GeneratorParam {
    private Double min;//最小值
    private Double max;//最大值
    private Boolean isInteger;//true为整数，false为浮点数
    private Double frequency;//正弦波的频率
    private Double phase;//正弦波的相位
    private Double smallRandomDisturbance;//正弦波添加的随机扰动的幅度

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Boolean getInteger() {
        return isInteger;
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
