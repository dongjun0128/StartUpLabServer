package com.startuplab.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.startuplab.common.vo.Excel;

public class MsExcel {
  public File makeXlsx(String fileName, List<List<Excel>> rows) {
    return makeXlsx(fileName, rows, null);
  }

  public File makeXlsx(String fileName, List<List<Excel>> rows, List<CellRangeAddress> mergeList) {
    // Workbook 생성
    // Workbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전
    Workbook xlsx = new XSSFWorkbook(); // Excel 2007 이상
    // *** Sheet-------------------------------------------------
    // Sheet 생성
    Sheet sheet = xlsx.createSheet(fileName);
    // 컬럼 너비 설정
    // sheet1.setColumnWidth(0, 10000);
    // sheet1.setColumnWidth(9, 10000);
    // ----------------------------------------------------------
    // *** Style--------------------------------------------------
    // Cell 스타일 생성
    DataFormat fmt = xlsx.createDataFormat();
    CellStyle stringStyle = xlsx.createCellStyle();
    stringStyle.setDataFormat(fmt.getFormat("@"));
    CellStyle intStyle = xlsx.createCellStyle();
    intStyle.setDataFormat(fmt.getFormat("#,##0"));
    // 줄 바꿈
    // cellStyle.setWrapText(true);
    // Cell 색깔, 무늬 채우기
    // cellStyle.setFillForegroundColor(XSSFColor..LIME.index);
    // cellStyle.setFillPattern(CellStyle.BIG_SPOTS);
    // ----------------------------------------------------------
    for (int row_counter = 0; row_counter < rows.size(); row_counter++) {
      Row row = sheet.createRow(row_counter);
      List<Excel> cells = rows.get(row_counter);
      for (int cell_counter = 0; cell_counter < cells.size(); cell_counter++) {
        Excel data = cells.get(cell_counter);
        Cell cell = row.createCell(cell_counter);
        if (data.getType().equals(1)) {
          double d = Double.parseDouble(data.getValue());
          cell.setCellValue(d);
          cell.setCellStyle(intStyle);
        } else {
          cell.setCellValue(data.getValue());
          cell.setCellStyle(stringStyle);
        }
        // if (data.getIsString().equals(1))
      }
    }
    if (mergeList != null && !mergeList.isEmpty()) {
      for (CellRangeAddress region : mergeList) {
        sheet.addMergedRegion(region);
      }
    }
    List<Excel> cells = rows.get(0);
    // logger.info("{}", cells.size());
    for (int i = 0; i < cells.size(); i++) // autuSizeColumn after setColumnWidth setting!!
    {
      // logger.info("width:{},{}", i, sheet.getColumnWidth(i));
      sheet.autoSizeColumn(i);
      // logger.info("auto width:{},{}", i, sheet.getColumnWidth(i));
      sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 1024); // 이건 자동으로 조절 하면 너무 딱딱해 보여서 자동조정한 사이즈에 (short)512를 추가해
                                                                 // 주니 한결 보기 나아졌다.
                                                                 // logger.info("add width:{},{}", i,
                                                                 // sheet.getColumnWidth(i));
    }
    // excel 파일 저장
    try {
      File xlsFile = new File(fileName + ".xlsx");
      FileOutputStream fileOut = new FileOutputStream(xlsFile);
      xlsx.write(fileOut);
      xlsx.close();
      return xlsFile;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
