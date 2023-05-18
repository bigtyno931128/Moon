package com.bigtyno.moon.repository;

import com.bigtyno.moon.model.entity.AlarmEntity;
import com.bigtyno.moon.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

    Page<AlarmEntity> findAllByUser(UserEntity user, Pageable pageable);
}
