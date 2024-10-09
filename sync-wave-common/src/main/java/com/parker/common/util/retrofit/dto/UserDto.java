package com.parker.common.util.retrofit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    @JsonProperty("results")
    private List<UserResult> results;

    @JsonProperty("info")
    private UserInfo info;
}

@Data
class UserResult {
    private String gender;
    private UserName name;
    private UserLocation location;
    private String email;
    private UserLogin login;
    private UserDob dob;
    private UserRegistered registered;
    private String phone;
    private String cell;
    private UserId id;
    private UserPicture picture;
    private String nat;
}

@Data
class UserName {
    private String title;
    private String first;
    private String last;
}

@Data
class UserLocation {
    private UserStreet street;
    private String city;
    private String state;
    private String country;
    private int postcode;
    private UserCoordinates coordinates;
    private UserTimezone timezone;
}

@Data
class UserStreet {
    private int number;
    private String name;
}

@Data
class UserCoordinates {
    private String latitude;
    private String longitude;
}

@Data
class UserTimezone {
    private String offset;
    private String description;
}

@Data
class UserLogin {
    private String uuid;
    private String username;
    private String password;
    private String salt;
    private String md5;
    private String sha1;
    private String sha256;
}

@Data
class UserDob {
    private String date;
    private int age;
}

@Data
class UserRegistered {
    private String date;
    private int age;
}

@Data
class UserId {
    private String name;
    private String value;
}

@Data
class UserPicture {
    private String large;
    private String medium;
    private String thumbnail;
}

@Data
class UserInfo {
    private String seed;
    private int results;
    private int page;
    private String version;
}


