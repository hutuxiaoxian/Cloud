package com.haijinhan.utils.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseActionEntity extends BaseEntity {

    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private String createUserId;
    @TableField(value = "create_user_name", fill = FieldFill.INSERT)
    private String createUserName;
    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;
    @TableField(value = "update_user_name", fill = FieldFill.INSERT_UPDATE)
    private String updateUserName;
}
