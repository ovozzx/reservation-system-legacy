package com.cafe.app.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestRegisterVO {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    private String email;
    @NotBlank
    private String salt;

}
