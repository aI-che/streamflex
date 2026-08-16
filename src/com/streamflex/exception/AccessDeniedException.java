package com.streamflex.exception;

/**
 * Kullanıcının hesap durumu nedeniyle içerik erişiminin
 * reddedildiğinde fırlatılan özel istisna sınıfı.
 * State Pattern tarafından kullanılır.
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }
}
