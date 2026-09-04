package com.haijinhan.utils.database;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends BaseEntity {
    private String userUuid;
    private String username;
    private String password;
    private String mobile;
    private String eMail;
}
