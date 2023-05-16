package com.bigtyno.moon.repository;

import com.bigtyno.moon.model.User;
import com.bigtyno.moon.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
}
