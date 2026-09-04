package com.haijinhan.utils.base.result;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class DataRequest<T> {
    @ApiModelProperty(value = "请求参数", required = true)
    @NotNull
    @Valid
    private T param;
}
