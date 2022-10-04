package com.startuplab.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.startuplab.common.CValue;
import lombok.Data;

@Data
public class User implements Serializable {

  private Integer user_id;
  private Integer assignment_id;
  private String user_email;
  private String user_password;
  private String user_name;
  private String user_phone;
  private Integer user_type;
  private Integer user_status;
  private LocalDateTime create_time;
  private LocalDateTime update_time;

  private int row_count;
  private int page_no;
  private String row_start;

  private String user_type_name;
  private String user_status_name;

  public String getRole() {
    String role = "ROLE_USER";
    if (getUser_type() != null && getUser_type().equals(CValue.code_user_type_admin)) {
      role = "ROLE_ADMIN";
    }
    return role;
  }

}
