package com.startuplab.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/view")
public class ViewController {
  @PostConstruct
  public void init() throws Exception {
    try {
    } catch (Exception e) {
      log.error("{}", e.getMessage());
    }
  }

  @RequestMapping(value = "/{page}")
  public String pageView2(HttpServletRequest request, @PathVariable String page) throws Exception {
    return "/webview/" + page;
  }

}
