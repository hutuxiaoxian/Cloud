package com.haijinhan.utils.base.result;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseData {
    private String uuid;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
