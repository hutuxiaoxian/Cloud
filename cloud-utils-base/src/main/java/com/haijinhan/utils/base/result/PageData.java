package com.haijinhan.utils.base.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageData {
    public static final Integer DEFAULT_PAGE_NUM = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 30;
    public static final Integer MAX_PAGE_SIZE = 5000;
    public static final String DESC = "desc";
    public static final String ASC = "asc";

    @ApiModelProperty("当前页，默认值为1")
    private Integer currPage;
    @ApiModelProperty("每页大小,默认值为30")
    private Integer pageSize;
    @ApiModelProperty("排序字段,可为空，默认按id（自增顺序）排序")
    private String sortBy;
    @ApiModelProperty("排序方式,升序asc,降序desc，可为空，默认升序")
    private String desc;

    public static final PageData NO_PAGING() {
        return new PageData(DEFAULT_PAGE_NUM, MAX_PAGE_SIZE);
    }

    public PageData() {
        this(DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
    }

    public PageData(Integer currPage, Integer pageSize) {
        this.currPage = currPage;
        this.pageSize = pageSize;
    }
}
