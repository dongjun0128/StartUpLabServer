package com.startuplab.service;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.startuplab.common.exception.MyException;
import com.startuplab.common.exception.MyException.MyError;
import com.startuplab.common.vo.FileUploadVo;
import com.startuplab.common.vo.MetaData;
import com.startuplab.common.vo.SearchKeyWord;
import com.startuplab.common.vo.SearchParam;
import com.startuplab.common.vo.ServiceResult;
import com.startuplab.common.vo.WorkDistribute;
import com.startuplab.vo.Code;
import com.startuplab.vo.Datas;
import com.startuplab.vo.Fcm;
import com.startuplab.vo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiService {

  @Autowired
  private FileUploadService fu;

  @Autowired
  private CommonService common;

  @Autowired
  private WebService web;

  @PostConstruct
  public void init() {

    try {

    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public ServiceResult getCodeList(SearchParam param) {
    ServiceResult sr = new ServiceResult();
    try {
      List<Code> list = common.getCodeList(param);
      sr.setData(list);
      sr.setMyException(new MyException(MyError.SUCCESS));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult joinUser(User vo) {
    ServiceResult sr = new ServiceResult();
    try {
      common.insertUser(vo);
      User newVo = common.getUser(new SearchParam("user_id", vo.getUser_id()));
      sr.setData(newVo);
      sr.setMyException(new MyException(MyError.SUCCESS));
    } catch (DuplicateKeyException e) {
      sr.setMyException(new MyException("Duplicate email."));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult editUser(User vo) {
    ServiceResult sr = new ServiceResult();
    try {
      common.updateUser(vo);
      User newVo = common.getUser(new SearchParam("user_id", vo.getUser_id()));
      sr.setData(newVo);
      sr.setMyException(new MyException(MyError.SUCCESS));
    } catch (DuplicateKeyException e) {
      sr.setMyException(new MyException("Duplicate email."));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  @Transactional
  public ServiceResult setFcm(Fcm param) {
    ServiceResult sr = new ServiceResult();
    try {
      User user = common.getUser(new SearchParam("user_id", param.getUser_id()));
      if (user == null) {
        sr.setMyException(new MyException("등록된 회원 정보가 없어요."));
        return sr;
      } else {
        if (user.getUser_status().equals(0)) {
          // 탈퇴거나 강제 탈퇴
          sr.setMyException(new MyException("서비스 이용이 불가합니다. 고객센터로 문의해 주세요."));
          return sr;
        }
      }

      Fcm old = common.getFcm(new SearchParam("fcm_token", param.getFcm_token()));
      if (old != null) {
        // 기존에 token이 등록되어 있는 경우
        if (param.getUser_id().equals(old.getUser_id())) {
          // 정상적으로 이미 등록되어 있는 토큰
          sr.setMyException(new MyException(MyError.SUCCESS));
          return sr;
        } else {
          log.warn("토큰이 같은데 User Id가 다름 :before: {}, after:{}", old.getUser_id(), param.getUser_id());
          // insertFcm에서 Update
        }
      }

      common.insertFcm(param);

      sr.setMyException(new MyException(MyError.SUCCESS));
    } catch (DuplicateKeyException e) {
      sr.setMyException(new MyException(e.getMessage()));
      e.printStackTrace();
    } catch (Exception e) {
      log.error("Exception: {}", e.getMessage());
      e.printStackTrace();
    } finally {
      if (!sr.getMyException().getMyError().equals(MyError.SUCCESS)) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        log.warn("Rollback");
      }
    }
    return sr;
  }

  public ServiceResult fileUpload(MultipartFile file) {
    ServiceResult sr = new ServiceResult();
    try {
      FileUploadVo uploadFile = fu.uploader(file);
      log.info("uploadFile: {}", uploadFile.toString());
      sr.setMyException(new MyException(MyError.SUCCESS));
      sr.addData("upload_file", uploadFile);
    } catch (DuplicateKeyException e) {
      sr.setMyException(new MyException(e.getMessage()));
      // } catch (MyException e) {
      // sr.setMyException(e);
      // e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult getUser(SearchParam param) {
    ServiceResult sr = new ServiceResult();
    try {
      User user = common.getUser(param);
      sr.setData(user);
      sr.setMyException(new MyException(MyError.SUCCESS));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult getUserById(int param) {
    ServiceResult sr = new ServiceResult();
    try {
      User user = common.getUserById(param);
      sr.setData(user);
      sr.setMyException(new MyException(MyError.SUCCESS));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult dbStore(Datas vo) {
    ServiceResult sr = new ServiceResult();
    try {
      web.insertDatas(vo);
      Datas newVo = web.getDatas(new SearchParam("data_id", vo.getData_id()));
      sr.setData(newVo);
      sr.setMyException(new MyException(MyError.SUCCESS));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult editDatas(Datas vo) {
    ServiceResult sr = new ServiceResult();
    try {
      web.updateDatas(vo);
      Datas newVo = web.getDatas(new SearchParam("data_id", vo.getData_id()));
      sr.setData(newVo);
      sr.setMyException(new MyException(MyError.SUCCESS));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult dbSelect(Datas vo) {
    ServiceResult sr = new ServiceResult();
    try {
      List<Datas> list = web.selectDatas(vo);
      sr.setData(list);
      sr.setMyException(new MyException(MyError.SUCCESS));

    } catch (DuplicateKeyException e) {
      sr.setMyException(new MyException("Duplicate email."));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult userSelect(User vo) {
    ServiceResult sr = new ServiceResult();
    try {
      List<User> list = web.selectUser(vo);
      sr.setData(list);
      sr.setMyException(new MyException(MyError.SUCCESS));

    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult workDistribute(WorkDistribute vo) {
    ServiceResult sr = new ServiceResult();
    try {
      JSONObject resultJson = new JSONObject();

      web.workDistribute(vo);

      resultJson.put("idsArray", vo.getIdsArray());
      resultJson.put("user_id", vo.getUser_id());
      resultJson.put("data_status", 2);

      sr.setData(resultJson);
      sr.setMyException(new MyException(MyError.SUCCESS));

    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult dbAssignmentNumSelect() {
    ServiceResult sr = new ServiceResult();
    try {
      JSONArray dbNumsArry = new JSONArray();

      for (int assignment_id = 1; assignment_id < 5; assignment_id++) {
        JSONObject dbNums = new JSONObject();
        dbNums.put("assignment_id", assignment_id);
        for (int data_status = 1; data_status < 5; data_status++) {
          dbNums.put("data_status" + data_status, web.selectAssignmentDatasNum(assignment_id, data_status));
        }
        dbNumsArry.add(dbNums);
      }
      sr.setData(dbNumsArry);
      sr.setMyException(new MyException(MyError.SUCCESS));

    } catch (DuplicateKeyException e) {
      sr.setMyException(new MyException("Duplicate email."));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult dbWorkNumSelect(Datas param) {
    ServiceResult sr = new ServiceResult();
    try {
      JSONObject dbNums = new JSONObject();
      dbNums.put("work_id", param.getWork_id());
      for (int data_status = 1; data_status < 5; data_status++) {
        dbNums.put("data_status" + data_status, web.selectWorkDatasNum(param.getWork_id(), data_status));
      }
      sr.setData(dbNums);
      sr.setMyException(new MyException(MyError.SUCCESS));

    } catch (DuplicateKeyException e) {
      sr.setMyException(new MyException("Duplicate email."));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult dbSearch(SearchKeyWord vo) {
    ServiceResult sr = new ServiceResult();
    try {
      List<Datas> list = web.searchDatas(vo);
      sr.setData(list);
      sr.setMyException(new MyException(MyError.SUCCESS));

    } catch (DuplicateKeyException e) {
      sr.setMyException(new MyException("Duplicate email."));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

  public ServiceResult metaSelect(MetaData vo) {
    ServiceResult sr = new ServiceResult();
    try {
      List<MetaData> list = web.selectMetas(vo);
      sr.setData(list);
      sr.setMyException(new MyException(MyError.SUCCESS));
    } catch (DuplicateKeyException e) {
      sr.setMyException(new MyException("Duplicate meta."));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sr;
  }

}
