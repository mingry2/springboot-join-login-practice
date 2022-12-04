package com.springboot.joinpractice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// java에서 제공하는 exception 말고 사용자정의의 exception 만들기
// exception의 최고 조상 runtimeException의 상속을 받아 작성
@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{
    // ErrorCode를 enum으로 작성해서 만들어둠
    // Enum -> Enum에 미리 지정 해놓고 그 값 말고 다른 값들을 넣지 못하게 하여
    // 예측한 범위 내에서 프로그램이 작동하도록 하기 위한 기능
    private ErrorCode errorCode;
    private String message;
}
