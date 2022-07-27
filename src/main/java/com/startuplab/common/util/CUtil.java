package com.startuplab.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.tika.Tika;

public class CUtil {
  private static Integer addKey = 0;

  public static String getUuid() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  public static String getUniqId() {
    String key = String.format("%04d", addKey);
    long millis = System.currentTimeMillis();
    addKey++;
    if (addKey >= 10000)
      addKey = 0;
    return Long.toString(millis) + key;
  }

  public static String getBody(HttpServletRequest request) throws IOException {
    String body = null;
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;
    try {
      InputStream inputStream = request.getInputStream();
      if (inputStream != null) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] charBuffer = new char[128];
        int bytesRead = -1;
        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
          stringBuilder.append(charBuffer, 0, bytesRead);
        }
      }
    } catch (IOException ex) {
      throw ex;
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException ex) {
          throw ex;
        }
      }
    }
    body = stringBuilder.toString();
    return body;
  }

  public static Integer objectToInteger(Object d) {
    Integer result = null;
    if (d == null)
      return null;
    try {
      if (d instanceof Double) {
        result = ((Double) d).intValue();
      } else if (d instanceof Long) {
        result = ((Long) d).intValue();
      } else if (d instanceof String) {
        Double str = Double.parseDouble((String) d);
        result = objectToInteger(str);
      } else if (d instanceof Integer) {
        result = (Integer) d;
      } else {
        result = objectToInteger(d.toString());
      }
    } catch (Exception e) {
    }
    return result;
  }

  public static String getRandomCode(int len) {
    String[] chars = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    String str = "";
    for (int i = 0; i < len; i++) {
      Random rnd = new Random();
      int ii = rnd.nextInt(60);
      str += chars[ii];
    }
    return str;
  }

  public static String getRandomNo(int len) {
    String no = "";
    for (int i = 0; i < len; i++) {
      Random rnd = new Random();
      Integer ii = rnd.nextInt(10);
      no += ii.toString();
    }
    return no;
  }

  public static int getRandomRange(int n1, int n2) {
    return (int) (Math.random() * (n2 - n1 + 1)) + n1;
  }

  public static String getFileExtension(String fileName) {
    return getFileExtension(fileName, "");
  }

  public static String getFileExtension(String fileName, String defaultValue) {
    String fileExtension = defaultValue;
    try {
      if (fileName.lastIndexOf(".") > 0) {
        fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileExtension;
  }

  public static Map<String, String> getQueryMap(String query) {
    if (query == null || query.equals(""))
      return new HashMap<>();
    String[] params = query.split("&");
    Map<String, String> map = new HashMap<String, String>();
    for (String param : params) {
      String name = param.split("=")[0];
      String value = param.split("=")[1];
      map.put(name, value);
    }
    return map;
  }

  public static Map<String, String> getQueryString(String urlString) {
    Map<String, String> queryStringMap = new HashMap<>();
    try {
      URL url = new URL(urlString);
      queryStringMap = getQueryMap(url.getQuery());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return queryStringMap;
  }

  public static boolean isEmptyString(Object str) {
    if (str == null)
      return true;
    String string = str.toString();
    if (string.equals(""))
      return true;
    return false;
  }

  public static String addComma(Object o) {
    Integer i = objectToInteger(o);
    try {
      return String.format("%,d", i);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String delComma(String s) {
    String result = s;
    try {
      result = s.replaceAll("\\,", "");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String onlyNumber(String s) {
    String result = s;
    try {
      result = s.replaceAll("[^\\d.]", "");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public static boolean isMobile(String userAgent) {
    boolean ismobile = false;
    String[] mobile = {"iPhone", "iPod", "Android"};
    for (int i = 0; i < mobile.length; i++) {
      if (userAgent.indexOf(mobile[i]) > -1) {
        ismobile = true;
        break;
      }
    }
    return ismobile;
  }

  public static boolean isKorean(char word) {
    boolean isKorean = true;
    // 한글의 제일 처음과 끝의 범위밖일 경우
    if (word < 0xAC00 || word > 0xD7A3) {
      isKorean = false;
    }
    return isKorean;
  }

  public static int getKoreanCount(String text) {
    int cnt = 0;
    if (!isEmptyString(text)) {
      for (int i = 0; i < text.length(); i++) {
        if (isKorean(text.charAt(i)))
          cnt++;
      }
    }
    return cnt;
  }

  public static List<Integer> getRandomList(int max) {
    List<Integer> nums = new ArrayList<>();
    for (int i = 0; i < max; i++) {
      nums.add(i);
    }
    Collections.shuffle(nums);
    return nums;
  }

  public static String getFileMineType(File f) {
    String mimeType = "";
    try {
      Tika tika = new Tika();
      mimeType = tika.detect(f);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mimeType;
  }
}
