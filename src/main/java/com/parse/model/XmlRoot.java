package com.parse.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@XmlRootElement(name = "users")
public class XmlRoot {

    @XmlElement(name = "user")
    private final List<XmlUserDto> users = new ArrayList<>();
}