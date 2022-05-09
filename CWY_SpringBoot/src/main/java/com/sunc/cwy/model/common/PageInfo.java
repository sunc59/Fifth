package com.sunc.cwy.model.common;

import lombok.Data;

@Data
public class PageInfo {

    private int currentPage = 1;

    private int count = 10;
}
