package com.bigtyno.moon.service;

import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.fixture.UserEntityFixture;
import com.bigtyno.moon.model.entity.UserEntity;
import com.bigtyno.moon.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @Test
    void 회원가입이_정상적으로_동작하는_경우(){

        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName , password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    @Test
    void 회원가입시_userName_회원가입한_유저가_이미_있는경우(){

        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName , password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(MoonApplicationException.class,() -> userService.join(userName, password));
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우(){

        String userName = "userName";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(userName , password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

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
