package com.bigtyno.moon.service;

import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.model.User;
import com.bigtyno.moon.model.entity.UserEntity;
import com.bigtyno.moon.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    // TODO : implement
    public User join(String userName, String password) {
        // 회원 가입하려는 userName으로 회원가입된 사용자가 있는지 ?
        Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userName);

        // 회원 가입 진행
        userEntityRepository.save(new UserEntity());

        return new User();
    }

    // TODO : implement
    public String login(String userName, String password) {

        //회원 가입 여부체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->
                new MoonApplicationException());

        //비밀번호 체크
        if(!userEntity.getPassword().equals(password)) {
            throw new MoonApplicationException();
        }

        // jwt 토큰 생성

        return "";
    }

}
