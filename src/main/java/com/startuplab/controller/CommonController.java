package com.startuplab.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;
import com.startuplab.common.CValue;
import com.startuplab.common.exception.MyException;
import com.startuplab.common.exception.MyException.MyError;
import com.startuplab.common.util.CUtil;
import com.startuplab.common.vo.ApiResult;
import com.startuplab.common.vo.SearchParam;
import com.startuplab.common.vo.ServiceResult;
import com.startuplab.config.oauth2.CurrentUser;
import com.startuplab.service.ApiService;
import com.startuplab.service.CommonService;
import com.startuplab.vo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/common")
public class CommonController {

  @Autowired
  private CommonService common;

  @Autowired
  private ApiService service;

  private Gson gson;

  @PostConstruct
  public void init() {
    try {
      gson = CValue.getGson();
    } catch (Exception e) {
      log.error("{}", e.getMessage());
    }
  }

  @RequestMapping(value = "/memory/status")
  @ResponseBody
  public ApiResult memoryStatus(HttpServletRequest request) throws Exception {
    ApiResult result = new ApiResult();
    try {
      Long heapSize = Runtime.getRuntime().totalMemory();
      Long heapMaxSize = Runtime.getRuntime().maxMemory();
      Long heapFreeSize = Runtime.getRuntime().freeMemory();
      Map<String, Object> map = new HashMap<>();
      map.put("heapSize", heapSize / 1024L / 1024L);
      map.put("heapMaxSize", heapMaxSize / 1024L / 1024L);
      map.put("heapFreeSize", heapFreeSize / 1024L / 1024L);
      result.setData(map);
    } catch (Exception e) {
      result.setMyError();
      e.printStackTrace();
    }
    return result;
  }

  @RequestMapping(value = "/token/info")
  @ResponseBody
  public ApiResult getTokenInfo(HttpServletRequest request, @CurrentUser User currentUser) throws Exception {
    ApiResult result = new ApiResult();
    try {
      Integer user_id = (currentUser != null) ? currentUser.getUser_id() : null;
      log.debug("user_id:{}", user_id);
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

  @RequestMapping(value = "/code/list")
  @ResponseBody
  public ApiResult getCodeList(HttpServletRequest request, @RequestBody(required = false) SearchParam param) throws Exception {
    ApiResult result = new ApiResult();
    try {

      log.info("param:{}", param);

      ServiceResult sr = service.getCodeList(param);
      if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
        result.addData("code_list", sr.getData());
      }
      result.setMyError(sr.getMyException());
      // } catch (MyException e) {
      // result.setMyError(e);
    } catch (Exception e) {
      result.setMyError();
      e.printStackTrace();
    }
    return result;
  }

  @RequestMapping(value = "/user/join")
  @ResponseBody
  public ApiResult joinUser(HttpServletRequest request, @RequestBody(required = false) User param, @CurrentUser User currentUser) throws Exception {
    ApiResult result = new ApiResult();
    try {
      // token 사용시 token의 유저정보
      Integer current_user_id = (currentUser != null) ? currentUser.getUser_id() : null;

      if (param == null) {
        throw new MyException("요청 내용이 없습니다.");
      }
      // 회원가입 필수 값이 user_email, user_password
      if (CUtil.isEmptyString(param.getUser_email()) || CUtil.isEmptyString(param.getUser_password())) {
        throw new MyException("필수 파라미터가 없습니다.");
      }

      ServiceResult sr = service.joinUser(param);
      if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
        result.addData("user", sr.getData());
      }
      result.setMyError(sr.getMyException());
    } catch (MyException e) {
      result.setMyError(e);
    } catch (Exception e) {
      result.setMyError();
      e.printStackTrace();
    }
    return result;
  }

