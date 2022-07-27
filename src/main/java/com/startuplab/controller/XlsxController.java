package com.startuplab.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.startuplab.common.CValue;
import com.startuplab.common.vo.SearchParam;
import com.startuplab.service.XlsxService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/xlsx")
public class XlsxController {

  @Autowired
  private XlsxService xlsx;

  private Gson gson;

  @PostConstruct
  public void init() {
    try {
      gson = CValue.getGson();
    } catch (Exception e) {
      log.error("{}", e.getMessage());
    }
  }

  @RequestMapping(value = "/item/list")
  public ModelAndView getItemListXlsx(HttpServletRequest request, @RequestParam SearchParam param) throws Exception {

    try {
      param.setXlsx();

      // String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
      // File file = xlsx.getItemList(list, "item_list_" + now);
      // return new ModelAndView(new FileDownloadView(), "downloadFile", file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
