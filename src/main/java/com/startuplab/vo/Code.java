package com.startuplab.vo;

import lombok.Data;

@Data
public class Code {

  private Integer cd_id;
  private String column_name;
  private String column_descript;
  private Integer code_id;
  private String code_name;
  private String code_descript;
  private Integer orderby;
  private Integer apply;
}
