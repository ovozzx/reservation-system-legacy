package com.cafe.app.auth.repository.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe.app.auth.repository.AuthRepository;
import com.cafe.app.user.vo.RequestLoginVO;
import com.cafe.app.user.vo.RequestRegisterVO;
import com.cafe.app.user.vo.VerifyLoginVO;

@Repository
public class AuthRepositoryImpl extends SqlSessionDaoSupport implements AuthRepository{

    private final String NAME_SPACE = "com.cafe.app.auth.repository.impl.AuthRepositoryImpl.";

    @Autowired
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    @Override
    public int saveRegisterInfo(RequestRegisterVO requestRegisterVO) {
       return super.getSqlSession().insert(this.NAME_SPACE + "saveRegisterInfo", requestRegisterVO);
    }

    @Override
    public VerifyLoginVO readSaltAndPwById(RequestLoginVO requestLoginVO) {
        return super.getSqlSession().selectOne(this.NAME_SPACE + "readSaltAndPwById", requestLoginVO); 
    }
    
}
