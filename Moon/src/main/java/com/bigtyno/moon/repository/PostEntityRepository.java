package com.bigtyno.moon.repository;

import com.bigtyno.moon.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity,Long > {

    public Page<PostEntity> findAllByUserId(Long userId, Pageable pageable);
}
