package com.startuplab.common.vo;

import java.util.HashMap;
import com.startuplab.common.exception.MyException;
import lombok.Data;

@SuppressWarnings("unchecked")
@Data
public class ApiResult {
  private ErrorStatus error;
  private Object data;

  public ApiResult() {
    error = new ErrorStatus();
  }

  public void addData(String key, Object data) {
    if (this.data == null)
      this.data = new HashMap<>();
    ((HashMap<String, Object>) this.data).put(key, data);
  }

  public void setMyError(Exception e) {
    int code = MyException.systemErrorCode;
    String msg = MyException.systemErrorMsg;
    if (e instanceof MyException) {
      code = ((MyException) e).getCode();
      msg = ((MyException) e).getMsg();
    }
    this.getError().setCode(code);
    this.getError().setMsg(msg);
  }

  public void setMyError() {
    setMyError(null);
  }
}
