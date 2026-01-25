package com.cafe.app.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe.app.auth.service.AuthService;
import com.cafe.app.user.vo.RequestLoginVO;
import com.cafe.app.user.vo.RequestRegisterVO;

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
    public String actionRegister(RequestRegisterVO requestRegisterVO, RedirectAttributes redirectAttributes) {
        int successCnt = this.authService.register(requestRegisterVO);
        // 회원가입 완료 팝업 -> 로그인 화면 이동
        if(successCnt > 0){
            redirectAttributes.addFlashAttribute("registerMsg", "회원가입이 완료되었습니다.");
        }else{
            redirectAttributes.addFlashAttribute("registerMsg", "회원가입 실패하였습니다.");
        }
        return "redirect:/login";
    }

    // 로그인
    @GetMapping("/login")
    public String viewLoginPage() {
        return "user/login";
    }
     

    @PostMapping("/login")
    public String actionLogin(RequestLoginVO requestLoginVO, Model model, HttpSession session) {
        boolean isLogin = authService.login(requestLoginVO);
        if (isLogin) {
            session.setAttribute("userId", requestLoginVO.getUserId()); // 기존 세션이 없으면 새 세션 생성
            return "redirect:/order"; // 로그인 성공 시 주문 페이지로 리다이렉트
        } else{
            model.addAttribute("loginMsg", "아이디 또는 패스워드가 일치하지 않습니다."); 
            return "user/login";
        }
    }

    // 로그아웃
	@GetMapping("/logout")
	public String doLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	

}
