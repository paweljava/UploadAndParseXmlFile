package com.parse.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
class XmlUser {

    private final String name;
    private final String email;
    private final String userName;
}