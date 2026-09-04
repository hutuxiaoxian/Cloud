package com.haijinhan.utils.database;

import com.baomidou.mybatisplus.annotation.*;
import com.haijinhan.utils.EmptyUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic() // 逻辑删除标志
    private Integer isDelete;

    public LocalDateTime getCreateTime() {
        return EmptyUtils.isNotEmpty(createTime) ? createTime: LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Shanghai"));
    }

    public LocalDateTime getUpdateTime() {
        return EmptyUtils.isNotEmpty(createTime) ? createTime: LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Shanghai"));
    }
}
