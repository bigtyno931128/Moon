package com.bigtyno.moon.service;

import com.bigtyno.moon.exception.ErrorCode;
import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.fixture.UserEntityFixture;
import com.bigtyno.moon.model.entity.UserEntity;
import com.bigtyno.moon.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;
    @Test
    void 회원가입이_정상적으로_동작하는_경우(){

        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName , password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(fixture);

        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    @Test
    void 회원가입시_입력한_userName으로_회원가입한_유저가_이미_있는경우(){

        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName , password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        MoonApplicationException exception = Assertions.assertThrows(MoonApplicationException.class,
                () -> userService.join(fixture.getUserName(), fixture.getPassword()));

        Assertions.assertEquals(ErrorCode.DUPLICATED_USER_NAME, exception.getErrorCode());
    }

//    @Test
//    void 회원가입시_아이디양식이_맞지않는_경우() {
//
//    }

    @Test
    void 로그인이_정상적으로_동작하는_경우(){

        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName , password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
    }

    @Test
    void 로그인시_userName_회원가입한_유저가_없는_경우(){

        String userName = "userName";
        String password = "password";

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

        Assertions.assertThrows(MoonApplicationException.class,() -> userService.login(userName, password));
    }

    @Test
    void 로그인시_패스워드가_일치하지않는_경우(){

        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrongPassword";

        UserEntity fixture = UserEntityFixture.get(userName , password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        Assertions.assertThrows(MoonApplicationException.class,() -> userService.login(userName, wrongPassword));
    }
}
