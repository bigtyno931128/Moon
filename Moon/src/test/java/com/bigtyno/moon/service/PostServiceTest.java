package com.bigtyno.moon.service;

import com.bigtyno.moon.exception.ErrorCode;
import com.bigtyno.moon.exception.MoonApplicationException;
import com.bigtyno.moon.fixture.TestInfoFixture;
import com.bigtyno.moon.fixture.UserEntityFixture;
import com.bigtyno.moon.model.entity.PostEntity;
import com.bigtyno.moon.repository.PostEntityRepository;
import com.bigtyno.moon.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    PostService postService;

    @MockBean
    UserEntityRepository userEntityRepository;

    @MockBean
    PostEntityRepository postEntityRepository;

    @Test
    void 할일작성시_정상작동() {
        TestInfoFixture.TestInfo fixture = TestInfoFixture.get();
        when(userEntityRepository.findByUserName(fixture.getUserName())).thenReturn(Optional.of(UserEntityFixture.get(fixture.getUserName(), fixture.getPassword())));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));
        Assertions.assertDoesNotThrow(() -> postService.write(fixture.getTitle(), fixture.getContent(), fixture.getStar(), fixture.getDeadLine(), fixture.getUserName()));
    }


    @Test
    void 할일작성시_유저가_존재하지_않으면_에러를_내뱉는다() {
        TestInfoFixture.TestInfo fixture = TestInfoFixture.get();
        when(userEntityRepository.findByUserName(fixture.getUserName())).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));
        MoonApplicationException exception = Assertions.assertThrows(MoonApplicationException.class, () -> postService.write(fixture.getTitle(), fixture.getContent(), fixture.getStar(), fixture.getDeadLine(), fixture.getUserName()));

        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @Disabled
    void 할일작성시_마감일이_현재날짜를_벗어난경우_에러발생(){
        TestInfoFixture.TestInfo fixture = TestInfoFixture.get();
        when(userEntityRepository.findByUserName(fixture.getUserName())).thenReturn(Optional.of(UserEntityFixture.get(fixture.getUserName(), fixture.getPassword())));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));
        MoonApplicationException exception = Assertions.assertThrows(MoonApplicationException.class, () -> postService.write(fixture.getTitle(), fixture.getContent(), fixture.getStar(), fixture.getDeadLine(), fixture.getUserName()));

        Assertions.assertEquals(ErrorCode.OVER_DEADLINE, exception.getErrorCode());
    }
}
