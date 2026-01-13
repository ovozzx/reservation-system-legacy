package com.cafe.app.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cafe.app.auth.service.AuthService;
import com.cafe.app.user.vo.RequestLoginVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private AuthService authService;

    // 회원가입
    @GetMapping("/register")
    public String viewRegisterPage() {
        return "user/register";
    }   
    @PostMapping("/register")
    public String actionRegister() {
        // 회원가입 처리 로직 필요
        return "redirect:/login";
    }

    // 로그인
    @GetMapping("/login")
    public String viewLoginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String actionLogin(RequestLoginVO requestLoginVO, HttpSession session) {
        boolean isLogin = authService.login(requestLoginVO);
        if (isLogin) {
            session.setAttribute("userId", requestLoginVO.getUserId());
            return "redirect:/order"; // 로그인 성공 시 주문 페이지로 리다이렉트
        }
        return "user/login";
    }

    // 로그아웃
	@GetMapping("/logout")
	public String doLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	

}
