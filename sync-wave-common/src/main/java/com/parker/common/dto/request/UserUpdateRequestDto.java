package com.parker.common.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String role;
    private String type;
}
