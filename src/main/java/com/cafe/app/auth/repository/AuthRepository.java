package com.cafe.app.auth.repository;

import com.cafe.app.user.vo.RequestLoginVO;
import com.cafe.app.user.vo.RequestRegisterVO;
import com.cafe.app.user.vo.VerifyLoginVO;

public interface AuthRepository {

    int saveRegisterInfo(RequestRegisterVO requestRegisterVO);

    VerifyLoginVO readSaltAndPwById(RequestLoginVO requestLoginVO);

}
