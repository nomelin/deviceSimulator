package top.nomelin.generate;

import lombok.Data;

/**
 * 不同的生成器只会从中取出自己需要的参数，因此很多参数可能并不会被每个生成器使用到。
 * 为了方便实现，这个逻辑可读性不好。
 */
@Data
public class GeneratorParam {
    private Double min;//最小值
    private Double max;//最大值
    private Boolean isInteger;//true为整数，false为浮点数
    private Double frequency;//正弦波的频率
    private Double phase;//正弦波的相位
    private Double smallRandomDisturbance;//正弦波添加的随机扰动的幅度
}
