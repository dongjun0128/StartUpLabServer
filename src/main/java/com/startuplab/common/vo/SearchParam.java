package com.startuplab.common.vo;

import java.beans.PropertyEditor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import com.startuplab.common.CValue;
import com.startuplab.common.exception.MyException;
import com.startuplab.common.util.CUtil;
import com.startuplab.common.util.TypeConverter;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unchecked")
@Slf4j
public class SearchParam extends HashMap<String, Object> {

  public SearchParam(HashMap<String, Object> map) {
    log.debug("");
    map.forEach((key, value) -> {
      add(key, value);
    });
  }

  public SearchParam(Object obj) {
    Class c = obj.getClass();
    for (Field field : c.getDeclaredFields()) {
      try {
        field.setAccessible(true);
        String key = field.getName();
        Object value = field.get(obj);
        add(key, value);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public SearchParam() {}

  public SearchParam(String key, Object value) {
    if (key != null && value != null)
      add(key, value);
  }

  public void add(String key, Object value) {
    this.put(key, value);
  }

  public void setSearchParams() {
    if ((this.get("search_word") != null && !this.get("search_word").toString().equals(""))) {
      this.put(this.get("search_type").toString(), this.get("search_word"));
    }
    if ((this.get("search_word1") != null && !this.get("search_word1").toString().equals(""))) {
      this.put(this.get("search_type1").toString(), this.get("search_word1"));
    }
    if ((this.get("search_word2") != null && !this.get("search_word2").toString().equals(""))) {
      this.put(this.get("search_type2").toString(), this.get("search_word2"));
    }

  }

  public void setForAdminQuery() {

    this.add("for_admin_query", true);
  }

  public void setRowStart() {
    if (this.get("row_count") == null)
      add("row_count", CValue.default_row_count);
    if (this.get("page_no") == null)
      add("page_no", CValue.default_page_no);
    Integer row_start = (CUtil.objectToInteger(this.get("page_no")) - 1) * CUtil.objectToInteger(this.get("row_count"));
    add("row_start", row_start.toString());
    setSearchParams();
  }

  public void setLastIdPaging() {
    if (this.get("row_count") == null)
      add("row_count", CValue.default_row_count);
    if (this.get("last_id") == null)
      add("last_id", -1);
  }

  public void setXlsx() {
    this.add("row_count", 999999);
    this.add("page_no", 1);
    setRowStart();
  }

  public String getSearchType() {
    return (this.get("search_type") != null) ? this.get("search_type").toString().trim() : "";
  }

  public String getSearchWord() {
    return (this.get("search_word") != null && !this.get("search_word").toString().equals("")) ? this.get("search_word").toString() : "";
  }

  public Integer getInteger(String key) {
    Integer i = null;
    try {
      i = CUtil.objectToInteger(this.get(key));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return i;
  }

  public String getString(String key) {
    String s = null;
    try {
      s = this.get(key).toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return s;
  }

  public <T> T getObject(Class<T> classType) {
    try {
      Constructor<?> constructor = classType.getConstructor(null);
      Object obj = constructor.newInstance();

      Class c = obj.getClass();
      for (Field field : c.getDeclaredFields()) {
        try {
          field.setAccessible(true);
          String key = field.getName();
          if (this.containsKey(key)) {
            Object value = this.get(key);

            if (value == null)
              continue;

            if (field.getType() == value.getClass()) {
              field.set(obj, value);
              continue;
            }

            PropertyEditor propertyEditor = new TypeConverter().getEditor().get(field.getType());
            if (propertyEditor != null) {
              propertyEditor.setAsText(String.valueOf(value));
              field.set(obj, propertyEditor.getValue());
            }

          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return (T) obj;
    } catch (Exception e) {
      e.printStackTrace();

    }

    return null;
  }

  public static boolean checkRequiedParams(SearchParam serarchParam, boolean isRequiedValue, String... params) throws Exception {

    if (serarchParam == null)
      new MyException("NO_REQUIRED_PARAMS: null");

    for (String param : params) {
      if (!serarchParam.containsKey(param)) {
        throw new MyException("NO_REQUIRED_PARAMS: " + param);
      }

      if (isRequiedValue) {
        Object obj = serarchParam.get(param);
        if (obj == null) {
          throw new MyException("NO_REQUIRED_PARAMS: " + param);
        } else {
          if (obj instanceof String) {
            if (((String) obj).isEmpty()) {
              throw new MyException("NO_REQUIRED_PARAMS: " + param);
            }
          } else if (obj instanceof Integer) {

          }
        }
      }
    }
    return true;
  }

  public static boolean checkRequiedParams(SearchParam serarchParam, String... params) throws Exception {
    return SearchParam.checkRequiedParams(serarchParam, true, params);
  }
}
