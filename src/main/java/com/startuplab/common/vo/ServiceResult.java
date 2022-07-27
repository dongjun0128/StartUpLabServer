package com.startuplab.common.vo;

import java.util.HashMap;
import com.startuplab.common.exception.MyException;
import com.startuplab.common.exception.MyException.MyError;
import lombok.Data;

@SuppressWarnings("unchecked")
@Data
public class ServiceResult {
  private MyException myException;
  private Object data;

  public ServiceResult() {
    this.myException = new MyException(MyError.SYSTEM_EXCEPTION);
  }

  public void addData(String key, Object data) {
    if (this.data == null)
      this.data = new HashMap<>();
    ((HashMap<String, Object>) this.data).put(key, data);
  }

  public void addPagingData(Integer total_count, Object list) {
    addData("total_count", total_count);
    addData("list", list);
  }

  public Object getParam(String key) {
    if (this.data != null) {
      return ((HashMap<String, Object>) this.data).get(key);
    }
    return null;
  }
}
