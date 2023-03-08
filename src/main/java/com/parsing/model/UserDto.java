package com.parsing.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
class UserDto {

    private final String name;
    private final String email;
    private final String userName;
}