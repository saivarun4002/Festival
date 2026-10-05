
package com.vinayakachaviti.exception;

import java.io.Serializable;

public class BadRequestException extends RuntimeException implements Serializable {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}