package com.nataly.hospital.exception;

import lombok.Getter;

@Getter
public class HospitalException extends RuntimeException {
    private final Message messageType;

    public HospitalException(Message message) {
        super(message.getDescription());
        this.messageType = message;
    }
}
