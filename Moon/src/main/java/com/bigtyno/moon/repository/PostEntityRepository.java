package com.bigtyno.moon.repository;

import com.bigtyno.moon.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity,Long > {
}
