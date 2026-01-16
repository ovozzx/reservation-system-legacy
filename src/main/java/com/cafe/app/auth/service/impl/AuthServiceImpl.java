package com.cafe.app.auth.service.impl;

import org.springframework.stereotype.Service;

import com.cafe.app.auth.service.AuthService;
import com.cafe.app.common.util.PasswordUtil;
import com.cafe.app.user.vo.RequestLoginVO;

@Service
public class AuthServiceImpl implements AuthService{

    @Override
    public boolean login(RequestLoginVO requestLoginVO) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'login'");
        // 해당 id의 salt 값을 DB에서 조회 후, 입력된 비밀번호와 비교하는 로직 필요
        // 예시: User user = userRepository.findByUserId(requestLoginVO.getUserId());
        if (requestLoginVO.getUserId() != null && requestLoginVO.getPassword().equals("password")) {
            return true;
        } else{
            return false;
        }

    }

    @Override
    public void register(RequestLoginVO requestLoginVO) {
        // salt 발급 
        System.out.println(PasswordUtil.generateSalt());

        // salt로 비밀번호 해시

        // DB에 회원정보 저장 : salt, 해시 패스워드 
        // throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

}
