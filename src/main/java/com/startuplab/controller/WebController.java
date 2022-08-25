package com.startuplab.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.startuplab.vo.Datas;
import com.startuplab.vo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/web")
public class WebController {

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

    @RequestMapping(value = "/db/store")
    @ResponseBody
    public ApiResult dbStore(HttpServletRequest request, @RequestBody(required = false) Datas param) {
        ApiResult result = new ApiResult();
        try {
            log.info("param:{}", param);
            if (param == null) {
                throw new MyException("요청 내용이 없습니다.");
            }
            ServiceResult sr = service.dbStore(param);
            if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
                result.addData("data", sr.getData());
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

}
