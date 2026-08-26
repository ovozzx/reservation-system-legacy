package com.cafe.app.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false); // 세션 없으면 null 반환 (생성 안 함), 비로그인 사용자에게 불필요한 빈 세션이 생성되지 않도록
        if(session == null || session.getAttribute("userId") == null){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
