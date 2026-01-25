package com.cafe.app.auth.service;

import com.cafe.app.user.vo.RequestLoginVO;
import com.cafe.app.user.vo.RequestRegisterVO;

public interface AuthService {

    boolean login(RequestLoginVO requestLoginVO);

    int register(RequestRegisterVO requestRegisterVO);

    

}
