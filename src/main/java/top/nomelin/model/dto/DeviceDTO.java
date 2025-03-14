package top.nomelin.model.dto;

import lombok.Data;

@Data
public class DeviceDTO {
    private String deviceId;
    private String userId;
    private int bufferSize;
    private Integer interval;
}