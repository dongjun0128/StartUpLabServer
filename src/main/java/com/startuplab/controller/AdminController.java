package com.startuplab.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.startuplab.common.CValue;
import com.startuplab.common.exception.MyException;
import com.startuplab.common.exception.MyException.MyError;
import com.startuplab.common.vo.ApiResult;
import com.startuplab.config.oauth2.CurrentUser;
import com.startuplab.vo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

  private Gson gson;

  @PostConstruct
  public void init() {
    try {
      gson = CValue.getGson();
    } catch (Exception e) {
      log.error("{}", e.getMessage());
    }
  }

  @RequestMapping(value = "/")
  public String login(Model model, HttpSession session) {
    log.info("admin index!");
    return "index";
  }

  @RequestMapping(value = "/token/check")
  @ResponseBody
  public ApiResult checkToken(HttpServletRequest request, @CurrentUser User currentUser) throws Exception {
    ApiResult result = new ApiResult();
    try {
      Integer admin_id = (currentUser != null) ? currentUser.getUser_id() : null;
      log.debug("admin_id:{}", admin_id);
      result.addData("user", currentUser);
      throw new MyException(MyError.SUCCESS);
    } catch (MyException e) {
      result.setMyError(e);
    } catch (Exception e) {
      result.setMyError();
      log.error("Exception:{}", e.getMessage());
    }
    return result;
  }

}
