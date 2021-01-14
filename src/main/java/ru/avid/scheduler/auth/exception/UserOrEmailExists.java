package ru.avid.scheduler.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class UserOrEmailExists extends AuthenticationException {
    public UserOrEmailExists(String msg) {
        super(msg);
    }

    public UserOrEmailExists(String msg, Throwable cause) {
        super(msg, cause);
    }
}
