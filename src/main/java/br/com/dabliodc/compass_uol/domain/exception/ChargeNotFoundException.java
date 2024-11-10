package br.com.dabliodc.compass_uol.domain.exception;

import org.springframework.context.MessageSource;

public class ChargeNotFoundException extends RuntimeException {
    public ChargeNotFoundException(String message) {
        super(message);
    }

    public ChargeNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("error.charge.notfound", null, null));
    }
}
