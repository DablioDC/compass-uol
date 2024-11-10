package br.com.dabliodc.compass_uol.domain.exception;

import org.springframework.context.MessageSource;

public class VendorNotFoundException extends RuntimeException {
    public VendorNotFoundException(String message) {
        super(message);
    }

    public VendorNotFoundException(MessageSource messageSource) {
        super(messageSource.getMessage("error.vendor.notfound", null, null));
    }
}

