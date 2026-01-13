package com.cafe.app.auth.service;

import com.cafe.app.user.vo.RequestLoginVO;

public interface AuthService {

    boolean login(RequestLoginVO requestLoginVO);

}
