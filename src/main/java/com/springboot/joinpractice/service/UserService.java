package com.springboot.joinpractice.service;

import com.springboot.joinpractice.domain.User;
import com.springboot.joinpractice.domain.dto.UserJoinRequest;
import com.springboot.joinpractice.exception.AppException;
import com.springboot.joinpractice.exception.ErrorCode;
import com.springboot.joinpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    // DB에 있는 userName 과 중복체크해야되기 때문에 DB와 연결이 필요
    private final UserRepository userRepository;

    public String join(String userName, String password){
        // 1. userName 중복 check
        userRepository.findByUserName(userName)
                // 만약에 해당 값이 있다면 -> ifPresent()
                // user 검색한 결과
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, userName + "는 이미 있습니다.");
                });

        // 2. 저장 .save() -> DB에 저장하는 것이기 때문에 User Entity의 형식을 builder로 만들어서
        // save(user) < 로 저장한다.
        User user = User.builder()
                .userName(userName)
                .password(password)
                .build();
        userRepository.save(user);

        return "SUCCESS";

    }
}
