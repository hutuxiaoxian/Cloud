package com.zhishouwei.platform.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhishouwei.platform.cloud.auth.entity.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper extends BaseMapper<OauthClientDetails> {

    @Select("SELECT * FROM oauth_client_details WHERE client_id = #{clientId}")
    OauthClientDetails findByClientId(@Param("clientId") String clientId);

}
