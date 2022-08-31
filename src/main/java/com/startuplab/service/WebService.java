package com.startuplab.service;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Data;
import com.startuplab.common.vo.SearchParam;
import com.startuplab.common.vo.WorkDistribute;
import com.startuplab.dao.CommonDAO;
import com.startuplab.dao.WebDAO;
import com.startuplab.vo.Code;
import com.startuplab.vo.Datas;
import com.startuplab.vo.Fcm;
import com.startuplab.vo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebService {
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

    public int insertDatas(Datas param) throws SQLException {
        WebDAO dao = sqlSession.getMapper(WebDAO.class);
        return dao.insertDatas(param);
    }

    public List<Datas> getDatasList(SearchParam param) throws SQLException {
        WebDAO dao = sqlSession.getMapper(WebDAO.class);
        return dao.getDatasList(param);
    }

    public Datas getDatas(SearchParam param) throws SQLException {
        List<Datas> list = getDatasList(param);
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    public int updateDatas(Datas param) throws SQLException {
        WebDAO dao = sqlSession.getMapper(WebDAO.class);
        return dao.updateDatas(param);
    }
    public List<Datas> selectDatas(Datas param) throws SQLException {
        WebDAO dao = sqlSession.getMapper(WebDAO.class);
        return dao.selectDatas(param);
    }
    public List<User> selectUser(User param) throws SQLException {
        WebDAO dao = sqlSession.getMapper(WebDAO.class);
        return dao.selectUser(param);
    }
    public int workDistribute(WorkDistribute param) throws SQLException{
        WebDAO dao = sqlSession.getMapper(WebDAO.class);
        return dao.workDistribute(param);
    }
    public int selectDatasNum(int assignment_id, int data_status) throws SQLException {
        WebDAO dao = sqlSession.getMapper(WebDAO.class);
        return dao.selectDatasNum(assignment_id, data_status);
    }
   
}
