package com.parse.model;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class XmlUserDto {

    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "username")
    private String userName;
}