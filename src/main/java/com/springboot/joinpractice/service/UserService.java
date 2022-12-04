package com.springboot.joinpractice.service;

import com.springboot.joinpractice.domain.User;
import com.springboot.joinpractice.domain.dto.UserJoinRequest;
import com.springboot.joinpractice.exception.AppException;
import com.springboot.joinpractice.exception.ErrorCode;
import com.springboot.joinpractice.repository.UserRepository;
import com.springboot.joinpractice.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    // DB에 있는 userName 과 중복체크해야되기 때문에 DB와 연결이 필요
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; // 1시간

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
                // BCryptPasswordEncoder의 encoder 메서드를 사용하여 password를 암호화한다.
                .password(encoder.encode(password))
                .build();
        userRepository.save(user);

        return "SUCCESS";

    }

    public String login(String userName, String password) {
        // userName 없음
        User selectedUser = userRepository.findByUserName(userName)
                // NOT_NOTFOUND가 나야함
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOTFOUND, userName + "이 없습니다"));

        // password 틀림
        log.info("selectedPw: {} pw: {}", selectedUser.getPassword(), password);
        if(!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "password를 잘 못 입력하였습니다.");
        }

        // 앞에서 exception 안났으면 token 발행
        String token = JwtTokenUtil.createToken(selectedUser.getUserName(), key, expireTimeMs);
        return token;
    }
}
