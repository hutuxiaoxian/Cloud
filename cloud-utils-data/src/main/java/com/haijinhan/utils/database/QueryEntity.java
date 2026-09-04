package com.haijinhan.utils.database;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryEntity extends BaseEntity {
    @TableField(exist = false)
    private String splitTableId;
    @TableField(exist = false)
    private String stime;
    @TableField(exist = false)
    private String etime;
}
