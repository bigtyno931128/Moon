package com.bigtyno.moon.model.entity;

import com.bigtyno.moon.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;
    private UserRole role = UserRole.USER;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

}
