package com.startuplab.dao;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.jdbc.support.MetaDataAccessException;
import com.startuplab.common.vo.MetaData;
import com.startuplab.common.vo.SearchKeyWord;
import com.startuplab.common.vo.SearchParam;
import com.startuplab.common.vo.WorkDistribute;
import com.startuplab.vo.Assignment;
import com.startuplab.vo.Code;
import com.startuplab.vo.Datas;
import com.startuplab.vo.Excel;
import com.startuplab.vo.Fcm;
import com.startuplab.vo.User;
import com.startuplab.vo.Work;

public interface WebDAO {

    int insertCode(Code param) throws SQLException;

    int updateCode(Code param) throws SQLException;

    int insertFcm(Fcm param) throws SQLException;

    int deleteFcm(Fcm param) throws SQLException;

    int insertUser(User param) throws SQLException;

    int updateUser(User param) throws SQLException;

    List<User> getUserList(SearchParam param) throws SQLException;

    List<Fcm> getFcmList(SearchParam param) throws SQLException;

    List<Code> getCodeList(SearchParam param) throws SQLException;

    int insertDatas(Datas param) throws SQLException;

    List<Datas> getDatasList(SearchParam param) throws SQLException;

    int updateDatas(Datas param) throws SQLException;

    List<Datas> selectDatas(Datas param) throws SQLException;

    List<User> selectUser(User param) throws SQLException;

    int workDistribute(WorkDistribute param) throws SQLException;

    int selectAssignmentDatasNum(int assignment_id, int data_status);

    int selectWorkDatasNum(int work_id, int data_status, int user_id, int assignment_id);

    List<Datas> searchDatas(SearchKeyWord param) throws SQLException;

    List<MetaData> selectMetas(MetaData param) throws SQLException;

    int emailToId(User param) throws SQLException;

    List<Assignment> selectAllAssignment(Assignment param) throws SQLException;

    List<Work> selectAllWork(Work param) throws SQLException;

    int getSelectDatasCount(Datas param) throws SQLException;

    void excelToDb(Excel param) throws SQLException;
}
