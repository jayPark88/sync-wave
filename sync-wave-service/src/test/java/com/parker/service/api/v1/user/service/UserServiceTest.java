package com.parker.service.api.v1.user.service;

import com.parker.common.enums.Role;
import com.parker.common.enums.UserStatus;
import com.parker.common.jpa.entity.UserEntity;
import com.parker.common.jpa.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void deleteUserInfo() {
        // given
        UserEntity saveResultEntity =
                userRepository.saveAndFlush(
                        UserEntity.builder()
                                .userName("parker-temp")
                                .password("parker123123!!")
                                .phone("01000000000")
                                .email("parker-test@gmail.com")
                                .role(Role.ROLE_MASTER.code())
                                .status(UserStatus.HOLDING.code())
                                .build()
                );

        // when
        Assertions.assertTrue(userRepository.findByEmail(saveResultEntity.getEmail()).isPresent());
        userRepository.deleteByEmail(saveResultEntity.getEmail());

        Assertions.assertTrue(userRepository.findByEmail(saveResultEntity.getEmail()).isEmpty());
    }
}