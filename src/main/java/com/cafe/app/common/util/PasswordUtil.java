package com.cafe.app.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;



public class PasswordUtil {

    // salt 생성
    public static String generateSalt(){ // 객체 생성 없이 쓰기 위해 static 사용
        SecureRandom random  = new SecureRandom(); // 예측 불가 암호용 난수 생성기
        byte[] salt = new byte[16]; // 128 bit (실무 표준, 충분한 엔트로피 제공), 1 byte = 8 bit, bit -> 0 or 1 
        // salt 배열을 무작위 값으로 채움, 실행할 때마다 다른 값 생성. 
        // 1 byte(8 bit)씩 반복하여 채움 (범위 : -128 ~ 127) signed byte
        // 채울 때마다 동일한 값 나올 수 있음. [12, 45, 12, -7, 12, 88, -7, ...]
        random.nextBytes(salt); 
        // Base64 : 이진 데이터 => ASCII 문자로 안전하게 표현하기 위한 인코딩 방식 (UTF와 같은 인코딩 규칙 적용 필요 없음)
        // 3 byte를 4 문자로 변환 => 16 byte 24 문자 (패딩 값 포함)
        return Base64.getEncoder().encodeToString(salt); // byte[]를 문자열 변환
    }

    // 패스워드 해시 처리
    public static String hashPassword(String password, String salt){
        try{
            // MessageDigest : 임의 길이의 데이터를 입력 받아, 고정 길이의 해시값을 만들어주는 Java 암호 API 클래스 
            // 모든 입력을 byte[] 이진 데이터 형태로만 처리한다 (해시 함수는 비트 단위 연산)
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] saltBytes = Base64.getDecoder().decode(salt); // 인코딩된 salt 값 디코딩하여 사용 (엔트로피?)
            md.update(saltBytes); // 누적 (이어붙임)
            // 문자 -> byte 인코딩 방식 UTF_8 (실무 표준)로 지정 
            byte[] hashed = md.digest(password.getBytes(StandardCharsets.UTF_8)); // update로 누적한 데이터와 인자로 전달한 데이터를 합쳐서 해시 계산하고 결과 반환
            return Base64.getEncoder().encodeToString(hashed);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        // TODO : PBKDF2, BCrypt, Argon2 사용해보기
   
    }
}
