package com.startuplab.controller;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.startuplab.vo.Assignment;
import com.startuplab.vo.Datas;
import com.startuplab.vo.User;
import com.startuplab.vo.Work;

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
                throw new MyException("?????? ????????? ????????????.");
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
    public ApiResult editDatas(HttpServletRequest request, @RequestBody(required = false) Datas param)
            throws Exception {
        ApiResult result = new ApiResult();

        try {

            if (param == null) {
                throw new MyException("?????? ????????? ????????????.");
            }
            // ?????? ?????? data_id ?????? ??????
            if (param.getData_id() == 0) {
                throw new MyException("?????? ??????????????? ????????????.");
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

            // ???????????? Paging??? ?????? ????????? ????????? row_count, page_no??? ????????? ????????? ??????.
            // row_count: ????????? ????????? ????????? ??????
            // page_no: ?????? page ??????
            // row_start: row_count??? page_no??? ????????? ?????? ????????????

            if (param == null) {
                throw new MyException("?????? ????????? ????????????.");
            }

            if (param.getData_status() == 0) {
                throw new MyException("?????? ??????????????? Data_status??? ??????????????????!");
            }

            if (param.getWork_id() == 0) {
                throw new MyException("?????? ??????????????? work_id ??????????????????!");
            }

            if (param.getRow_count() == 0) {
                param.setRow_count(CValue.default_row_count);
            }

            if (param.getPage_no() == 0) {
                param.setPage_no(CValue.default_page_no);
            }

            Integer row_start = (CUtil.objectToInteger(param.getPage_no()) - 1)
                    * CUtil.objectToInteger(param.getRow_count());
            param.setRow_start(row_start.toString());

            log.info("param:{}", param);

            ServiceResult sr = service.dbSelect(param);
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

    @RequestMapping(value = "/manager/work/info")
    @ResponseBody
    public ApiResult managerDbSelect(HttpServletRequest request, @RequestBody(required = false) Datas param) {
        ApiResult result = new ApiResult();
        try {

            // ???????????? Paging??? ?????? ????????? ????????? row_count, page_no??? ????????? ????????? ??????.
            // row_count: ????????? ????????? ????????? ??????
            // page_no: ?????? page ??????
            // row_start: row_count??? page_no??? ????????? ?????? ????????????

            log.info("param:{}", param);
            if (param == null) {
                throw new MyException("?????? ????????? ????????????.");
            }

            if (param.getWork_id() == 0) {
                throw new MyException("?????? ??????????????? work_id ??????????????????!");
            }

            if (param.getRow_count() == 0) {
                param.setRow_count(CValue.default_row_count);
            }

            if (param.getPage_no() == 0) {
                param.setPage_no(CValue.default_page_no);
            }

            Integer row_start = (CUtil.objectToInteger(param.getPage_no()) - 1)
                    * CUtil.objectToInteger(param.getRow_count());
            param.setRow_start(row_start.toString());

            ServiceResult sr = service.dbSelect(param);
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
                throw new MyException("?????? ????????? ????????????.");
            }

            if (param.getIdsArray() == null) {
                throw new MyException("?????? ??????????????? idsArray ??? ??????????????????!");
            }

            if (param.getUser_id() == 0) {
                throw new MyException("?????? ??????????????? user_id ??? ??????????????????!");
            }

            if (param.getData_status() == 0) {
                throw new MyException("?????? ??????????????? data_status ??? ??????????????????!");
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
        log.info("dbAssignmentNumSelect ??????");

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
        log.info("dbWorkNumSelect ??????");
        log.info("param:{}", param);
        log.info("{}", param.getWork_id());

        try {
            ServiceResult sr = service.dbWorkNumSelect(param);
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

    @RequestMapping(value = "/search")
    @ResponseBody
    public ApiResult dbSearch(HttpServletRequest request, @RequestBody(required = false) SearchKeyWord param) {
        ApiResult result = new ApiResult();
        log.info("dbSearch ??????");

        try {
            if (param.getWork_id() == null) {
                throw new MyException("?????? ??????????????? work_id ??? ??????????????????!");
            }

            if (param.getColumnName() == null) {
                throw new MyException("?????? ??????????????? columnName ??? ??????????????????!");
            }

            if (param.getKeyword() == null) {
                throw new MyException("?????? ??????????????? keyword ??? ??????????????????!");
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
        log.info("metaData Search ??????");

        try {
            if (param.getWork_id() == 0) {
                throw new MyException("?????? ??????????????? work_id ??? ??????????????????!");
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

    @RequestMapping(value = "/email/to/id")
    @ResponseBody
    public ApiResult emailToId(HttpServletRequest request, @RequestBody(required = false) User param) {
        ApiResult result = new ApiResult();
        log.info("emailToId ??????");

        try {
            if (param.getUser_email() == null) {
                throw new MyException("?????? ??????????????? user_email??? ??????????????????!");
            }
            ServiceResult sr = service.emailToId(param);
            if (sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
                result.addData("user_id", sr.getData());
            }
            result.setMyError(sr.getMyException());

        } catch (Exception e) {
            result.setMyError();
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/all/assignments")
    @ResponseBody
    public ApiResult selectAllAssignment(HttpServletRequest request, @RequestBody(required = false) Assignment param) {
        ApiResult result = new ApiResult();
        try {
            ServiceResult sr = service.selectAllAssignment(param);
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

    @RequestMapping(value = "/all/works")
    @ResponseBody
    public ApiResult selectAllWork(HttpServletRequest request, @RequestBody(required = false) Work param) {
        ApiResult result = new ApiResult();
        try {
            ServiceResult sr = service.selectAllWork(param);
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

    @RequestMapping(value = "/execel/to/db")
    @ResponseBody
    public ApiResult excelToDb(HttpServletRequest request, MultipartFile file, int assignment_id, int work_id) {
        ApiResult result = new ApiResult();
        log.info("execelToId ??????");
        log.info("{}, {}, {}, {}", file.getContentType(), file.getSize(), file.getName(), file.getClass());
        log.info("{}, {}", assignment_id, work_id);
        try {
            ServiceResult sr = service.excelToDb(file, assignment_id, work_id);
            result.setMyError(sr.getMyException());
        } catch (Exception e) {
            result.setMyError();
            e.printStackTrace();
        }

        return result;
    }
}
