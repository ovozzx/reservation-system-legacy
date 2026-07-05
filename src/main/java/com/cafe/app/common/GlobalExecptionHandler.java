package com.cafe.app.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExecptionHandler {

    // 500.jsp
    @ExceptionHandler(Exception.class) // 모든 예외의 최상위 부모 -> 구체적인 핸들러에 안 걸리는 예외는 전부 handleException에서 잡힘!
    public String handleException(Exception e, Model model) {
        model.addAttribute("msg", "서버 오류 발생");
        return "error/error";
    }

    // 400 :  @Valid 실패 잡힘
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleCheckValidation(IllegalArgumentException e, Model model){
        model.addAttribute("msg", e.getMessage());
        return "error/error";
    }

}
