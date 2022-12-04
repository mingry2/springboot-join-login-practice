package com.springboot.joinpractice.repository;

import com.springboot.joinpractice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // userName을 중복체크해줘야하기 때문에 findById가 아닌 findByName으로 커스텀해줘야함
    // userName이 있으면 optional 안에 값이 들어오고 없으면 안들어옴
    Optional<User> findByUserName(String userName);
}
