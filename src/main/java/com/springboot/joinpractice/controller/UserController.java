package com.springboot.joinpractice.controller;

import com.springboot.joinpractice.domain.dto.UserJoinRequest;
import com.springboot.joinpractice.domain.dto.UserLoginRequest;
import com.springboot.joinpractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// User가 가입(join)하는 API이기 때문에 UserCotroller

@RestController
@RequestMapping("/api/v1/users") // users로 오는 요청은 모두 이 컨트롤러가 처리함
@RequiredArgsConstructor // DI할 때 생성자를 따로 만들어주지 않아도 된다.-> UserService 할 때
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    // @RequestBody로 받아온 내용을 UserJoinRequest 타입의 dto로 받아옴
    // UserJoinRequest 로는 userName, password를 받아옴
    // ResponseEntity는 UserJoinRequest로 받아온 요청에대한 응답을 담아서 보낸다.
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) {
        userService.join(dto.getUserName(),dto.getPassword());
        return ResponseEntity.ok().body("회원가입이 성공하였습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest dto) {
        // userService.login애서 성공하면 token을 반환할것이기 때문
        String token = userService.login(dto.getUserName(),dto.getPassword());
        return ResponseEntity.ok().body(token);
    }

}
