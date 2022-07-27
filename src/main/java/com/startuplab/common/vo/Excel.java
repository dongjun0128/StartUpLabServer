package com.startuplab.common.vo;

public class Excel {
  private Integer type; // 0 string, 1:integer
  private String value;

  public Excel() {
    type = 0;
  }

  public Excel(Integer value) {
    type = 1;
    this.value = value.toString();
  }

  public Excel(String value) {
    type = 0;
    this.value = value;
  }

  public Excel(String value, Integer type) {
    this.type = type;
    this.value = value;
  }

  public Integer getType() {
    if (this.value == null || this.value.equals(""))
      return 0;
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
