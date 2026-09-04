package com.haijinhan.utils.base.result;

import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;

public class PageResult<T> {
    @ApiModelProperty("数据内容")
    private Collection<T> items;
    @ApiModelProperty("当前数据总数量,数据总量/每页大小=总页数；count/pageSize=MaxPages")
    private Integer count;
    @ApiModelProperty("当前页，默认值为1")
    private Integer currPage;
    @ApiModelProperty("每页大小,默认值为30")
    private Integer pageSize;
}
