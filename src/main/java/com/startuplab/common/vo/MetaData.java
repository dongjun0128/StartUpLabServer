package com.startuplab.common.vo;

import lombok.Data;

@Data
public class MetaData {
    private int work_id;
    private int meta_id;
    private String meta_key;
    private String meta_name;
    private String meta_type;
    private String meta_constraints;
    private int meta_orderby;

    private String meta_type_name;
}
