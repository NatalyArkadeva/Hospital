package com.nataly.hospital.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum Message {
    NOT_FOUND("Not found"),
    ALREADY_EXISTS("Already exists");

    private String description;
}
