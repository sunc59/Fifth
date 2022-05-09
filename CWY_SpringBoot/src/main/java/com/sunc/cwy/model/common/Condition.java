package com.sunc.cwy.model.common;

import lombok.Data;

@Data
public class Condition {

    private Object object;

    private PageInfo pageInfo = new PageInfo();
}
