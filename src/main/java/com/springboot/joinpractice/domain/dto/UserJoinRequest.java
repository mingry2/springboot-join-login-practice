package com.springboot.joinpractice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// User에게 받을 정보 -> 가입(join)이니까 userName, password가 있어야함
@Getter
@AllArgsConstructor
public class UserJoinRequest {
    private String userName;
    private String password;
}
