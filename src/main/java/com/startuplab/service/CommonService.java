package com.startuplab.service;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.startuplab.common.vo.SearchParam;
import com.startuplab.dao.CommonDAO;
import com.startuplab.vo.Code;
import com.startuplab.vo.Fcm;
import com.startuplab.vo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonService {

  @Qualifier("sqlSession")
  @Autowired
  private SqlSession sqlSession;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AsyncService async;

  @PostConstruct
  public void init() {

    try {
      // log.error("Thread Caller: {}", Thread.currentThread().getId());
      // async.asyncTest();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public int insertCode(Code param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    return dao.insertCode(param);
  }

  public int updateCode(Code param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    return dao.updateCode(param);
  }

  public int insertFcm(Fcm param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    return dao.insertFcm(param);
  }

  public int deleteFcm(Fcm param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    return dao.deleteFcm(param);
  }

  public int insertUser(User param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    param.setUser_password(passwordEncoder.encode(param.getUser_password()));
    return dao.insertUser(param);
  }

  public int updateUser(User param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    param.setUser_password(null);
    return dao.updateUser(param);
  }

  public int updateUserPassword(User param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    User user = new User();
    user.setUser_id(param.getUser_id());
    user.setUser_password(passwordEncoder.encode(param.getUser_password()));
    return dao.updateUser(user);
  }

  public List<User> getUserList(SearchParam param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    return dao.getUserList(param);
  }

  public User getUser(SearchParam param) throws SQLException {
    List<User> list = getUserList(param);
    return (list != null && !list.isEmpty()) ? list.get(0) : null;
  }

  public User getUserById(int user_id) throws SQLException {
    SearchParam id = new SearchParam("user_id", user_id);
    return getUser(id);
  }

  public User getUserByEmail(String email) throws SQLException {
    SearchParam param = new SearchParam("user_email", email);
    return getUser(param);
  }

  public List<Fcm> getFcmList(SearchParam param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    return dao.getFcmList(param);
  }

  public Fcm getFcm(SearchParam param) throws SQLException {
    List<Fcm> list = getFcmList(param);
    return (list != null && !list.isEmpty()) ? list.get(0) : null;
  }

  public List<Code> getCodeList(SearchParam param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    return dao.getCodeList(param);
  }

  public int getUserListTotalCount(SearchParam param) throws SQLException {
    CommonDAO dao = sqlSession.getMapper(CommonDAO.class);
    return dao.getUserListTotalCount(param);
  }

}
