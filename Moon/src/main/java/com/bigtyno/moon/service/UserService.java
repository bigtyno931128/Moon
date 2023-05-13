package com.bigtyno.moon.service;

import com.bigtyno.moon.exception.ErrorCode;
import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.model.User;
import com.bigtyno.moon.model.entity.UserEntity;
import com.bigtyno.moon.repository.UserEntityRepository;
import com.bigtyno.moon.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public User join(String userName, String password) {
        // 회원 가입하려는 userName으로 회원가입된 사용자가 있는지 ?
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new MoonApplicationException(ErrorCode.DUPLICATED_USER_NAME,"이미 사용중인 닉네임 입니다.");
        });

        // 회원 가입 진행
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,encoder.encode(password)));

        return User.fromEntity(userEntity);
    }

    public User loadUserByUserName(String userName) {
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(()->
                new MoonApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded", userName)));
    }


    // TODO : implement
    public String login(String userName, String password) {

        //회원 가입 여부체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(()->
                new MoonApplicationException(ErrorCode.USER_NOT_FOUND,"사용자가 존재하지 않습니다."));

        //비밀번호 체크
        if(!userEntity.getPassword().equals(password)) {
            throw new MoonApplicationException(ErrorCode.INVALID_PASSWORD,"비밀번호가 일치 하지 않습니다.");
        }

        // jwt 토큰 생성
        String token = JwtTokenUtils.generateAccessToken(userName, secretKey, expiredTimeMs);

        return token;
    }

}
