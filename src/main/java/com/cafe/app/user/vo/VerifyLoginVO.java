package com.cafe.app.user.vo;

public class VerifyLoginVO {

    private String salt;
    private String password;

    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "VerifyLoginVO [salt=" + salt + ", password=" + password + "]";
    }
    
    

}
