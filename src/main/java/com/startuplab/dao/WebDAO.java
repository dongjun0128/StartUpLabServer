package com.startuplab.dao;

import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.support.MetaDataAccessException;
import com.startuplab.common.vo.MetaData;
import com.startuplab.common.vo.SearchKeyWord;
import com.startuplab.common.vo.SearchParam;
import com.startuplab.common.vo.WorkDistribute;
import com.startuplab.vo.Code;
import com.startuplab.vo.Datas;
import com.startuplab.vo.Fcm;
import com.startuplab.vo.User;

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

    int selectWorkDatasNum(int work_id, int data_status);

    List<Datas> searchDatas(SearchKeyWord param) throws SQLException;

    List<MetaData> selectMetas(MetaData param) throws SQLException;

    int emailToId(User param) throws SQLException;
}
