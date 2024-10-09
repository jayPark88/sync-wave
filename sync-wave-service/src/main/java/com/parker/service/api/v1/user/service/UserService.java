package com.parker.service.api.v1.user.service;


import com.parker.common.dto.request.UserDto;
import com.parker.common.dto.request.UserUpdateRequestDto;
import com.parker.common.enums.Role;
import com.parker.common.enums.UserStatus;
import com.parker.common.exception.CustomException;
import com.parker.common.jpa.entity.UserEntity;
import com.parker.common.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

import static com.parker.common.exception.enums.ResponseErrorCode.FAIL_2000;
import static com.parker.common.exception.enums.ResponseErrorCode.FAIL_500;


/**
 * com.parker.admin.api.v1.login.service
 * ㄴ UserService
 *
 * <pre>
 * description :
 * </pre>
 *
 * <pre>
 * <b>History:</b>
 *  parker, 1.0, 12/25/23  초기작성
 * </pre>
 *
 * @author parker
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Transactional
    public UserEntity signUp(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new CustomException(FAIL_2000.code(), messageSource.getMessage("error.2000", new String[]{userDto.getUserName()}, Locale.getDefault()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserEntity userEntity = UserEntity.builder()
                .userName(userDto.getUserName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickName(userDto.getNickName())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .role(userRepository.count() > 0 ? Role.ROLE_MASTER.code() : Role.ROLE_USER.code())
                .status(UserStatus.ACTIVATED.code())
                .createId(userDto.getNickName())// 삭제 예정 notion 기록전 남겨 둠, 회원가입 시 사용자가 인증되지 않은 상태에서 @CreatedBy를 사용하면 anonymousUser가 자동으로 설정됩니다. 따라서 회원가입 후 사용자를 즉시 로그인시키거나, createId를 명시적으로 설정하여 이 문제를 해결할 수 있습니다.
                .build();
        return userRepository.save(userEntity);
    }

    @Transactional
    public UserEntity updateUser(String userId, UserUpdateRequestDto userUpdateRequestDto) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    Optional.ofNullable(userUpdateRequestDto.getPassword())
                            .map(passwordEncoder::encode)// map: userUpdateRequestDto.getPassword()는 인코딩 후에 설정해야 하므로 map을 사용하여 중간 변환을 처리합니다.
                            .ifPresent(existingUser::setPassword);
                    return existingUser;
                }).orElseThrow(() -> new CustomException(FAIL_500.code(), messageSource.getMessage("user.not.found", null, Locale.getDefault()), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Transactional
    public String deleteUserInfo(String userId){
        userRepository.deleteById(userId);
        return userId+" deleted!";
    }

    @Transactional(readOnly = true)
    public Page<UserEntity> searchUserList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> getUserInfo(String userId){
        return userRepository.findById(userId);
    }
}