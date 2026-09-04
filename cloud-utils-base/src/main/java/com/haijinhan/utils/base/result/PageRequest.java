package com.haijinhan.utils.base.result;

import lombok.Data;

@Data
public class PageRequest<T> {
    private PageData page;
    private T param;
}
