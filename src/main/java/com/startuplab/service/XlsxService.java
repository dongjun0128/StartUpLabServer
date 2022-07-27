package com.startuplab.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.startuplab.common.CValue;
import com.startuplab.common.util.MsExcel;
import com.startuplab.common.vo.Excel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({"unchecked", "unused", "rawtypes"})
@Service
public class XlsxService {
  private Gson gson;
  private MsExcel msExcel;

  @PostConstruct
  public void ServiceInit() {
    try {
      msExcel = new MsExcel();
      gson = CValue.getGson();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  // 행열 변환
  public List transformData(List<List<Excel>> rows) {
    List newRows = new ArrayList<>();
    try {
      int maxColumn = rows.size();
      int maxRow = rows.get(0).size();
      // log.info("max Row:{}, max:colum:{}", maxRow, maxColumn);
      for (int row = 0; row < maxRow; row++) {
        List<Excel> data = new ArrayList<>();
        for (int column = 0; column < maxColumn; column++) {
          Excel cell = new Excel("");
          try {
            cell = rows.get(column).get(row);
          } catch (Exception e) {
          }
          data.add(cell);
        }
        newRows.add(data);
      }
      // log.info("newRows:{}", newRows);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return newRows;
  }
}
