package com.cafe.app.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.app.auth.repository.AuthRepository;
import com.cafe.app.auth.service.AuthService;
import com.cafe.app.common.util.PasswordUtil;
import com.cafe.app.user.vo.RequestLoginVO;
import com.cafe.app.user.vo.RequestRegisterVO;
import com.cafe.app.user.vo.VerifyLoginVO;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AuthRepository authRepository;

    @Override
    public boolean login(RequestLoginVO requestLoginVO) {
        // 해당 id의 salt 값(Base64 문자)을 DB에서 조회
        // A 해당 id의 password 값(Base64 문자)을 DB에서 조회
        VerifyLoginVO verifyLoginVO = this.authRepository.readSaltAndPwById(requestLoginVO);
        // B 입력된 비밀번호 조회된 salt로 해시처리
        String inputPassword = PasswordUtil.hashPassword(requestLoginVO.getPassword(), verifyLoginVO.getSalt()); // 입력한 패스워드, 조회한 salt => Base64 문자열 
        // A == B 일치 여부 확인 
        if(verifyLoginVO != null && inputPassword.equals(verifyLoginVO.getPassword())){
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public int register(RequestRegisterVO requestRegisterVO) {
        // salt 발급 
        String saltBaseStr = PasswordUtil.generateSalt();
        // salt로 비밀번호 해시
        String passwordBaseStr = PasswordUtil.hashPassword(requestRegisterVO.getPassword(), saltBaseStr);
        requestRegisterVO.setPassword(passwordBaseStr);
        requestRegisterVO.setSalt(saltBaseStr);
        // DB에 회원정보 저장 : salt, 해시 패스워드 
        int insertCnt = this.authRepository.saveRegisterInfo(requestRegisterVO);
        // throw new UnsupportedOperationException("Unimplemented method 'register'");
        return insertCnt;
    }

}
