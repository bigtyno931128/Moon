package com.bigtyno.moon.repository;

import com.bigtyno.moon.model.User;
import com.bigtyno.moon.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
}
