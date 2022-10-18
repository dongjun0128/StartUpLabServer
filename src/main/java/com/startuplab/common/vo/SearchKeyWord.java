package com.startuplab.common.vo;

import lombok.Data;

@Data
public class SearchKeyWord {
    private String keyword;
    private String columnName;
    private String data_status;
    private String work_id;
    private String user_id;
}
