package com.startuplab.vo;

import lombok.Data;

@Data
public class Fcm {
  private Integer fcm_id;
  private Integer user_id;
  private Integer app_type;
  private String fcm_token;
}
