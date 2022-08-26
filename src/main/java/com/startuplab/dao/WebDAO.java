package com.startuplab.dao;

import java.sql.SQLException;
import java.util.List;
import com.startuplab.common.vo.SearchParam;
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

}
