package com.bigtyno.moon.service;

import com.bigtyno.moon.exception.ErrorCode;
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
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new MoonApplicationException(ErrorCode.DUPLICATED_USER_NAME,"이미 사용중인 닉네임 입니다.");
        });

        // 회원 가입 진행
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,password));

        return User.fromEntity(userEntity);
    }

    // TODO : implement
    public String login(String userName, String password) {

        //회원 가입 여부체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->
                new MoonApplicationException(ErrorCode.DUPLICATED_USER_NAME,"이미 사용중인 닉네임 입니다."));

        //비밀번호 체크
        if(!userEntity.getPassword().equals(password)) {
            throw new MoonApplicationException(ErrorCode.DUPLICATED_USER_NAME,"이미 사용중인 닉네임 입니다.");
        }

        // jwt 토큰 생성

        return "";
    }

}
