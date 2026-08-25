package com.cafe.app.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe.app.auth.service.AuthService;
import com.cafe.app.user.vo.RequestLoginVO;
import com.cafe.app.user.vo.RequestRegisterVO;

import jakarta.servlet.http.HttpSession;

import javax.validation.Valid;
import java.util.UUID;

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
    public String actionRegister(@Valid RequestRegisterVO requestRegisterVO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("msg", "필수 항목을 입력해주세요.");
            return "redirect:/register";
        }

        if(!requestRegisterVO.getPassword().equals(requestRegisterVO.getConfirmPassword())){ // 비밀번호 일치 확인
            redirectAttributes.addFlashAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "redirect:/register";
        }

        try {
            int successCnt = this.authService.register(requestRegisterVO);
            // 회원가입 완료 팝업 -> 로그인 화면 이동
            if (successCnt > 0) {
                redirectAttributes.addFlashAttribute("registerMsg", "회원가입이 완료되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("registerMsg", "회원가입 실패하였습니다.");
            }
            return "redirect:/login";
        } catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/register";
        }
    }

    // 로그인
    @GetMapping("/login")
    public String viewLoginPage() {
        return "user/login";
    }
     

    @PostMapping("/login")
    public String actionLogin(RequestLoginVO requestLoginVO, Model model, HttpServletRequest request) {
        boolean isLogin = authService.login(requestLoginVO);
        if (isLogin) {
            // 먹고가기 (IN) 정보 유지를 위해 백업
            HttpSession backup = request.getSession();
            String orderType = (String) backup.getAttribute("orderType");
            // HttpServletRequest :  HTTP 요청 전체 정보
            // HttpSession : 세션만 다루는 객체 -> 새 세션 생성 불가
            request.getSession().invalidate(); // 기존 세션 통째로 무효화, 같은 브라우저는 JSESSIONID를 공유
            HttpSession session = request.getSession(true);  // 새 세션 생성 -> 새 JSESSIONID 발급
            session.setAttribute("userId", requestLoginVO.getUserId()); // 기존 세션이 없으면 새 세션 생성
            session.setAttribute("orderStartTime", System.currentTimeMillis());
            if(orderType != null){
                session.setAttribute("orderType", orderType);
            }
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

    // 먹고가기 > 비회원 주문
    @GetMapping("/order/guest")
    public String guestOrder(HttpSession session){
        // 임시 ID 발급
        String userId = "GUEST_" + UUID.randomUUID().toString().substring(0, 8);
        RequestRegisterVO requestRegisterVO = new RequestRegisterVO();
        requestRegisterVO.setUserId(userId);
        requestRegisterVO.setPassword(userId);
        this.authService.register(requestRegisterVO);
        session.setAttribute("userId", userId);
        return "redirect:/order";
    }

	

}
