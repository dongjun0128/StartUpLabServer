package com.startuplab.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.startuplab.common.vo.MetaData;
import com.startuplab.common.vo.SearchKeyWord;
import com.startuplab.common.vo.SearchParam;
import com.startuplab.common.vo.ServiceResult;
import com.startuplab.common.vo.WorkDistribute;
import com.startuplab.config.oauth2.CurrentUser;
import com.startuplab.service.ApiService;
import com.startuplab.service.CommonService;
import com.startuplab.vo.Datas;
import com.startuplab.vo.User;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

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

    @RequestMapping(value = "/db/edit")
    @ResponseBody
    public ApiResult editDatas(HttpServletRequest request, @RequestBody(required = false) Datas param) throws Exception {
        ApiResult result = new ApiResult();

        try {

            if (param == null) {
                throw new MyException("요청 내용이 없습니다.");
            }
            // 필수 값이 data_id 라고 가정
            if (param.getData_id() == 0) {
                throw new MyException("필수 파라미터가 없습니다.");
            }
            log.error("{}", param.toString());
            ServiceResult sr = service.editDatas(param);
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

    @RequestMapping(value = "/work/info")
    @ResponseBody
    public ApiResult dbSelect(HttpServletRequest request, @RequestBody(required = false) Datas param) {
        ApiResult result = new ApiResult();
        try {
            log.info("param:{}", param);
            if (param == null) {
                throw new MyException("요청 내용이 없습니다.");
            }

            if (param.getData_status() == 0) {
                throw new MyException("필수 파라미터인 status를 입력해주세요!");
            }

            if (param.getWork_id() == 0) {
                throw new MyException("필수 파라미터인 work_id 입력해주세요!");
            }

            ServiceResult sr = service.dbSelect(param);
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

    @RequestMapping(value = "/manager/work/info")
    @ResponseBody
    public ApiResult managerDbSelect(HttpServletRequest request, @RequestBody(required = false) Datas param) {
        ApiResult result = new ApiResult();
        try {
            log.info("param:{}", param);
            if (param == null) {
                throw new MyException("요청 내용이 없습니다.");
            }

            if (param.getWork_id() == 0) {
                throw new MyException("필수 파라미터인 work_id 입력해주세요!");
            }

            ServiceResult sr = service.dbSelect(param);
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

    @RequestMapping(value = "/assignment/user/list")
    @ResponseBody
    public ApiResult userSelect(HttpServletRequest request, @RequestBody(required = false) User param) {
        ApiResult result = new ApiResult();

        try {
            log.info("param:{}", param);

            ServiceResult sr = service.userSelect(param);
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

    @ResponseBody
    @RequestMapping(value = "/work/distribution")
    public ApiResult workDistribute(@RequestBody WorkDistribute param) throws SQLException {
        ApiResult result = new ApiResult();
        log.info("idsArray={}", param.getIdsArray());
        log.info("user_id={}", param.getUser_id());
        try {
            log.info("param:{}", param);
            if (param == null) {
                throw new MyException("요청 내용이 없습니다.");
            }

            if (param.getIdsArray() == null) {
                throw new MyException("필수 파라미터인 idsArray 를 입력해주세요!");
            }

            if (param.getUser_id() == 0) {
                throw new MyException("필수 파라미터인 user_id 를 입력해주세요!");
            }

            ServiceResult sr = service.workDistribute(param);
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

    @RequestMapping(value = "/assignment/nums")
    @ResponseBody
    public ApiResult dbAssignmentNumSelect(HttpServletRequest request) {
        ApiResult result = new ApiResult();
        log.info("dbAssignmentNumSelect 시작");

        try {
            ServiceResult sr = service.dbAssignmentNumSelect();
            if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
                result.addData("data", sr.getData());
            }
            result.setMyError(sr.getMyException());

        } catch (Exception e) {
            result.setMyError();
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/work/nums")
    @ResponseBody
    public ApiResult dbWorkNumSelect(HttpServletRequest request, @RequestBody(required = false) Datas param) {
        ApiResult result = new ApiResult();
        log.info("dbWorkNumSelect 시작");
        log.info("param:{}", param);
        log.info("{}", param.getWork_id());

        try {

            if (param.getWork_id() == 0) {
                throw new MyException("필수 파라미터인 Work_id 를 입력해주세요!");
            }

            ServiceResult sr = service.dbWorkNumSelect(param);
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

    @RequestMapping(value = "/search")
    @ResponseBody
    public ApiResult dbSearch(HttpServletRequest request, @RequestBody(required = false) SearchKeyWord param) {
        ApiResult result = new ApiResult();
        log.info("dbSearch 시작");

        try {
            if (param.getData_status() == null) {
                throw new MyException("필수 파라미터인 data_status 를 입력해주세요!");
            }

            if (param.getWork_id() == null) {
                throw new MyException("필수 파라미터인 work_id 를 입력해주세요!");
            }

            if (param.getColumnName() == null) {
                throw new MyException("필수 파라미터인 columnName 를 입력해주세요!");
            }

            if (param.getKeyword() == null) {
                throw new MyException("필수 파라미터인 keyword 를 입력해주세요!");
            }

            ServiceResult sr = service.dbSearch(param);
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

    @RequestMapping(value = "/metadata")
    @ResponseBody
    public ApiResult metaSelect(HttpServletRequest request, @RequestBody(required = false) MetaData param) {
        ApiResult result = new ApiResult();
        log.info("metaData Search 시작");

        try {
            if (param.getWork_id() == 0) {
                throw new MyException("필수 파라미터인 work_id 를 입력해주세요!");
            }
            ServiceResult sr = service.metaSelect(param);
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
