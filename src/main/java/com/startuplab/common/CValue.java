package com.startuplab.common;

import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.startuplab.common.util.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CValue {
  @PostConstruct
  public void init() {
    try {
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  // public static final String SERVER_TIME_ZONE = "UTC";
  public static final String SERVER_TIME_ZONE = "Asia/Seoul";

  public static Gson getGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
    gsonBuilder.setDateFormat("yyyyMMddHHmmss");
    gsonBuilder.setPrettyPrinting();
    // gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, Object>>() {
    // }.getType(), new MapDeserializerDoubleAsIntFix());
    Gson gson = gsonBuilder.create();
    return gson;
  }

  public static final String newLine = "\n";
  public static final String middleDot = "·";
  // 라이브 , 테스트 여부

  @Value("${spring.profiles.active}")
  public static String profile;

  public static final String reg_Number = "[0-9]*";
  public static final String reg_5word = "\\S{5,}";
  public static final String reg_Email = "[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{1,5}";
  public static final String reg_Date = "\\d{4}\\-\\d{2}\\-\\d{2}";
  public static final String reg_Password = "\\S{6,}";
  public static final String reg_UserId = "\\S{4,}";
  public static final String reg_oreProductName = "*\\&*";

  public static final int default_row_count = 100;
  public static final int default_page_no = 1;

  public static final int code_user_type_admin = 1;
  public static final int code_user_type_user = 2;

}