  @RequestMapping(value = "/user/edit")
  @ResponseBody
  public ApiResult editUser(HttpServletRequest request, @RequestBody(required = false) User param, @CurrentUser User currentUser) throws Exception {
    ApiResult result = new ApiResult();
    try {
      // token 사용시 token의 유저정보
      Integer current_user_id = (currentUser != null) ? currentUser.getUser_id() : null;

      if (param == null) {
        throw new MyException("요청 내용이 없습니다.");
      }
      // 회원가입 필수 값이 user_email, user_type 이라고 가정
      if (CUtil.isEmptyString(param.getUser_email()) || param.getUser_type() == null || param.getUser_id() == null) {
        throw new MyException("필수 파라미터가 없습니다.");
      }
      log.error("{}", param.toString());
      ServiceResult sr = service.editUser(param);
      if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
        result.addData("user", sr.getData());
      }
      result.setMyError(sr.getMyException());
    } catch (MyException e) {
      result.setMyError(e);
    } catch (Exception e) {
      result.setMyError();
      e.printStackTrace();
    }
    return result;
  }

  @RequestMapping(value = "/file/upload")
  @ResponseBody
  public ApiResult fileUpload(HttpServletRequest request, MultipartFile file) throws Exception {
    ApiResult result = new ApiResult();
    try {
      if (file == null) {
        throw new MyException("NO_REQUIRED_PARAMS: file");
      }
      log.info("{}, {}, {}", file.getContentType(), file.getSize(), file.getName());
      ServiceResult sr = service.fileUpload(file);
      if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
        result.setData(sr.getData());
      }
      result.setMyError(sr.getMyException());
    } catch (MyException e) {
      result.setMyError(e);
    } catch (Exception e) {
      result.setMyError();
      e.printStackTrace();
    }
    return result;
  }

  @RequestMapping(value = "/user/info")
  @ResponseBody
  public ApiResult getUser(HttpServletRequest request, @RequestBody(required = false) SearchParam param) throws Exception {
    ApiResult result = new ApiResult();
    try {

      log.info("param:{}", param);
      SearchParam.checkRequiedParams(param, "search_type", "search_word");
      param.setSearchParams();

      log.info("param:{}", param);
      ServiceResult sr = service.getUser(param);
      if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
        result.addData("user", sr.getData());
      }
      result.setMyError(sr.getMyException());

    } catch (MyException e) {
      result.setMyError(e);
    } catch (Exception e) {
      result.setMyError();
      e.printStackTrace();
    }
    return result;
  }

  @RequestMapping(value = "/user/info/{user_id}")
  @ResponseBody
  public ApiResult getUserById(HttpServletRequest request, @PathVariable int user_id) throws Exception {
    ApiResult result = new ApiResult();
    try {

      log.info("param:{}", user_id);

      ServiceResult sr = service.getUserById(user_id);
      if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
        result.addData("user", sr.getData());
      }
      result.setMyError(sr.getMyException());

    } catch (Exception e) {
      result.setMyError();
      e.printStackTrace();
    }
    return result;
  }

  @RequestMapping(value = "/user/list")
  @ResponseBody
  public ApiResult getUserList(HttpServletRequest request, @RequestBody(required = false) SearchParam param) throws Exception {
    ApiResult result = new ApiResult();
    try {

      // 화면에서 Paging을 위한 데이터 요청은 row_count, page_no를 변수로 받아야 한다.
      // row_count: 한번에 보여줄 데이터 갯수
      // page_no: 현재 page 번호
      // row_start: row_count와 page_no로 계산된 검색 시작갯수

      if (param == null)
        param = new SearchParam();
      if (param.get("row_count") == null) {
        param.add("row_count", CValue.default_row_count);
      }

      if (param.get("page_no") == null) {
        param.add("page_no", CValue.default_page_no);
      }
      Integer row_start = (CUtil.objectToInteger(param.get("page_no")) - 1) * CUtil.objectToInteger(param.get("row_count"));
      param.add("row_start", row_start.toString());

      ServiceResult sr = service.getUserListForPaging(param);
      if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
        result.setData(sr.getData());
      }
      result.setMyError(sr.getMyException());

    } catch (Exception e) {
      result.setMyError();
      e.printStackTrace();
    }
    return result;
  }

}
