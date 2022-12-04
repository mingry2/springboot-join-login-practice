package com.springboot.joinpractice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.joinpractice.domain.dto.UserJoinRequest;
import com.springboot.joinpractice.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean // userService를 흉내내는것
    UserService userService;

    // java object를 JSON으로 만들어주는 jackson
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    void join() throws Exception {
        String userName = "mingyeong";
        String password = "asdf1234";

        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                // JSON 형식으로 바꿔줌
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 - userName 중복")
    void join_fail() throws Exception {
        String userName = "mingyeong";
        String password = "asdf1234";

        // 56번 코드가 실행될 때 예외를 날리는것
        // mocking 을 한다 ? 무슨말이즹..?
        // any() 넘기는 값이 무엇이든 exception 처리
        when(userService.join(any(),any()))
                .thenThrow(new RuntimeException("해당 userId가 중복됩니다."));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        // JSON 형식으로 바꿔줌
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password))))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}